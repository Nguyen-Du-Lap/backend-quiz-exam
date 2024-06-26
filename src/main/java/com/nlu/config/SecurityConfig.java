package com.nlu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nlu.model.enumeration.ERole;
import com.nlu.security.ExceptionHandlerFilter;
import com.nlu.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
	
	
	private final static String[] PUBLIC_ENDPOINTS = {
			"/api/auth/login",
			"/api/auth/register",
			"/api/students/login",
			"/api/worktimes/students/**",
			"/api/exam-numbers/students/**",
			"/api/user_answers/students/**",
	};
	private final static String[] SWAGGER_ENDPOINTS = {
			"swagger-ui.html",
			"/swagger-ui/**",
			"/v3/api-docs/**",
            "/javainuse-openapi/**"
	};
	
	@Autowired private UserDetailsService userDetailsService;
	@Autowired private CorsConfigurationSource corsConfigurationSource; 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ExceptionHandlerFilter exceptionHandlerFilter() {
		return new ExceptionHandlerFilter();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
		 	.cors(cors -> cors.configurationSource(corsConfigurationSource))
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.authorizeHttpRequests(authorize ->
					authorize.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
					.requestMatchers(HttpMethod.GET,"/api/exams/**").permitAll()
					.requestMatchers(HttpMethod.GET,"/api/categories/**").permitAll()
					.requestMatchers(HttpMethod.GET,"/api/time-exams/**").permitAll()
					
					// role teacher
					.requestMatchers("/api/students/import").hasRole(ERole.TEACHER.toString())
					.requestMatchers("/api/students/revoke/**").hasRole(ERole.TEACHER.toString())
					.requestMatchers("/api/students/download/**").hasRole(ERole.TEACHER.toString())
					
					//role admin
					
					.requestMatchers(HttpMethod.POST,"/api/exams").hasAnyRole(ERole.TEACHER.toString(),ERole.ADMIN.toString())
					.requestMatchers("/api/result/**").hasAnyRole(ERole.TEACHER.toString(),ERole.ADMIN.toString())
					.requestMatchers("/api/exams/all").hasRole(ERole.ADMIN.toString())
					.requestMatchers("/api/upload/**").hasAnyRole(ERole.TEACHER.toString(),ERole.ADMIN.toString())
						.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter(), JwtAuthenticationFilter.class)
                .exceptionHandling(handling -> {
                handling.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
                })
              ;
        
      return http.build();
	}
		
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(SWAGGER_ENDPOINTS);
	}
}
