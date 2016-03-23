package com.pb.mybatis.service;

import java.util.List;

import com.pb.mybatis.entity.User;
import com.pb.mybatis.util.UserPage;

/**
 * Service层User操作接口
 * @author Voishion
 * @version 2012.12.30
 */
public interface UserService {
	
	void saveUser(User user);

	void updateUser(User user);
	
	void deleteUser(Integer id);
	
	User findById(Integer id);
	
	List<User> findAll();
	
	List<User> findByPage(UserPage page);
	
	Integer getCount(UserPage page);
}