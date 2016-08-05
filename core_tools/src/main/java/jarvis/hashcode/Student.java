package jarvis.hashcode;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by C5023792 on 8/5/2016.
 */
public class Student {
    private String name;
    private String firstName;
    private String lastName;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 0;
//        if (null != firstName) {
//            hash += firstName.hashCode();
//        }
//        if (null != lastName) {
//            hash += lastName.hashCode();
//        }
        hash = HashCodeBuilder.reflectionHashCode(this);
        return hash;
    }
}
