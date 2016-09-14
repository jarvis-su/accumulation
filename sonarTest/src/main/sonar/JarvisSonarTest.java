package sonar;

import java.util.ArrayList;
import java.util.List;

public class JarvisSonarTest {

    List<String> testList = new ArrayList<>();

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
