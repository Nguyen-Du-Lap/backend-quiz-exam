package com.nlu.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.nlu.config.Translator;
import com.nlu.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${nlu.jwt.secret}")
	private String SECRET_KEY;
	@Value("${nlu.jwt.expiration}")
	private Long JWT_TIME_EXPIRATION;
	private Key getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date dateExpiration = new Date(now.getTime() + JWT_TIME_EXPIRATION * 30);

		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(now)
				.setExpiration(dateExpiration)
				.claim("roles", buildClaimRoles(userDetails))
				.signWith(getSecretKey(), SignatureAlgorithm.HS512)
				.compact();
	}
	private String buildClaimRoles(UserDetails userDetails) {
		  StringJoiner stringJoiner = new StringJoiner(" ");
		  if(!CollectionUtils.isEmpty(userDetails.getAuthorities()))
			  userDetails.getAuthorities()
			  .forEach(authority ->
				  stringJoiner.add(authority.getAuthority())
			  );
		return stringJoiner.toString();
	}

	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String authToken) {

		try {
			Jwts.parserBuilder()
			.setSigningKey(getSecretKey())
			.build()
			.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
	        log.error("Invalid JWT signature: {}", e.getMessage());
	        throw new SignatureException(Translator.toLocale("jwt_invalid"));
	    } catch (MalformedJwtException e) {
	        log.error("Malformed JWT token: {}", e.getMessage());
	        throw new MalformedJwtException(Translator.toLocale("jwt_malformed"));
	    } catch (ExpiredJwtException e) {
	        log.error("JWT token is expired: {}", e.getMessage());
	        throw new ExpiredJwtException(null, null, Translator.toLocale("jwt_invalid"));
	    }
	}

}
