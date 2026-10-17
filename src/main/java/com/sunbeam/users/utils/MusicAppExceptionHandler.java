package com.sunbeam.users.utils;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MusicAppExceptionHandler {
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ApiResponse<?> dataAccesException(Exception e){
		return ApiResponse.error("Data access Exception : "+e.getMessage());
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public ApiResponse<?> genericException(Throwable e){
		return ApiResponse.error("Error : "+e.getMessage());
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler
	public ApiResponse<?> loginException(InvalidCredentialsException ex){
		return ApiResponse.error(ex.getMessage());
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = MalformedURLException.class)
	public ApiResponse<?> downloadError(Throwable e){
		return ApiResponse.error("Download file error : "+e.getMessage());
	}

	@ExceptionHandler(value = IOException.class)
	public ApiResponse<?> uploadError(Throwable e){
		return ApiResponse.error("Album upload error : "+e.getMessage());
	}
}
