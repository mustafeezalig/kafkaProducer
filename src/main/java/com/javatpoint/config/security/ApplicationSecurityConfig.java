package com.javatpoint.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class ApplicationSecurityConfig {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity
		http.authorizeHttpRequests(request -> request.requestMatchers("/login").permitAll().anyRequest().authenticated());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// http.formLogin();
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.httpBasic();
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailService(PasswordEncoder passwordEncoder) {
		UserDetails user = User.withUsername("admin1").password(passwordEncoder.encode("admin123")).roles("ADMIN")
				.build();

		UserDetails user2 = User.withUsername("user1").password(passwordEncoder.encode("user123")).roles("USER")
				.build();

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		// jdbcUserDetailsManager.createUser(user);
		// jdbcUserDetailsManager.createUser(user2);
		jdbcUserDetailsManager.getUsersByUsernameQuery();
		System.out.println("Credentials........." + jdbcUserDetailsManager.getUsersByUsernameQuery());
		return jdbcUserDetailsManager;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}
}
