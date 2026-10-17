package com.sunbeam.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = {"password"})
public class LoginCredentialsDTO {
	 private String email;
	 private String password;
}
