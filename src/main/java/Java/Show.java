package Java;

import java.util.ArrayList;
import java.util.Date;

<<<<<<< HEAD
public class Show extends Credit {
=======
public class Show extends Credit{
>>>>>>> 9d578648e2173fdc7f09cf3d6f5c686b2bfcfe5b

    private ArrayList <Season> seasons;

    public Show(String showName, Date dateadded, int showID, boolean approved, ArrayList<Season> seasons, String description){
        super(showName, dateadded, showID, approved, description);
        this.seasons = seasons;
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
