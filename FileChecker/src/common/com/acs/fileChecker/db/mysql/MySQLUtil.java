package com.acs.fileChecker.db.mysql;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.acs.fileChecker.common.Constant;
import com.acs.fileChecker.common.PropertyFileReader;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * MySQLUtil based on Apache Common DbUtils. 
 * How to use? Please refer to MySQLUtilTest.
 * 
 * @author Rui
 * 
 */
public class MySQLUtil {

    private static QueryRunner runner = null;
    private static MysqlDataSource mysqlDataSource = null;

    static {
        // init by default config in common jar
        DbUtils.loadDriver("com.mysql.jdbc.Driver");
        try {
            PropertyFileReader reader = new PropertyFileReader(Constant.DEFAULT_DB_CONFIG_FILE, null);
            String host = reader.retrieveValueInstance("mysql.database.host");
            String port = reader.retrieveValueInstance("mysql.database.port");
            String userId = reader.retrieveValueInstance("mysql.database.user");
            String password = reader.retrieveValueInstance("mysql.database.pass");
            String schema = reader.retrieveValueInstance("mysql.database.schema");
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL("jdbc:mysql://" + host + ":" + port + "/" + schema);
            mysqlDataSource.setUser(userId);
            mysqlDataSource.setPassword(password);
            runner = new QueryRunner(mysqlDataSource);
        } catch (Exception e) {
        }
    }

    /**
     * ReInit Data Source, instead of default data source.
     * 
     * @param dataSource
     */
    public static void init(String host, String port, String schema, String userId, String password) {
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://" + host + ":" + port + "/" + schema);
        mysqlDataSource.setUser(userId);
        mysqlDataSource.setPassword(password);
        runner = new QueryRunner(mysqlDataSource);
    }

    /**
     * Get Runner Instance.
     * @return
     */
    public static QueryRunner getRunner() {
        return runner;
    }
    
}
