package com.gabriel.hroauth.servicies;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gabriel.hroauth.entities.User;
import com.gabriel.hroauth.feignClients.UserFeignClient;

@Service
public class UserService implements UserDetailsService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserFeignClient userFeignClient;
	
	public User findByEmail (String emal) {
		User user = userFeignClient.findByEmail(emal).getBody();
		if (user == null) {
			logger.error("Email not found: " + emal);
			throw new IllegalArgumentException("Email not found");
		}
		logger.info("Email found: " + emal );
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Código repedido apenas para fins de aprendizado
		User user = userFeignClient.findByEmail(username).getBody();
		if (user == null) {
			logger.error("Email not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("Email found: " + username );
		return user;
	}
}
