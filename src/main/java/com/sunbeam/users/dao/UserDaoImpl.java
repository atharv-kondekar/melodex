package com.sunbeam.users.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sunbeam.users.domain.*;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{

	private JdbcTemplate jdbcTemplate;
	private UserRowMapper userRowMapper;

	public UserDaoImpl(JdbcTemplate jdbcTemplate , UserRowMapper userRowMapper) {
		this.jdbcTemplate =jdbcTemplate;
		this.userRowMapper=userRowMapper;
	}

	public int save(User user) {
		String sql = "INSERT INTO users VALUES (default,?,?,?,?,?,?)";
		int count = jdbcTemplate.update(sql,user.getName(),user.getEmail(),user.getPasswd()
				,user.getAddr(),user.getBirth(),user.getRole());

		Integer id = jdbcTemplate.queryForObject(
				"SELECT LAST_INSERT_ID()", Integer.class
				);
		user.setId(id);

		return count;
	}

	public int updateById(User user) {
		String sql = "UPDATE users SET name=?,email=?,passwd=?,addr=?,birth=?,role=? "
				+ "WHERE id = ?";
		int count = jdbcTemplate.update(sql,user.getName(),user.getEmail(),user.getPasswd()
				,user.getAddr(),user.getBirth(),user.getRole(),user.getId());

		return count;
	}

	public int updateEmail(String email , int id) {
		String sql = "UPDATE users SET email = ? WHERE id = ?";
		int count = jdbcTemplate.update(sql,email,id);
		return count;
	}

	public int deleteUser(int id) {
		String sql = "DELETE FROM users WHERE id = ?";
		int count = jdbcTemplate.update(sql,id);
		return count;
	}

	public List<User> findAll() {
		String sql = "SELECT * FROM users";
		List<User> userList = jdbcTemplate.query(sql,userRowMapper);
		return userList.isEmpty()?null:userList;
	}

	public User findUserById(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";
		List<User> userList = jdbcTemplate.query(sql,userRowMapper,id);
		return userList.isEmpty()?null:userList.get(0);
	}

	public Optional<User> findByEmail(String email) {
		String sql= "SELECT * FROM users WHERE email=?";
		List<User> userList = jdbcTemplate.query(sql,userRowMapper,email);
		return userList.isEmpty()?Optional.empty():Optional.of(userList.get(0));
	}
}
