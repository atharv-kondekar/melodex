package com.sunbeam.users.controller;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunbeam.users.dto.LoginCredentialsDTO;
import com.sunbeam.users.dto.UserRequestDTO;
import com.sunbeam.users.dto.UserResponseDTO;
import com.sunbeam.users.service.UserService;
import com.sunbeam.users.utils.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersRestController {

	private UserService userService;

	public UsersRestController(UserService userService) {
		this.userService = userService;
	}

	//Exception Handling in the REST API

	// Option 1 : Use the try-catch for every method
	@PostMapping
	public ApiResponse<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequestDTO) {
//		try {
			UserResponseDTO userResponseDTO = userService.registerUser(userRequestDTO);
			return ApiResponse.success(userResponseDTO);
//		} catch (Exception e) {
//			return ApiResponse.error("Error : "+e.getMessage());
//		}
	}

	// Option 2 : Handle Exception in controller level - Write for Each @Controller Class
	//@ExceptionHandler//(exception = DataAccessException.class)
/*
	public ApiResponse<?> handelException(Exception e){
		return ApiResponse.error("Error : "+e.getMessage());
	}
*/
	//Option 3 : Write the @ExceptionHandler for all Controller
	// 			 @RestControllerAdvice

	@GetMapping
	public ApiResponse<List<UserResponseDTO>> getAllUsers(){
		return ApiResponse.success( userService.getAllUsers() );
	}

	@PostMapping("/{id}")
	public ApiResponse<UserResponseDTO> findById(@PathVariable int id){
		return ApiResponse.success( userService.findById(id));
	}

	@PatchMapping("/{id}")
	public ApiResponse<UserResponseDTO> updateEmail(@PathVariable int id, @RequestBody LoginCredentialsDTO lc){
		return ApiResponse.success( userService.updateEmail(id,lc.getEmail()));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<UserResponseDTO> deleteUser(@PathVariable int id){
		return ApiResponse.success( userService.deleteById(id));
	}

	@PostMapping("/login")
	public ApiResponse<UserResponseDTO> authenticate(@RequestBody LoginCredentialsDTO lc){
		UserResponseDTO user = userService.authenticate(lc.getEmail(), lc.getPassword());
		return ApiResponse.success(user);
	}
}
