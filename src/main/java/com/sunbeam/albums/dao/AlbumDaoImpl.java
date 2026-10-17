package com.sunbeam.albums.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sunbeam.albums.domain.Album;

@Repository
@Transactional
public class AlbumDaoImpl implements AlbumDao{

	 private JdbcTemplate jdbcTemplate;
	 private AlbumRowMapper albumRowMapper;

	 public AlbumDaoImpl(JdbcTemplate jdbcTemplate, AlbumRowMapper albumRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.albumRowMapper = albumRowMapper;
	 }

	 public int save(Album album) {
		String sql = "INSERT INTO albums VALUES (default,?,?)";
		int count = jdbcTemplate.update(sql,album.getTitle(),album.getThumbnail());
		Integer id = jdbcTemplate.queryForObject(
				"SELECT LAST_INSERT_ID()" ,
				Integer.class);
		album.setId(id);

		return count;
	}

	 public List<Album> findAll() {
		String sql = "SELECT * FROM albums";
		List<Album> albums = jdbcTemplate.query(sql,albumRowMapper);
		return albums.isEmpty()?null:albums;
	}

	 public Optional<Album> findById(int albumId) {
			String sql = "SELECT * FROM albums WHERE id=?";
			List<Album> list = jdbcTemplate.query(sql, albumRowMapper, albumId);
			return list.isEmpty()? Optional.empty() : Optional.of(list.get(0));
	}

	 public int update(Album album) {
			String sql = "UPDATE albums SET title=?, thumbnail=? WHERE id=?";
			int count = jdbcTemplate.update(sql, album.getTitle(), album.getThumbnail(), album.getId());
			return count;
	}

	public int deleteById(int albumId) {
			String sql = "DELETE FROM albums WHERE id=?";
			int count = jdbcTemplate.update(sql, albumId);
			return count;
	}
}
