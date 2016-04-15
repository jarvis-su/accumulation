import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataRang {

    public static void main(String[] args) {

        List<Date> l1 = new ArrayList<>();
        List<Date> l2 = new ArrayList<>();
        List<Date> l3 = new ArrayList<>();
        List<Date> l4 = new ArrayList<>();
        List<Date> l5 = new ArrayList<>();
        List<Date> l6 = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d1 = "2015-01-01 13:01:00";
        String d2 = "2015-01-02 13:02:00";
        String d3 = "2015-01-02 14:13:00";
        String d4 = "2015-01-03 13:04:00";
        String d5 = "2015-01-04 13:05:00";
        String d6 = "2015-01-05 13:06:00";
        String d7 = "2015-01-05 16:07:00";

        String payDay = "2015-01-03 00:00:00";
        try {
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            Date date3 = sdf.parse(d3);
            Date date4 = sdf.parse(d4);
            Date date5 = sdf.parse(d5);
            Date date6 = sdf.parse(d6);
            Date date7 = sdf.parse(d7);

            l1.add(date2);
            l1.add(date1);

            l2.add(date4);
            l2.add(date3);
            l2.add(date1);

            l3.add(date7);
            l3.add(date6);
            l3.add(date4);


            Date payDate = sdf.parse(payDay);

            System.out.println(sdf.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println("sdafasdfasd");
    }
}
