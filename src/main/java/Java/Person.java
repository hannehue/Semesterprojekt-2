package Java;

import java.util.ArrayList;
import java.util.Date;

public class Person extends Credit{
    private int personId;
    private ArrayList<Job> jobs;
    private String phoneNumber;
    private String personalInfo;

    public Person(String name, Date dateAdded, int creditId, boolean approved,
                        String description, int personId, String phoneNumber, String personalInfo){
        super(name, dateAdded, creditId, approved, description);
        this.personId = personId;
        this.jobs = new ArrayList<Job>();
        this.phoneNumber = phoneNumber;
        this.personalInfo = personalInfo;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }
}
