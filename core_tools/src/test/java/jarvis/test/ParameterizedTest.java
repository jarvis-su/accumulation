package jarvis.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * ParameterizedTest
 *
 * @author Jarvis Su
 * @date 3/21/2016
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {
    private String extected;
    private String target;

     public ParameterizedTest(String target, String extected) {
        super();
        this.target = target;
        this.extected = extected;
    }

    @Parameters
    public static Collection<?> contructData() {
        return Arrays.asList({"aaa", "AAA"},
                {null, null},
                {"", ""},
                {"BBB", "BBB"});
    }

    @Parameterized.Parameters
    public static Collection<?> contructData1() {
        return Arrays.asList({"aaa1", "AAA1"},
                {null, null},
                {"", ""},
                {"BBB1", "BBB1"});
    }

    public String toUpperCase(String str) {
        if (null == str) {
            return str;
        }
        return str.toUpperCase();
    }

    @Test
    public void testUpperCase() {
        Assert.assertEquals(extected, toUpperCase(target));
    }
}
