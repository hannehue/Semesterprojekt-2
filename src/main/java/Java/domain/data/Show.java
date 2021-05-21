package Java.domain.data;

import Java.interfaces.ISeason;
import Java.interfaces.IShow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Show extends Credit implements IShow {

    private ObservableList<ISeason> seasons;
    private boolean allSeasonApproved;

    public Show(String showName, Date dateadded, int creditID, boolean approved,  String description, boolean allSeasonApproved){
        super(showName, dateadded, creditID, approved, description);
        this.seasons = FXCollections.observableArrayList();
        this.allSeasonApproved = allSeasonApproved;
    }

    public Show(String showName, String description, boolean allSeasonApproved){
        super(
                showName,
                new Date(),
                0,
                false,
                description
        );
        this.seasons = FXCollections.observableArrayList();
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
    public ObservableList<ISeason> getSeasons() {
        return seasons;
    }

    @Override
    public void setSeasons(ObservableList<ISeason> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toFileString() {
        String showString = "" + getName() + "::" + getDateAdded() + "::" + getCreditID() + "::" + isApproved() + "::" + getDescription() + "::" + getCreditID() + "::";
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
