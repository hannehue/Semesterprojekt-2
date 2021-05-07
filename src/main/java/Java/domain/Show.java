package Java.domain;

import Java.interfaces.ISeason;
import Java.interfaces.IShow;

import java.util.ArrayList;
import java.util.Date;

public class Show extends Credit implements IShow {

    private ArrayList <ISeason> seasons;
    private boolean allSeasonApproved;

    public Show(String showName, Date dateadded, int showID, boolean approved,  String description, boolean allSeasonApproved){
        super(showName, dateadded, showID, approved, description);
        this.seasons = new ArrayList<>();
        this.allSeasonApproved = allSeasonApproved;
    }

    @Override
    public boolean isAllSeasonApproved() {
        return allSeasonApproved;
    }

    @Override
    public void setAllSeasonApproved(boolean allSeasonApproved) {
        this.allSeasonApproved = allSeasonApproved;
    }

    @Override
    public ArrayList<ISeason> getSeasons() {
        return seasons;
    }

    @Override
    public void setSeasons(ArrayList<ISeason> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toFileString() {
        String showString = "" + getName() + "::" + getDateAdded() + "::" + getCreditID() + "::" + isApproved() + "::" + getDescription() + "::" + getCreditID() + "::";

        for (ISeason s : seasons){
            showString += s.toFileString() + "++";
        }

        return showString;
    }

    public void addSeason(ISeason season) {
        this.seasons.add(season);
    }

    public int getNumberOfSeason() { return seasons.size();};

    @Override
    public String toString() {
        return this.getName();
    }
}
