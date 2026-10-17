package com.sunbeam.users.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
	private String status;
	private T data;
	private String message;

	public static <T> ApiResponse<T> success(T data){
		return new ApiResponse<T>("Success", data, null);
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<T>("Failure", null, message);
	}
}
