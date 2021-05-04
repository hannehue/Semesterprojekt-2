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

<<<<<<< HEAD
    @Override
=======
    public boolean isAllSeasonApproved() {
        return allSeasonApproved;
    }

    public void setAllSeasonApproved(boolean allSeasonApproved) {
        this.allSeasonApproved = allSeasonApproved;
    }

>>>>>>> Hans_Structure
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

<<<<<<< HEAD
    @Override
=======
>>>>>>> Hans_Structure
    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toFileString() {
        String showString = "" + getName() + "::" + getDateAdded() + "::" + getCreditID() + "::" + isApproved() + "::" + getDescription() + "::" + getCreditID() + "::";

        for (Season s : seasons){
            showString += s.toFileString() + "++";
        }

        return showString;
    }

    public void addSeason(Season season) {
        this.seasons.add(season);
    }

    public int getNumberOfSeason() { return seasons.size();};
}
