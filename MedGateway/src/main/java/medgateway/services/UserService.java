package medgateway.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;


import medgateway.models.UserRepository;
import medgateway.models.Users;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepository.findById(username).orElseThrow(()->new UsernameNotFoundException(username));
		List<GrantedAuthority> authorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getRole());
		return new User(user.getUsername(),user.getPassword(),authorities);
	}
	
	public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findById(email);
        if (user.isPresent()) {
            user.get().setResetToken(token);
            userRepository.save(user.get());
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }
     
    public Users getByResetPasswordToken(String token) {
        return userRepository.findByResetToken(token);
    }
     
    public void updatePassword(Users user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetToken(null);
        userRepository.save(user);
    }
    
    public HashMap<String,String> addUser(Users user){
    	Optional<Users> alreadyexistuser = userRepository.findById(user.getUsername());
    	int strength=10;
    	HashMap<String, String> map = new HashMap<>();
    	if(alreadyexistuser.isPresent()) {
    		map.put("response","already exist");
    		return map;
    	}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		System.out.println(user);
		user.setPassword(encodedPassword);
		userRepository.save(user);
		HashMap<String,String> tokensent = emailService.sendtoken(user.getUsername());
		if(tokensent.get("response").contentEquals("success")) {
			map.put("response", "success");
		}else {
			userRepository.deleteById(user.getUsername());
			map.put("response","failure");
		}
		return map;
    }

    public  HashMap<String,String> deleteUser(String email){
    	System.out.println("hello");
    	HashMap<String,String> map = new HashMap<String,String>();
    	try {
    		userRepository.deleteById(email);
    		map.put("response", "success");
    	}catch(Exception e) {
    		map.put("response", "failure");
    	}
    	return map;
    }
    
    public  HashMap<String,String> updatePhone(String email,String phoneno){
    	Optional<Users> user = userRepository.findById(email);
    	HashMap<String,String> map = new HashMap<String,String>();
    	if(user.get() == null)
    		map.put("response","no user found");
    	else {
    		user.get().setPhone(phoneno);
    		userRepository.save(user.get());
    		map.put("response","success");
    	}
    	return map;
    		
    }

    public HashMap<String,String> updateName(String email,String name){
    	Optional<Users> user = userRepository.findById(email);
    	HashMap<String,String> map = new HashMap<String,String>();
    	if(user.get() == null)
    		map.put("response","no user found");
    	else {
    		user.get().setName(name);
    		userRepository.save(user.get());
    		map.put("response","success");
    	}
    	return map;
    }

}
