package com.javatpoint.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

	private String SECRET_KEY = "Zm9vYmFyYmF6cXV4eHl6MTIzNDU2Nzg5YWJjZGVmZ2hpamtsbW5vcA==";
	Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	public String getTokenFromHeader(HttpServletRequest request) {
		
		String bearerToken=request.getHeader("Authorization");
		
		if(bearerToken !=null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
		
	}
	
	public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
	
	 public String extractUsername(String token) {
	        return Jwts.parser()
	                .setSigningKey(SECRET_KEY)
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
	 public boolean validateToken(String token) {
	        try {
	            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
	            return true;
	        } catch (JwtException e) {
	            return false;
	        }
	    }

}
