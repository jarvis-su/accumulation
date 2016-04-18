package com.jarvis.demo.manager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jarvis.supporter.logger.Log4jAdapter;

/**
 * Created by Jarvis on 4/14/16.
 */
public class BasicDao {
	
	protected Log4jAdapter logger = Log4jAdapter.getLog4jAdapter(this.getClass().getName());

    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
