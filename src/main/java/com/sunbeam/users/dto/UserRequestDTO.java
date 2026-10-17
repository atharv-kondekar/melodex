package com.sunbeam.users.dto;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor @AllArgsConstructor
//@ToString(exclude = {"password"})
// I don't want to take "role" in the "Request from User"
public class UserRequestDTO {
		//private int id; //id is the Primary key and Auto Increment
		private String name;
		private String email;
		private String password;
		private String address;
		@DateTimeFormat(pattern = "yyyy-mm-dd")
		private Date birth;
}