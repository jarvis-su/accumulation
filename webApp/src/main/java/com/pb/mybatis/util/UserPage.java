package com.pb.mybatis.util;

import com.pb.mybatis.entity.User;

/**
 * User 分页查询公共条件类
 * @author Voishion
 */
public class UserPage {
	Integer firstRec;
	Integer pageSize;
	User user;
	public Integer getFirstRec() {
		return firstRec;
	}
	public void setFirstRec(Integer firstRec) {
		this.firstRec = firstRec;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}	
}