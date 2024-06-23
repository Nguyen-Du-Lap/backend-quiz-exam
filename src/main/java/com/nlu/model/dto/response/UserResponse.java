package com.nlu.model.dto.response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.nlu.model.entity.Role;
import com.nlu.model.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
	
	Long id;
	String code;
	String fullname;
	String email;
	String username;
	Boolean active;
	Set<String> listRoles;
	
	public static UserResponse fromEntity(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.code(user.getCode())
				.fullname(user.getFullname())
				.username(user.getUsername())
				.email(user.getEmail())
				.active(user.getActive())
				.listRoles(cvtListRoles(user.getListRoles()))
				.build();
	}
	
	private static Set<String> cvtListRoles(Set<Role> listRoles){
        return Optional.ofNullable(listRoles).orElse(Collections.emptySet()).stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

	public static List<UserResponse> fromEntities(List<User> users) {
		return Optional.ofNullable(users)
				.orElse(Collections.emptyList())
				.stream()
				.map(UserResponse::fromEntity)
				.toList();
	}

}
