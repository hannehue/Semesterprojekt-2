package Java;

import java.util.ArrayList;
import java.util.Date;

public class Person extends Credit{
    private int personID;
    private ArrayList<Job> jobs;
    private String phoneNumber;
    private String personalInfo;
    private String personEmail;

    public Person(String name, Date dateAdded, int creditId, boolean approved,
                        String description, int personID, String phoneNumber, String personalInfo, String personEmail){
        super(name, dateAdded, creditId, approved, description);
        this.personID = personID;
        this.jobs = new ArrayList<Job>();
        this.phoneNumber = phoneNumber;
        this.personalInfo = personalInfo;
        this.personEmail = personEmail;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
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

    public String getEmail() {
        return personEmail;
    }

    @Override
    public String toString() {
        String jobString = "";
        for (Job job : jobs) {
            jobString += "\t" + job.toString() + "\n";
        }
        return "Name: " + getName() + ", PersonID: " + personID + "\n" +
                "Jobs:\n" + jobString;
    }
    @Override
    public String toFileString() {
        String personString = getName() + "," + getDateAdded() + "," + getCreditID() + "," + isApproved() +
                "," + getDescription() + "," + getPersonID() + "," + getPhoneNumber() + "," +
                getPersonalInfo() + "," + getEmail() + ",";

        //Koreografi;Fotografer--165--Mand i hættetrøje ved tanken;Hans Jensen,
        String jobString = "";

        for (Job j : getJobs()) {
            String roles = "";
            String characterNames = "";

            roles = j.getRole().toString();

            characterNames = j.getCharacterName();

            jobString += roles + "--" + j.getProgram() + "--" + characterNames + ",";
        }
        personString += jobString;

        System.out.println(personString);
        return personString;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }
}
