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

    private static final ApplicationManager instance = new ApplicationManager();

    private ApplicationManager() {
    }

    public static ApplicationManager getInstance() {
        return instance;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
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

    public ArrayList<ICredit> search(String searchString){
        ArrayList<ICredit> creditsList = new ArrayList();
        creditsList.addAll(PersonManager.getInstance().searchPerson(searchString));
        creditsList.addAll(MovieManager.getInstance().searchMovie(searchString));
        return creditsList;
    }

}
