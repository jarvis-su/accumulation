package jarvis.hashcode;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by C5023792 on 8/5/2016.
 */
public class HashcodeTest {
    public static void main(String[] args) {
        Student studentOne = new Student();
        studentOne.setName("aaa");
        studentOne.setAge(34);
        List<Student> listOne = new ArrayList<Student>();
        listOne.add(studentOne);
        Teacher teacherOne = new Teacher();
        teacherOne.setName("bbb");
        teacherOne.setAge(33);
        teacherOne.setStudent(listOne);

        Student studentTwo = new Student();
        studentTwo.setName("aaa");
        studentTwo.setAge(34);
        List<Student> listTwo = new ArrayList<Student>();
        listTwo.add(studentTwo);
        Teacher teacherTwo = new Teacher();
        teacherTwo.setName("bbb");
        teacherTwo.setAge(33);
        teacherTwo.setStudent(listTwo);

        System.out.println(teacherOne == teacherTwo);
        System.out.println(teacherOne.equals(teacherTwo));

        Student s1= new Student();
        s1.setFirstName("aaa");
        Student s2 = new Student();
        s2.setLastName("aaa");
        System.out.println("s1 hashcode is " + s1.hashCode());
        System.out.println("s2 hashcode is " + s2.hashCode());
    }
}
