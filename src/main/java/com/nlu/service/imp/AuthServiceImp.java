package com.nlu.service.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nlu.exception.NotFoundException;
import com.nlu.exception.ResourceExistedException;
import com.nlu.model.dto.request.LoginRequest;
import com.nlu.model.dto.request.RegisterRequest;
import com.nlu.model.dto.response.AuthenticationResponse;
import com.nlu.model.entity.Role;
import com.nlu.model.entity.User;
import com.nlu.model.enumeration.ERole;
import com.nlu.repository.RoleRepository;
import com.nlu.repository.UserRepository;
import com.nlu.security.CustomUserDetails;
import com.nlu.security.jwt.JwtTokenProvider;
import com.nlu.service.AuthService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{
	
	UserRepository userRepository;
	RoleRepository roleRepository;
	PasswordEncoder passwordEncoder;
	JwtTokenProvider jwtTokenProvider;
	UserDetailsService userDetailsService;
	AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public AuthenticationResponse login(LoginRequest request) {

		// Thực hiện xác thực người dùng
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				request.getUsername(),
				request.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		// Xác thực thành công, tiếp tục xử lý
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService
				.loadUserByUsername(request.getUsername());
		String jwtToken = jwtTokenProvider.generateToken(customUserDetails);

		Set<String> listRoles = customUserDetails.getAuthorities()
				.stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toSet());

		return AuthenticationResponse.builder()
				.token(jwtToken)
				.userId(customUserDetails.getId())
				.authenticated(true)
				.listRoles(listRoles)
				.build();
	    
	}

	@Transactional
	@Override
	public void register(RegisterRequest request) {
		// TODO Auto-generated method stub
		if(userRepository.existsByUsername(request.getUsername()))
			throw new ResourceExistedException("register_username_exists");
		if(userRepository.existsByEmail(request.getEmail()))
			throw new ResourceExistedException("register_email_exists");
		
		User user = new User();
		user.setUsername(request.getUsername());
		user.setCode(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setActive(true);
		List<String> listRoles = request.getListRoles();
		Set<Role> setRoles = new HashSet<>();
		
		if(listRoles.isEmpty()) {
			Role role = roleRepository.findByName(ERole.STUDENT.toString())
					.orElseThrow(() -> new NotFoundException("role_not_found",ERole.STUDENT.toString()));
			setRoles.add(role);
		}else {
			listRoles.forEach(role ->{
			Role roleTeacher = roleRepository.findByName(ERole.TEACHER.toString())
					.orElseThrow(() -> new NotFoundException("role_not_found",ERole.TEACHER.toString()));
			setRoles.add(roleTeacher);
			});
		}
		user.setListRoles(setRoles);
		
		userRepository.save(user);
		
	}
	
	

}
