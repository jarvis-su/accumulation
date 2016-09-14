package jarvis.inner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C5023792 on 8/26/2016.
 */
public class Test2 {
    public static void main(String[] args) {
        int loopCount = Integer.MAX_VALUE/50;
        System.out.println(loopCount);
        long begin = System.currentTimeMillis();
        List<PrevInOutDetail1> list = new ArrayList<PrevInOutDetail1>();
        for(int i = 0 ;i < loopCount;i++){
            list.add(new PrevInOutDetail1());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

}
