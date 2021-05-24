package Java.domain;

import Java.data.DatabaseLoaderFacade;
import Java.domain.data.*;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;

public class ApplicationManager {

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
    public <T extends ICredit> void approveCredit(int id, ObservableList<T> observableList) {
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

    public ObservableList<ICredit> search(String searchString){
        ArrayList<ICredit> creditsList = new ArrayList();
        creditsList.addAll(PersonManager.getInstance().searchPerson(searchString));
        creditsList.addAll(MovieManager.getInstance().searchMovie(searchString));
        creditsList.addAll(ShowManager.getInstance().searchShows(searchString));
        //Opretter ny observableliste det endelige resultat bliver lagt i
        ObservableList<ICredit> observableList = FXCollections.observableArrayList();
        //går igennem hver credit
        for (ICredit e: creditsList) {
            observableList.add(e);
            /*
            //Opretter en ny stringbuilder for hver credit der er blevet returneret
            StringBuilder stringBuilder = new StringBuilder();
            //Splitter ved ","
            String[] observableResultsString = e.toString().split(",");
            //Tilføj hver linje der er blevet splittet til string builder
            for (String s: observableResultsString) { stringBuilder.append(s).append("\n"); }
            //tilføj til liste
            observableList.add(stringBuilder);
             */
        }

        return observableList;
    }

    public void searchShow( String search, ObservableList<IShow> list){
        list.addAll(DatabaseLoaderFacade.getInstance().searchShowsFromDatabase(search));
        //list.add(new Show("name", "desc", false));
    }

}
