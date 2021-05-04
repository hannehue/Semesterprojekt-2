package Java;

import java.util.ArrayList;
import java.util.Date;

public interface ICredit {

    //getters/setters for Credit class
    String getName();
    void setName(String name);

    Date getDateAdded();
    void setDateAdded(Date dateAdded);

    int getCreditID();
    void setCreditID(int creditID);

    String getDescription();
    void setDescription(String description);

    boolean isApproved();
    void setApproved(boolean approved);

/**
    //getters/setters for Person class
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


    //getter/setter for Episode class
    int getSeasonID();
    void setSeasonID(int seasonID);

    //getters/setters for Season class
    ArrayList<Episode> getEpisodes();
    void setEpisodes(ArrayList<Episode> episodes);

    int getShowID();
    void setShowID(int showID);


    //getter/setter for Show class
    ArrayList<Season> getSeasons();
    void setSeasons(ArrayList<Season> seasons);
    **/
}
