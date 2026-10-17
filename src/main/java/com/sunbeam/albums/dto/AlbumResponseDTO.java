package com.sunbeam.albums.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlbumResponseDTO {
	private int id;
	private String title;
	private String thumbnail;
}