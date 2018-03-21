package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.demo.dao.User;
import com.example.demo.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/", "/home").permitAll().anyRequest().authenticated()
		.antMatchers("/webjars", "/home").permitAll().anyRequest().anonymous()
		.and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {

		Collection<User> users = new ArrayList<>();
		users.add(new User("florian", "florian"));
		users.add(new User("jakob", "jakob"));
		users.add(new User("martin", "martin"));

		userRepository.saveAll(users);

		Collection<UserDetails> userDetails = new ArrayList<>();

		for (User user : users) {
			userDetails.add(org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
					.username(user.getUsername()).password(user.getPassword()).roles("USER").build());
		}

		return new InMemoryUserDetailsManager(userDetails);
	}
}