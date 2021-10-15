package com.autenticacion.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.autenticacion.service.UserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtTokenFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	UserDetailService userdetailservice;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = this.getToken(req);
			if (token != null && validateToken(token)) {

				String nombreUsuario = getNombreUsuarioFromToken(token);

				UserDetails userdetails = userdetailservice.loadUserByUsername(nombreUsuario);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userdetails, null,
						userdetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			logger.error("Fail en el metodo Do Filter");
		}
		filterChain.doFilter(req, res);
	}

	private String getToken(HttpServletRequest request) {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer")) {

			return header.replace("Bearer ", "");
		}

		return null;

	}

	public boolean validateToken(String token) {

		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

			return true;

		} catch (MalformedJwtException e) {
			logger.error("token mal formado");
		} catch (UnsupportedJwtException e) {
			logger.error("token no soportado");
		} catch (ExpiredJwtException e) {
			logger.error("token expirado");
		} catch (IllegalArgumentException e) {
			logger.error("token vacio");
		} catch (SignatureException e) {
			logger.error("fail en la firma");
		}
		return false;
	}

	public String getNombreUsuarioFromToken(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

}
