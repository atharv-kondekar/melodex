package com.sunbeam;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sunbeam.albums.domain.Album;
import com.sunbeam.albums.dto.AlbumRequestDTO;
import com.sunbeam.users.domain.User;
import com.sunbeam.users.dto.UserRequestDTO;
import com.sunbeam.users.dto.UserResponseDTO;

@Configuration
public class AppConfig {
	@Bean
	ModelMapper modelMapper() {
		ModelMapper mapper =  new ModelMapper();

		mapper.typeMap(UserRequestDTO.class,User.class)
			.addMapping(UserRequestDTO::getAddress , User::setAddr)
			.addMapping(UserRequestDTO::getPassword, User::setPasswd);

		mapper.typeMap(User.class,UserResponseDTO.class)
			.addMapping(User::getAddr,UserResponseDTO::setAddress);

		mapper.typeMap(AlbumRequestDTO.class,Album.class)
			.addMapping(dto->dto.getThumbnail().getOriginalFilename(),
					(en,name)->en.setThumbnail( (String) name));

		return mapper;

	}
}
