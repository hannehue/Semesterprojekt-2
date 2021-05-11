package Java.domain;

import Java.data.DatabaseLoader;
import Java.domain.data.*;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationManager implements IDataProcessors {

    private DatabaseLoader dataLoader;
    private ArrayList<IPerson> personList = new ArrayList<>();
    private ArrayList<IMovie> movieList= new ArrayList<>();
    private ArrayList<IShow> showList = new ArrayList<>();
    private ObservableList<IJob> tempList = FXCollections.observableArrayList();
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
        show.getSeasons().add(season);
        show.setAllSeasonApproved(false);
    }

    @Override
    public void addEpisode(String title, int length, String show, String season, int id) {
        for (IShow s : showList) {
            if (s.getName() == show) {
                for (ISeason seasonToGet : s.getSeasons()) {
                    if (seasonToGet.getName() == season) {
                        IEpisode episode = new Episode(
                                getNextEpisode(show, season) + " - " + title,
                                new Date(),
                                id,
                                false,
                                "description",
                                nextId(),
                                null,
                                length,
                                null,
                                seasonToGet.getCreditID()
                        );
                        seasonToGet.addEpisode(episode);
                        seasonToGet.setAllEpisodeApproved(false);
                        System.out.println("tilføjet " + episode.getName());
                    }
                }
            }
        }

    }

    @Override
    public String getNextEpisode(String show, String season) {
        String episodeString = "";
        for (IShow s: showList) {
            if (s.getName() == show) {
                for (ISeason se: s.getSeasons()) {
                    if (se.getName() == season) {
                        episodeString = season + "E" + (se.getNumberOfEpisode() + 1);
                    }
                }
            }
        }
        return episodeString;
    }

    @Override
    public void onStop(){

        dataLoader.addCreditsToDatabase(personList);
        dataLoader.addCreditsToDatabase(movieList);
        dataLoader.addCreditsToDatabase(showList);

        try {
            dataLoader.writeAllCredits();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<IMovie> getMovieList() {
        return movieList;
    }


    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userTypeSetter) {
        userType = userTypeSetter;
    }

    public ArrayList<IShow> getShowList() {
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

    public IProduction getProduction(int productionID) {
        for (IMovie movie : movieList) {
            if (movie.getProductionID() == productionID) {
                return movie;
            }
        }

        for (IShow show : showList) {
            for (ISeason season : show.getSeasons()) {
                for (IEpisode episode : season.getEpisodes()) {
                    if (episode.getProductionID() == productionID) {
                        return episode;
                    }
                }
            }
        }
        return null;
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

    public ISeason getSeasonById(int showId, int seasonId) {
        IShow show = getShowById(showId);
        for (ISeason season : show.getSeasons()){
            return season;
        }
        return null;
    }

    public IEpisode getEpisodeById(int showId, int episodeId){
        IShow show = getShowById(showId);
        ArrayList<ISeason> seasons = show.getSeasons();
       for (ISeason season : seasons)
            for (IEpisode episode : season.getEpisodes()){
                if (episode.getCreditID() == episodeId) {
                    return episode;
                }
            }
       return null;
    }

    /**
     * Searches through the list of persons to return the found people.
     *
     * @param findPerson
     * @return
     */
    public ArrayList<ICredit> searchPerson(String findPerson) {
        ArrayList<ICredit> creditList = new ArrayList<>();
        for (ICredit person : personList) {
            if (person.getName().toLowerCase().contains(findPerson)) {
                creditList.add(person);
            }
        }
        return creditList;
    }

    public ArrayList<IPerson> getUnapprovedPersons(){
        ArrayList<IPerson> persons = personList;
        for (IPerson person : persons){
            if (!person.isApproved()){
                persons.remove(person);
            }
        }
        return persons;
    }

    public ArrayList<IMovie> getUnapprovedMovies(){
        ArrayList<IMovie> movies = movieList;
        for (IMovie movie : movies){
            if (!movie.isApproved()){
                movies.remove(movie);
            }
        }
        return movies;
    }

    public void setPersonApproved(int personId){
        for (ICredit personCredit: personList) {
            if (personCredit.getCreditID() == personId) {
                personCredit.setApproved(true);
            }
        }
    }

}
