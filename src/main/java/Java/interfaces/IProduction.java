package Java.interfaces;

import Java.domain.data.Category;

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