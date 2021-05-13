package Java.domain;

import Java.data.DatabaseLoader;
import Java.domain.data.*;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationManager implements IDataProcessors {

    private DatabaseLoader dataLoader;
    private ObservableList<IPerson> personList = FXCollections.observableArrayList();
    private ObservableList<IMovie> movieList = FXCollections.observableArrayList();
    private ObservableList<IShow> showList = FXCollections.observableArrayList();
    private ObservableList<IJob> tempList = FXCollections.observableArrayList();
    private ObservableMap<Integer, ISeason> seasonMap = FXCollections.observableHashMap();
    private ObservableMap<Integer, IEpisode> episodeMap = FXCollections.observableHashMap();
    private UserType userType;
    private String searchFieldPlaceholder = "";

    private static ApplicationManager instance = new ApplicationManager();
    private int idTracker = 0; //should be moved to database (tracker id for Movie og Person)

    private ApplicationManager() {
        try{
            dataLoader = DatabaseLoader.getInstance();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        for (String[] strings: dataLoader.getPersonArraylist()){
            personList.add(dataLoader.queryToPerson(strings));
        }
        for (String[] strings: dataLoader.getMovieArrayList()){
            movieList.add(dataLoader.queryToMovie(strings));
        }

        System.out.println(personList.toString());
    }

    public static ApplicationManager getInstance() {
        return instance;
    }

    public int nextId() {
        int temp = idTracker;
        idTracker++;
        return temp;
    }

    @Override
    public void addPerson(String name, String description, String phoneNumber, String email) {
        IPerson person = new Person(
                name,
                new Date(),
                1, // change later
                false,
                description,
                1,
                phoneNumber,
                null,
                email);
        personList.add(person);
    }

    public void addTempJob(int personId, Role role, String charactername, int productionId) {
        tempList.add(new Job(
            personId,
                role,
                charactername,
                productionId
        ));
        System.out.println("temp job added");
    }

    public void addTempJob(int personId, Role role, int productionId) {
        tempList.add(new Job(
                personId,
                role,
                productionId
        ));
        System.out.println("temp job added");
    }

    @Override
    public void addJob(int productionId) {
        for (IJob job : tempList) {
            getPersonById(job.getPersonId()).addJob(job);
        }
        tempList.clear();
    }

    @Override
    public void addMovie(String name, String description, Category[] categories, int id, int length) {
        IMovie movie = new Movie(
                name,
                null,
                id,
                false,
                description,
                1, // Change later
                categories,
                length,
                null);
        movieList.add(movie);
        System.out.println(movie.getName());
    }

    @Override
    public void addShow(String title, String description) {
        IShow show = new Show(
                title,
                null,
                nextId(),
                false,
                "desc",
                true);
        showList.add(show);
        System.out.println("Added show: " + show.getName());
    }

    @Override
    public void addSeason(String description, int showId) {
        IShow show = getShowById(showId);
        ISeason season = new Season(
                "S" + (show.getNumberOfSeason() + 1),
                new Date(),
                nextId(),
                false,
                description,
                show.getCreditID(),
                false
        );
        show.getSeasons().add(season.getCreditID());
        seasonMap.put(season.getCreditID(), season);
        show.setAllSeasonApproved(false);
    }

    @Override
    public void addEpisode(String title, int length, int seasonId, int id) {
        ISeason season = seasonMap.get(seasonId);
        IEpisode episode = new Episode(
                getNextEpisode(season.getCreditID()) + " - " + title,
                new Date(),
                id,
                false,
                "description",
                nextId(),
                null,
                length,
                null,
                season.getCreditID()
        );
        season.getEpisodes().add(episode.getCreditID());
        episodeMap.put(episode.getCreditID(), episode);
        System.out.println("tilføjet " + episode.getName());
    }

    @Override
    public String getNextEpisode(Integer seasonId) {
        ISeason season = seasonMap.get(seasonId);
        String episodeString = season + "E" + (season.getNumberOfEpisode() + 1);
        return episodeString;
    }

    @Override
    public void onStop(){

        dataLoader.addCreditsToDatabase((ArrayList<IPerson>) personList);
        dataLoader.addCreditsToDatabase((ArrayList<IMovie>) movieList);
        dataLoader.addCreditsToDatabase((ArrayList<IShow>) showList);

        try {
            dataLoader.writeAllCredits();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<IPerson> getPersonList(){
        return  personList;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
    }

    public ObservableList<IShow> getShowList() {
        return showList;
    }

    public void clearTempJobs(){
        tempList.clear();
    }

    public ObservableList<IJob> getTempList() {
        return tempList;
    }

    public String getSearchFieldPlaceholder() {
        return searchFieldPlaceholder;
    }

    public void setSearchFieldPlaceholder(String searchFieldPlaceholder) {
        ApplicationManager.getInstance().searchFieldPlaceholder = searchFieldPlaceholder;
    }

    public IPerson getPersonById(int personId){
        for (IPerson person : personList){
            if (person.getCreditID() == personId){
                return person;
            }
        }
        return null;
    }

    public IShow getShowById(int showId){
        for (IShow show : showList){
            if (show.getCreditID() == showId){
                return show;
            }
        }
        return null;
    }


    public void approveSeason(int seasonId) {
        ISeason se = null;
        if (seasonMap.containsKey(seasonId)) {
            se = seasonMap.remove(seasonId);
            se.setApproved(true);
        }
        if (se != null) {
            seasonMap.put(seasonId, se);
        }
    }
    public void approveEpisode(int episodeId) {
        IEpisode ep = null;
        if (episodeMap.containsKey(episodeId)) {
            ep = episodeMap.remove(episodeId);
            ep.setApproved(true);
        }
        if (ep != null) {
           episodeMap.put(episodeId, ep);
        }
    }

    public ISeason getSeasonById(int seasonId) {
        return seasonMap.get(seasonId);
    }

    public ObservableMap<Integer, ISeason> getSeasonMap(){
        return seasonMap;
    }

    public ObservableMap<Integer, IEpisode> getEpisodeMap(){
        return episodeMap;
    }


    /**
     * Searches through the list of persons to return the found people.
     *
     * @param findPerson
     * @return
     */
    public ArrayList<ICredit> searchPerson(String findPerson) {
        ArrayList<ICredit> creditList = new ArrayList<>();
        for (ICredit person : getPersonList()) {
            if (person.getName().toLowerCase().contains(findPerson)) {
                creditList.add(person);
            }
        }
        return creditList;
    }

    public ObservableList<IPerson> getPersons(){
        return personList;
    }

    public ObservableList<IMovie> getMovies(){
        return movieList;
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

}
