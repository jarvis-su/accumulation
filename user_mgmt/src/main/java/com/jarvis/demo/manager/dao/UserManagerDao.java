package com.jarvis.demo.manager.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jarvis.users.User;

/**
 * Created by Jarvis on 4/16/16.
 */
public class UserManagerDao extends BasicDao {

	private static final String get_user_info_by_login_name = "select * from users u where u.login_name = ? ";

	public User getUserInfo(String loginName) {
		User activeUser = null;

		Object[] values = new Object[1];
		values[1] = loginName;
		List<?> list = jdbcTemplate.queryForList(get_user_info_by_login_name, values);

		Iterator<?> iterator = list.iterator();
		if (iterator.hasNext()) {
			Map<?, ?> user = (Map<?, ?>) iterator.next();
			activeUser = new User();
			activeUser.setUserId((Integer) user.get("user_id"));
			logger.debug(user.get("user_id"));
		}

		return activeUser;

	}

}
