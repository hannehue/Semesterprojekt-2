package Java;

import java.util.ArrayList;
import java.util.Date;

public class Show extends Credit {

    private ArrayList <Season> seasons;
    private boolean allSeasonApproved;

    public Show(String showName, Date dateadded, int showID, boolean approved,  String description, boolean allSeasonApproved){
        super(showName, dateadded, showID, approved, description);
        this.seasons = new ArrayList<>();
        this.allSeasonApproved = allSeasonApproved;
    }

    public boolean isAllSeasonApproved() {
        return allSeasonApproved;
    }

    public void setAllSeasonApproved(boolean allSeasonApproved) {
        this.allSeasonApproved = allSeasonApproved;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public void addSeason(Season season) {
        this.seasons.add(season);
    }

    public int getNumberOfSeason() { return seasons.size();};
}
