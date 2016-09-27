package com.smj.dbvariable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.acs.fileChecker.db.beans.TblVariable;
import com.acs.fileChecker.db.mysql.MySQLUtil;

public class VarCache {
    private static final VarCache _instance = new VarCache();
    private Map<String, DBVar> _cache;

    protected VarCache() {
        this._cache = new HashMap();
    }

    public static VarCache getInstance() {
        return _instance;
    }

    public static Vector<String> resolve(String s) throws Exception {
        return resolve(s, new Properties());
    }

    public static Vector<String> resolve(String s, Properties env) throws Exception {
        VarString vs = new VarString(s);
        // System.err.println("Resolving " + s);
        return vs.resolve(getInstance(), env);
    }

    public DBVar getVar(String s) {
        return (DBVar) this._cache.get(s);
    }

    public void putVar(String s, DBVar v) {
        this._cache.put(s, v);
    }

    public void load() {
        List<TblVariable> variables = null;
        try {
            variables = MySQLUtil
                    .getRunner()
                    .query("select name, value, type from tblVariable tv, tblVariableType tvt where tvt.varTypeId = tv.varTypeId",
                            new BeanListHandler<TblVariable>(TblVariable.class));
            for (TblVariable var : variables) {
                String name = var.getName();
                String val = var.getValue();
                String type = "com.smj.dbvariable." + var.getType();
                Class c1;
                if ((type == null) || ((c1 = Class.forName(type)) == null)) {
                    System.err.println("Variable type not found " + type);
                } else {
                    DBVar dbvar = (DBVar) c1.newInstance();
                    dbvar.setInitialValue(val);
                    putVar(name, dbvar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}