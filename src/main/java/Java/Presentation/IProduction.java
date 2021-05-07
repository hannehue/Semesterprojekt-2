package Java.Presentation;

import java.util.ArrayList;
import java.util.Date;

public interface IProduction extends ICredit {

    int getProductionID();
    int getLengthInSecs();

    Category[] getCategories();

    Date getReleaseDate();

    ArrayList<Integer> getStaffIDs();

    String toFileString();
}
