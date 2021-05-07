package Java.Application;

import java.util.ArrayList;

public interface IPerson extends ICredit{
    int getPersonID();
    void setPersonID(int personID);

    ArrayList<Job> getJobs();
    void setJobs(ArrayList<Job> jobs);

    String getPhoneNumber();
    void setPhoneNumber(String phoneNumber);

    String getPersonalInfo();
    void setPersonalInfo(String personalInfo);

    String getEmail();
    void setEmail(String email);

    String toFileString();
}
