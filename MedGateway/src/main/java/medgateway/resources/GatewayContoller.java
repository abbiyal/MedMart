package medgateway.resources;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import medgateway.models.AuthenticationRequest;
import medgateway.models.AuthenticationResponse;
import medgateway.models.UserRepository;
import medgateway.models.Users;
import medgateway.services.UserService;
import medgateway.util.JwtUtil;

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
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String addUser(@RequestBody Users user) {
		int strength=10;
		BCryptPasswordEncoder bCryptPasswordEncoder =
				  new BCryptPasswordEncoder(strength, new SecureRandom());
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		return "success";
	}
}
