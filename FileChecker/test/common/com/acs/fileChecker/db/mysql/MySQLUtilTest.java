package com.acs.fileChecker.db.mysql;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.acs.fileChecker.db.beans.TblState;

public class MySQLUtilTest {

    @Test
    public void testQueryWithBeanHandler() {
        try {
            TblState state = MySQLUtil.getRunner().query("SELECT * FROM STATE where stateId = ?",
                    new BeanHandler<TblState>(TblState.class), 1);
            System.out.println(state.getGroupId() + "\t" + state.getStateName() + "\t" + state.getInstanceId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryWithBeanListHandler() {
        try {
            List<TblState> states = MySQLUtil.getRunner().query("SELECT * FROM STATE",
                    new BeanListHandler<TblState>(TblState.class));
            for (TblState tblState : states) {
                System.out.println(tblState.getGroupId() + "\t" + tblState.getStateName() + "\t"
                        + tblState.getInstanceId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryWithMapHandler() {
        try {
            Map<String, Object> results = MySQLUtil.getRunner().query("SELECT * FROM STATE", new MapHandler());

            for (Map.Entry<String, Object> entry : results.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryWithMapListHandler() {
        try {
            List<Map<String, Object>> results = MySQLUtil.getRunner()
                    .query("SELECT * FROM STATE", new MapListHandler());

            for (Map<String, Object> map : results) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + "\t" + entry.getValue());
                }
                System.out.println("---------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() {
        try {
            int count = MySQLUtil.getRunner().update("update STATE set stateName=? where stateId=?",new Object[]{"CAEBT",1});
            System.out.println("Update records:" + count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
