package medgateway.resources;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Future;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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
import medgateway.services.EmailService;
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
	
	@Autowired
	private EmailService emailService;

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			  authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
					);
		}catch(BadCredentialsException e){
			return ResponseEntity.ok(new AuthenticationResponse("null","null"));
		}
		final UserDetails userDetails=userDetailsService
									  .loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt=jwtTokenUtil.generateToken(userDetails);
		final String roles=userDetails.getAuthorities().toString();
		return ResponseEntity.ok(new AuthenticationResponse(jwt,roles));
		
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public HashMap<String,String> addUser(@RequestBody Users user) {
		return userDetailsService.addUser(user);
	}
	
	@RequestMapping(value="/forgot/sendtoken",method=RequestMethod.POST)
	public HashMap<String,String> changePassword(@RequestBody PasswordRequest passwordRequest) {
		String email=passwordRequest.getEmail();
		return emailService.sendtoken(email);
		
	}
	
	@RequestMapping(value="/forgot/verifytoken",method=RequestMethod.POST)
	public HashMap<String,String> verifyOtp(@RequestBody HashMap<String,String> token) {
		Users user=userRepository.findByResetToken(token.get("otp"));
		HashMap<String,String> map=new HashMap<>();
		if(user==null) {
			map.put("response","Invalid Token");
			return map;
		}
		map.put("response","Valid");
		return map;
	}
	
	@RequestMapping(value="/forgot/updatepassword",method=RequestMethod.POST)
	public HashMap<String,String> updatePassword(@RequestBody HashMap<String,String> request){
		Optional<Users> user=userRepository.findById(request.get("email"));
		Users newUser = user.get();
		userDetailsService.updatePassword(newUser, request.get("password"));
		HashMap<String,String> map=new HashMap<>();
		map.put("response","success");
		return map;
		
	}
		
	}
