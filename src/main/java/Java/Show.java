package Java;

import java.util.ArrayList;
import java.util.Date;

public class Show extends Credit {

    private ArrayList <Season> seasons;

    public Show(String showName, Date dateadded, int showID, boolean approved,  String description){
        super(showName, dateadded, showID, approved, description);
        this.seasons = new ArrayList<>();
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
