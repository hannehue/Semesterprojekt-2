package Java.domain.data;

import Java.interfaces.IJob;
import Java.interfaces.IPerson;

import java.util.ArrayList;
import java.util.Date;

public class Person extends Credit implements IPerson {
    private int personID;
    private ArrayList<IJob> jobs;
    private String phoneNumber;
    private String personalInfo;
    private String personEmail;

    public Person(String name, Date dateAdded, int creditId, boolean approved,
                        String description, int personID, String phoneNumber, String personalInfo, String personEmail){
        super(name, dateAdded, creditId, approved, description);
        this.personID = personID;
        this.jobs = new ArrayList<IJob>();
        this.phoneNumber = phoneNumber;
        this.personalInfo = personalInfo;
        this.personEmail = personEmail;
    }

    public Person(String name, String description, String phoneNumber, String personalInfo, String personEmail){
        super(
                name,
                new Date(),
                0,
                false,
                description
        );
        this.personID = 0;
        this.jobs = new ArrayList<IJob>();
        this.phoneNumber = phoneNumber;
        this.personalInfo = personalInfo;
        this.personEmail = personEmail;
    }

    @Override
    public int getPersonID() {
        return personID;
    }

    @Override
    public void setPersonID(int personID) {
        this.personID = personID;
    }

    @Override
    public ArrayList<IJob> getJobs() {
        return jobs;
    }

    @Override
    public void setJobs(ArrayList<IJob> jobs) {
        this.jobs = jobs;
    }
    @Override
    public void addJob(IJob job){

        jobs.add(job);
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPersonalInfo() {
        return personalInfo;
    }

    @Override
    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    @Override
    public String getEmail() {
        return personEmail;
    }

    @Override
    public void setEmail(String email){
        this.personEmail = email;
    }

    @Override
    public String toString() {
        String jobString = "";
        for (IJob job : jobs) {
            jobString += "\t" + job.toString() + "\n";
        }
        return "Name: " + getName() + ", PersonID: " + personID + "\n" +
                "Jobs:\n" + jobString;
    }

    @Override
    public String getPersonEmail() {
        return personEmail;
    }

    @Override
    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }
}
