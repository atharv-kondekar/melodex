package com.sunbeam.albums.dao;

import java.util.List;
import java.util.Optional;

import com.sunbeam.albums.domain.Album;

public interface AlbumDao {
	int save(Album album);

	int update(Album album);

	int deleteById(int albumId);

	List<Album> findAll();

	Optional<Album> findById(int albumId);
}