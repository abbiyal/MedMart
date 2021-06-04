package medgateway.services;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import medgateway.models.UserRepository;
import medgateway.models.Users;

@Service
public class EmailService {
	
	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private JavaMailSender mailSender;
	
	public HashMap<String,String> sendtoken(String email){
		System.out.println(email);
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		String token=Integer.toString(otp);
		try {
	        userDetailsService.updateResetPasswordToken(token, email);
	        sendEmail(email, token);         
	    } catch (UsernameNotFoundException ex) {
	    	HashMap<String,String> map=new HashMap<>();
	        map.put("response","No User Found");
	        return map;
	    } catch (UnsupportedEncodingException | MessagingException e) {
	        HashMap<String,String> map=new HashMap<>();
	        map.put("response","Connetion Error !!");
	       return map;
	    }
		HashMap<String, String> map = new HashMap<>();
		map.put("response","success");
		return map;
	}
	
	
	public void sendEmail(String recipientEmail, String token)
	        throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message =mailSender.createMimeMessage();             
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom("medmartowner@gmail.com", "Medmart Support");
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

}
