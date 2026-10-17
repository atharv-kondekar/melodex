package com.sunbeam.users.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.sunbeam.users.domain.User;

@Component
public class UserRowMapper implements RowMapper<User>{
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail( rs.getString("email"));
		user.setPasswd( rs.getString("passwd"));
		user.setAddr( rs.getString("addr"));
		user.setBirth(rs.getDate("birth"));
		user.setRole( rs.getString("role"));
		return user;
	}
}
