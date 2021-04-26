package Java;

import java.util.ArrayList;
import java.util.Date;

public class Show extends Credit{

    private String title;
    private int showID;
    private ArrayList <Season> seasons;

    public Show(String title, Date dateAdded, int creditID, boolean approved, String description, int showID, ArrayList <Season> seasons){
        super(dateAdded, creditID, approved, description);
        this.title = title;
        this.showID = showID;
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
        String showString = "" + title + "::" + getDateAdded() + "::" + getCreditID() + "::" + isApproved() + "::" + getDescription() + "::" + showID + "::";

        for (Season s : seasons){
            showString += s.toFileString() + "++";
        }

        return showString;
    }
}
