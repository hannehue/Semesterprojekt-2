package Java;

import java.util.ArrayList;
import java.util.Date;

public class Show extends Credit{

    private ArrayList <Season> seasons;

    public Show(String name, Date dateAdded, int creditID, boolean approved, String description, int showID, ArrayList <Season> seasons){
        super(name, dateAdded, creditID, approved, description);
        this.seasons = seasons;
    }


    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public String toFileString() {
        return null;
    }
}
