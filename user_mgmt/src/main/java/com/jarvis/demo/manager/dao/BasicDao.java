package com.jarvis.demo.manager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Jarvis on 4/14/16.
 */
public class BasicDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

}
