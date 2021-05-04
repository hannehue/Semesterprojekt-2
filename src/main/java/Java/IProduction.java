package Java;

import java.util.ArrayList;
import java.util.Date;

public interface IProduction {

    int getProductionID();
    int getLengthInSecs();

    Category[] getCategories();

    Date getReleaseDate();

    ArrayList<Integer> getStaffIDs();

}
