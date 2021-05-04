package Java;

import java.util.ArrayList;
import java.util.Date;

public class Person extends Credit{
    private int personID;
    private ArrayList<Job> jobs;
    private String phoneNumber;
    private String personalInfo;
    private String email;

    public Person(String name, Date dateAdded, int creditID, boolean approved,
                  String description, int personID, String phoneNumber, String personalInfo, String email){
        super(name, dateAdded, creditID, approved, description);
        this.personID = personID;
        this.jobs = new ArrayList<Job>();
        this.phoneNumber = phoneNumber;
        this.personalInfo = personalInfo;
        this.email = email;
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
    public ArrayList<Job> getJobs() {
        return jobs;
    }

    @Override
    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
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
        return email;
    }

    @Override
    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String toString() {
        String jobString = "";
        for (Job job : jobs) {
            jobString += "\t" + job.toString() + "\n";
        }
        return "Name: " + getName() + ", PersonID: " + personID + "\n" +
                "Email: " + email + "\n" +
                "Phone number: " + phoneNumber + "\n" +
                "Jobs:\n" + jobString +
                "PersonalInfo: " + personalInfo  + "\n";
    }
    @Override
    public String toFileString(){
            String personString = getName() + "," + getDateAdded() + "," + getCreditID() + "," + isApproved() +
                    "," + getDescription() + "," + getPersonID() + "," + getPhoneNumber() + "," +
                    getPersonalInfo() + "," + getEmail() + ",";

            //Koreografi;Fotografer--165--Mand i hættetrøje ved tanken;Hans Jensen,
            String jobString = "";

            for (Job j : getJobs()) {
                String roles = "";
                String characterNames = "";

                for (Role role : j.getRoles()) {
                    roles += role.toString() + ";";
                }
                roles = roles.substring(0, roles.length() - 1);

                for (String characterName : j.getCharacterNames()) {
                    characterNames += characterName + ";";
                }
                characterNames = characterNames.substring(0, characterNames.length() - 1);

                jobString += roles + "--" + j.getProgram() + "--" + characterNames + ",";
            }
            personString += jobString;

            System.out.println(personString);
            return personString;
    }
}
