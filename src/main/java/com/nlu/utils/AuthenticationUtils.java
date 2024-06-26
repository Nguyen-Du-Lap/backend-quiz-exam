package com.nlu.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nlu.security.CustomUserDetails;

public class AuthenticationUtils {
	public static Long extractUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		var principal = authentication.getPrincipal();
		if (principal.equals("anonymousUser")) {
			return 0L;
		}
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		return userDetails.getId();
	}

	public static CustomUserDetails extractUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		var principal = authentication.getPrincipal();
		if (principal.equals("anonymousUser")) {
			return null;
		}
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		return userDetails;
	}
}
