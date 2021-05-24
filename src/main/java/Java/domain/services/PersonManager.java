package Java.domain.services;

import Java.data.DatabaseLoader;
import Java.data.DatabaseLoaderFacade;
import Java.domain.ApplicationManager;
import Java.domain.data.Person;
import Java.interfaces.ICredit;
import Java.interfaces.IPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class PersonManager {
    private static PersonManager instance = new PersonManager();
    private DatabaseLoaderFacade database = DatabaseLoaderFacade.getInstance();
    private PersonManager(){}

    public static PersonManager getInstance() {
        return instance;
    }

    private final ObservableList<IPerson> personList = FXCollections.observableArrayList();

    public void addPerson(String name, String description, String phoneNumber, String personalInfo, String email) {
        IPerson person = new Person(
                name,
                description,
                phoneNumber,
                personalInfo,
                email);
        Map<String, Integer> IDs = DatabaseLoaderFacade.getInstance().putInDatabase(person);
        person.setCreditID(IDs.get("creditID"));
        person.setPersonID(IDs.get("personID"));

    }

    public IPerson getPersonById(int personCreditId){
        for (IPerson person : personList){
            if (person.getCreditID() == personCreditId){
                return person;
            }
        }
        return null;
    }


    public ObservableList<IPerson> getPersonList(){
        return personList;
    }



    /**
     * Searches through the list of persons to return the found people.
     *
     * @param findPerson
     * @return
     */
    public ArrayList<IPerson> searchPerson(String findPerson) {
        ArrayList<IPerson> people = new ArrayList<>();
        people.addAll(DatabaseLoaderFacade.getInstance().searchPersonsFromDatabase(findPerson));
        personList.setAll(people);
        return people;
    }

}
