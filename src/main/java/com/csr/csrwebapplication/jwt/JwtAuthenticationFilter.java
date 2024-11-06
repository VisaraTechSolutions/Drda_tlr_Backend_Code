package com.csr.csrwebapplication.jwt;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Autowired
	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = null;
		String username = null;
		String decryptedToken = null;

		// Check Authorization header
		final String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);
			try {
				decryptedToken = SecureAES.decrypt(jwtToken); // Decrypt the token
			} catch (Exception e) {
				// Log and handle decryption errors
				e.printStackTrace();
				// Optionally, respond with an error status code
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
				return;
			}

			// Extract username from the decrypted token
			username = jwtUtil.extractUsername(decryptedToken);
		}

		// Validate token and set authentication
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (jwtUtil.validateToken(decryptedToken, username)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
						null, new ArrayList<>());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				// Token validation failed
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

}