package com.ecommerce.tutorial.ecommercebackend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ecommerce.tutorial.ecommercebackend.model.LocalUser;
import com.ecommerce.tutorial.ecommercebackend.repository.LocalUserDAO;
import com.ecommerce.tutorial.ecommercebackend.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTRequestFilter extends OncePerRequestFilter{

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private LocalUserDAO repository;	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenHeader = request.getHeader("Authorization");
		if(tokenHeader != null && tokenHeader.startsWith("Bearer")) {
			String token = tokenHeader.substring(7);
			try {
				String username = jwtService.getUsername(token);
				Optional<LocalUser> opUser = repository.findByUsernameIgnoreCase(username);
				if(opUser.isPresent()) {
					LocalUser user = opUser.get();
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}catch(JWTDecodeException ex) {
				
			}
		}
		filterChain.doFilter(request, response);
		
	}
	

}
