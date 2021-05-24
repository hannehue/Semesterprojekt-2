package Java.domain;

import Java.data.DatabaseLoader;
import Java.domain.data.*;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.IOException;
import java.util.ArrayList;

public class ApplicationManager {

    private DatabaseLoader dataLoader;
    private UserType userType;
    private String searchFieldPlaceholder = "";

    private static final ApplicationManager instance = new ApplicationManager();
    private int idTracker = -1; //should be moved to database (tracker id for Movie og Person)

    private ApplicationManager() {
    }

    public static ApplicationManager getInstance() {
        return instance;
    }

    public int nextId() {
        int temp = idTracker;
        idTracker--;
        return temp;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
    }


    public String getSearchFieldPlaceholder() {
        return searchFieldPlaceholder;
    }

    public void setSearchFieldPlaceholder(String searchFieldPlaceholder) {
        ApplicationManager.getInstance().searchFieldPlaceholder = searchFieldPlaceholder;
    }

    /**
     * Aprrove some credit with an id, from a list.
     * @param id
     * @param observableList
     * @param <T>
     */
    public  <T extends ICredit> void approveCredit(int id, ObservableList<T> observableList) {
        T approveCredit = null;
        for (T credit: observableList) {
            if (credit.getCreditID() == id) {
                approveCredit = credit;
            }
        }
        if (approveCredit != null){
            observableList.remove(approveCredit);
            approveCredit.setApproved(true);
            observableList.add(approveCredit);
        }
    }


    public <T extends ICredit> void approveCredit(int id, ObservableMap<Integer,T> map) {
        T credit = null;
        if (map.containsKey(id)) {
            credit = map.remove(id);
            credit.setApproved(true);
        }
        if (credit != null) {
            map.put(id, credit);
        }
    }

    public ObservableList<StringBuilder> search(String searchString){
        ArrayList<ICredit> creditsList = new ArrayList();
        creditsList.addAll(PersonManager.getInstance().searchPerson(searchString));
        creditsList.addAll(MovieManager.getInstance().searchMovie(searchString));
        //Opretter ny observableliste det endelige resultat bliver lagt i
        ObservableList<StringBuilder> observableList = FXCollections.observableArrayList();
        //går igennem hver credit
        for (ICredit e: creditsList) {
            //Opretter en ny stringbuilder for hver credit der er blevet returneret
            StringBuilder stringBuilder = new StringBuilder();
            //Splitter ved ","
            String[] observableResultsString = e.toString().split(",");
            //Tilføj hver linje der er blevet splittet til string builder
            for (String s: observableResultsString) { stringBuilder.append(s).append("\n"); }
            //tilføj til liste
            observableList.add(stringBuilder);
        }

        return observableList;
    }

}
