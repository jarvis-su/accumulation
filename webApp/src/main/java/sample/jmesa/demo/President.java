package sample.jmesa.demo;

import java.io.Serializable;
import java.sql.Date;


public class President implements Serializable {
  
	private static final long serialVersionUID = 1L;
	private Integer id;
    private Name name;
    private String term;
    private Date born;
    private Date died;
    private String education;
    private String career;
    private String politicalParty;
    private Double salary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer presidentId) {
        this.id = presidentId;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public Date getDied() {
        return died;
    }

    public void setDied(Date died) {
        this.died = died;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPoliticalParty() {
        return politicalParty;
    }

    public void setPoliticalParty(String politicalParty) {
        this.politicalParty = politicalParty;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
