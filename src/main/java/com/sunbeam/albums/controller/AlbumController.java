package com.sunbeam.albums.controller;

import com.sunbeam.users.service.UserService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ValueRange;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.albums.domain.Album;
import com.sunbeam.albums.dto.AlbumRequestDTO;
import com.sunbeam.albums.dto.AlbumResponseDTO;
import com.sunbeam.albums.services.AlbumService;
import com.sunbeam.users.utils.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/albums")
public class AlbumController {

	private final UserService userService;
	private AlbumService albumService;

	public AlbumController(AlbumService albumService, UserService userService) {
		this.albumService = albumService;
		this.userService = userService;
	}

	@GetMapping
	public ApiResponse<List<AlbumResponseDTO>> getAllAlbums() {
		return ApiResponse.success( albumService.findAll());
	}

	@Value("${music.dirname}")
	private String musicDirPath;

	//Option 2 : High level File NIO & Spring resource
//	@GetMapping(value = "/image/{imageName}",produces = "image/jpg")
//	public Resource getAlbumImage(@PathVariable String imageName) {
//		Path imagePath = Paths.get(musicDirPath,imageName);
//
//		try {
//			Resource resource = new UrlResource(imagePath.toUri());
//			return resource;
//		} catch (Exception e) {
//			throw new RuntimeException("DOWNLOAD ERROR: " + e.getMessage());
//		}
//	}

	// This can work for any type
	//Option 2: High level file NIO & Spring Resource with "Dynamic Content Type"
	@GetMapping("/image/{imageName}")
	public ResponseEntity<Resource> getAlbumImage(
	        @PathVariable String imageName) throws MalformedURLException {

	    Path imagePath = Paths.get(musicDirPath, imageName);

	    Resource resource = new UrlResource(imagePath.toUri());

	    if (!resource.exists() || !resource.isReadable()) {
	        return ResponseEntity.notFound().build();
	    }

	    MediaType contentType = MediaTypeFactory
	            .getMediaType(imageName)
	            .orElse(MediaType.APPLICATION_OCTET_STREAM);

	    return ResponseEntity.ok()
	            .contentType(contentType)
	            .body(resource);
	}
	//Option 1: Upload Single file as @RequestParam & Save to disk using File NIO
/*	@PostMapping("/image")
	//HTML Form ->	<input type = "file" name="imageFile"> </>
	public ApiResponse<String> uploadImage(@RequestParam("imageFile") MultipartFile imgFile){
		try
		{
			String imageName = imgFile.getOriginalFilename();
			Path imagePath = Paths.get(musicDirPath,imageName);
			Files.write(imagePath,imgFile.getBytes());
			return ApiResponse.success("Image is Saved : "+imagePath.toString());
		}
		catch (Exception e) {
			throw new RuntimeException("Upload Error : "+e.getMessage());
		}

	}
*/
	//Option 2 : Upload Whole Album
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	//Don't use the @RequestBody because our album is form data not JSON data
	public ApiResponse<AlbumResponseDTO> addAlbum(@ModelAttribute AlbumRequestDTO albumRequestDTO)
			throws IOException{
//		try
//		{
			String fileName = albumRequestDTO.getThumbnail().getOriginalFilename();
			Path filePath = Paths.get(musicDirPath, fileName);
			Files.write(filePath, albumRequestDTO.getThumbnail().getBytes());

			return ApiResponse.success(albumService.addAlbum(albumRequestDTO));
//		} catch (Exception e) {
//			throw new RuntimeException("Album Upload error : "+e.getMessage());
//		}
	}
}

