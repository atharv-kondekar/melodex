package com.sunbeam.users.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbeam.albums.domain.Album;
import com.sunbeam.albums.dto.AlbumRequestDTO;
import com.sunbeam.albums.dto.AlbumResponseDTO;
import com.sunbeam.users.dao.UserDao;
import com.sunbeam.users.domain.User;
import com.sunbeam.users.dto.LoginCredentialsDTO;
import com.sunbeam.users.dto.UserRequestDTO;
import com.sunbeam.users.dto.UserResponseDTO;
import com.sunbeam.users.utils.ApiResponse;
import com.sunbeam.users.utils.InvalidCredentialsException;

@Service
@Transactional
public class UserService {

	private UserDao userDao;
	private ModelMapper mapper;

	public UserService(UserDao userDao , ModelMapper mapper) {
		this.userDao = userDao;
		this.mapper=mapper;
	}

	public UserResponseDTO registerUser (UserRequestDTO userRequestDTO) {
		User user = mapper.map(userRequestDTO, User.class);
		user.setRole("ROLE_CUSTOMER");
		System.out.println("UserRequestSTO => User");
		System.out.println("UserRerquestDTO = "+userRequestDTO);
		System.out.println("User = "+user);

		userDao.save(user);

		UserResponseDTO userResponseDTO =
				mapper.map(user, UserResponseDTO.class);

		System.out.println("\nUser => UserResponseDTO");
		System.out.println("User = "+user);
		System.out.println("UserResponseDTO = "+userResponseDTO);

		return userResponseDTO;
 	}

	public List<UserResponseDTO> getAllUsers() {
		List<User> userList = userDao.findAll();
		return userList.stream()
				.map( user -> mapper.map(user,UserResponseDTO.class))
				.toList();
	}

	public UserResponseDTO findById(int id) {
		User user = userDao.findUserById(id);
		return mapper.map(user, UserResponseDTO.class);
	}

	public UserResponseDTO updateEmail(int id,String email) {
		int count = userDao.updateEmail(email, id);
		if (count == 1 ) {
			User user = userDao.findUserById(id);
			return mapper.map(user, UserResponseDTO.class);
		}
		return null;
	}

	public UserResponseDTO deleteById(int id) {
		User user = userDao.findUserById(id);
		int count = userDao.deleteUser(id);

		if (count == 1) {
			return mapper.map(user, UserResponseDTO.class);
		}

		return null;
	}

	public UserResponseDTO authenticate(String email , String password) {
		Optional<User> optUser = userDao.findByEmail(email);

		if (optUser.isPresent()) {

		    User user = optUser.get();

		    if (password.equals(user.getPasswd())) {
		        return mapper.map(user, UserResponseDTO.class);
		    }
		}

		 throw new InvalidCredentialsException();
	}


}
