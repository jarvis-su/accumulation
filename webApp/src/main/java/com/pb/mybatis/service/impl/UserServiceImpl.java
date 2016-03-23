package com.pb.mybatis.service.impl;

import java.util.List;
import com.pb.mybatis.dao.UserDao;
import com.pb.mybatis.entity.User;
import com.pb.mybatis.service.UserService;
import com.pb.mybatis.util.UserPage;
/**
 * Service层User操作接口实现
 * @author Voishion
 * @version 2012.12.30
 */
public class UserServiceImpl implements UserService{

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void saveUser(User user) {
		userDao.save(user);		
	}

	@Override
	public void deleteUser(Integer id) {
		userDao.delete(id);
		
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);		
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findById(Integer id) {
		return userDao.findById(id);
	}

	@Override
	public List<User> findByPage(UserPage page) {
		return userDao.findByPage(page);
	}

	@Override
	public Integer getCount(UserPage page) {
		return userDao.getCount(page);
	}

}