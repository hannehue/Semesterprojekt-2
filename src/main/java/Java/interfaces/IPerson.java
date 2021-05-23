package Java.interfaces;

import java.util.ArrayList;

public interface IPerson extends ICredit{
    int getPersonID();
    void setPersonID(int personID);

    ArrayList<IJob> getJobs();
    void setJobs(ArrayList<IJob> jobs);
    void addJob(IJob job);

    String getPhoneNumber();
    void setPhoneNumber(String phoneNumber);

    String getPersonalInfo();
    void setPersonalInfo(String personalInfo);

    String getEmail();
    void setEmail(String email);

    String getPersonEmail();
    void setPersonEmail(String personEmail);
}
