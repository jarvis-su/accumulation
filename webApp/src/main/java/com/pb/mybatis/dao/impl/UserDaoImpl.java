package com.pb.mybatis.dao.impl;

import java.util.List;
import com.pb.mybatis.dao.UserDao;
import com.pb.mybatis.entity.User;
import com.pb.mybatis.mapper.UserMapper;
import com.pb.mybatis.util.UserPage;

public class UserDaoImpl implements UserDao{
    
	//在此处注入一个UserMapper
	private UserMapper userMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public void delete(Integer id) {
		userMapper.delete(id);
	}

	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

	@Override
	public User findById(Integer id) {
		return userMapper.findById(id);
	}

	@Override
	public void save(User user) {
		userMapper.save(user);
//		throw new RuntimeException("Error");		
	}

	@Override
	public void update(User user) {
		userMapper.update(user);		
	}

	@Override
	public List<User> findByPage(UserPage page) {
		return userMapper.findByPage(page);
	}

	@Override
	public Integer getCount(UserPage page) {
		return userMapper.getCount(page);
	}
}