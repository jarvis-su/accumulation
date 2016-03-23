import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by C5023792 on 12/2/2015.
 */
public class Test {


    public static void main(String[] args) {
        System.out.println("asdfasdfasd");
        Properties properties = System.getProperties();
        Iterator it = properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.print(entry.getKey() + "=");
            System.out.println(entry.getValue());
        }

        int a = 1;
        a += a + 2;
        System.out.println(a);

    }


}
