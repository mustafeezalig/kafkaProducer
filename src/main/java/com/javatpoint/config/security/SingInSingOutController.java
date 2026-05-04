package com.javatpoint.config.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SingInSingOutController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = null;
		try {
			System.out.println("Credentials"+loginRequest);
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		} catch (Exception e) {
			System.out.println("Error::"+e);
			Map<String, Object> map = new HashMap<>();
			map.put("message", "Unauthorized");
			map.put("status", false);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);

		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtUtil.generateToken(userDetails.getUsername());
		List<String> roles = userDetails.getAuthorities().stream().map(u -> u.getAuthority())
				.collect(Collectors.toList());
		LoginResponse loginResponse = new LoginResponse(token, userDetails.getUsername(), roles);
		return ResponseEntity.ok(loginResponse);

	}

}
