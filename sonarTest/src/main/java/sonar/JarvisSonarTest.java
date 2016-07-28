package sonar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.acs.ecc.bs.EccTrxCmd;
import com.tps.eppic.EppicExceptions;

public class JarvisSonarTest extends EccTrxCmd {

    List<String> testList = new ArrayList<>();

    @Override
    protected void initAccount() throws EppicExceptions, SQLException {
    }

    @Override
    protected void executeHelper() throws EppicExceptions, SQLException {
        jarvisTest();
        testRemoveAll();
    }

    private int jarvisTest() {
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            prepStmt = _conn.prepareStatement("select 1 from dual", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException ee) {
            return 0;
        } finally {
            // EccUtil.releaseDBResource(this, prepStmt);
            ResourceUtil.releaseResource(rs, prepStmt);
        }

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    private void testRemoveAll() {
        testList.removeAll(testList);
    }

    private String stringTest() {
        String str = "";
        for (int i = 0; i < 100; i++) {
            str += i + "-";
        }
        return str;
    }

    private void compareTest() {
        Integer a = 0;
        Integer b = 1;
        //...
        if (a == a) { //self comparison
            System.out.println("foo");
        }
        if (2 + 1 * 12 == 2 + 1 * 12) { //selfcomparison
            System.out.println("foo");
        }//...

        if (a != a) { // always false
            System.out.println("foo");
        }
        if (a == b && a == b) { // if the first one is true, the second one is too
            System.out.println("foo");
        }
        if (a == b || a == b) { // if the first one is true, the second one is too
            System.out.println("foo");
        }

        int j = 5 / 5; //always 1
        int k = 5 - 5; //always 0

    }
}
