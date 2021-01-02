package medgateway.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;


import medgateway.models.UserRepository;
import medgateway.models.Users;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepository.findById(username).orElseThrow(()->new UsernameNotFoundException(username));
		List<GrantedAuthority> authorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getRole());
		return new User(user.getUsername(),user.getPassword(),authorities);
	}
	

}
