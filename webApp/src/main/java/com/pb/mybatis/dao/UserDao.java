package com.pb.mybatis.dao;

import java.util.List;
import com.pb.mybatis.entity.User;
import com.pb.mybatis.util.UserPage;
/**
 * 定义实体内操作接口Dao
 * @author Voishion
 * @version 2012.12.30
 */
public interface UserDao {
	
	/**
	 * 保存
	 * @param user
	 */
	void save(User user);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<User> findAll();
	
	/**
	 * 按ID查询
	 * @param id
	 * @return
	 */
	User findById(Integer id);
	
	/**
	 * 删除
	 * @param user
	 */
	void delete(Integer id);
	
	/**
	 * 更新
	 * @param map
	 */
	void update(User user);
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<User> findByPage(UserPage page);
	
	/**
	 * 分页查询时得到符合条件的数据
	 * @param page
	 * @return
	 */
	Integer getCount(UserPage page);

}