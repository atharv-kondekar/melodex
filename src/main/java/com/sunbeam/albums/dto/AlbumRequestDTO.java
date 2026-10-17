package com.sunbeam.albums.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AlbumRequestDTO {
	private String title;
	//Additional fields for the additional Data
	private MultipartFile thumbnail; // For file upload
}
