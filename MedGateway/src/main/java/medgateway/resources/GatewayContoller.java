package medgateway.resources;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.mail.imap.Utility;

import medgateway.models.AuthenticationRequest;
import medgateway.models.AuthenticationResponse;
import medgateway.models.PasswordRequest;
import medgateway.models.UserRepository;
import medgateway.models.Users;
import medgateway.services.UserService;
import medgateway.util.JwtUtil;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api")
public class GatewayContoller {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private JavaMailSender mailSender;

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			  authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
					);
		}catch(BadCredentialsException e){
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails=userDetailsService
									  .loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt=jwtTokenUtil.generateToken(userDetails);
		final String roles=userDetails.getAuthorities().toString();
		return ResponseEntity.ok(new AuthenticationResponse(jwt,roles));
		
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public HashMap<String,String> addUser(@RequestBody Users user) {
		int strength=10;
		BCryptPasswordEncoder bCryptPasswordEncoder =
				  new BCryptPasswordEncoder(strength, new SecureRandom());
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		System.out.println(user);
		user.setPassword(encodedPassword);
		userRepository.save(user);
		HashMap<String, String> map = new HashMap<>();
		map.put("response","success");
		return map;
	}
	
	@RequestMapping(value="/forgotsendtoken",method=RequestMethod.POST)
	public String changePassword(@RequestBody PasswordRequest passwordRequest) {
		String email=passwordRequest.getEmail();
		Optional<Users> user=userRepository.findById(email);
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		String token=Integer.toString(otp);
		try {
	        userDetailsService.updateResetPasswordToken(token, email);
	        sendEmail(email, token);         
	    } catch (UsernameNotFoundException ex) {
	        System.out.println("No USer Found");
	    } catch (UnsupportedEncodingException | MessagingException e) {
	        System.out.println("Error while sending email");
	    }
		return "success";
	}
	public void sendEmail(String recipientEmail, String token)
	        throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message =mailSender.createMimeMessage();             
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom("medmartowner@gmail.com", "MEdmart Support");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Here's the OTP to reset your password";
	     
	    String content = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>The OTP for the same is</p>"
	            +  token
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}
	@RequestMapping(value="/verifytoken",method=RequestMethod.POST)
	public String updatePassword(@RequestParam(value="token") String token,@RequestParam(value="password") String newPassword) {
		Users user=userRepository.findByResetToken(token);
		if(user==null) {
			return "User not found";
		}
		String original_token=user.getResetToken();
		System.out.println(original_token);
		System.out.println(token);
		if(!original_token.contentEquals(token))
			return "Invalid Token";
		userDetailsService.updatePassword(user, newPassword);
		return "success";
	}
}
