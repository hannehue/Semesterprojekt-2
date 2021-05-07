package Java.domain;

import Java.data.DatabaseLoader;
import Java.interfaces.*;
import Java.presentation.CreditSystemController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationManager implements IDataProcessors {

    private DatabaseLoader dataLoader;
    private ArrayList<IPerson> personList = new ArrayList<>();
    private ArrayList<IMovie> movieList= new ArrayList<>();
    private ArrayList<IShow> showList = new ArrayList<>();
    private ArrayList<Job> tempList = new ArrayList<>();
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
        //creditList.addAll(dataLoader.readPersons());
        //[Role.getRoleFromString("Koreografi")], 234324,["pisboiii"])

        for (String[] strings: dataLoader.getPersonArraylist()){
            personList.add(dataLoader.queryToPerson(strings));
        }
        for (String[] strings: dataLoader.getMovieArrayList()){
            movieList.add(dataLoader.queryToMovie(strings));
        }

        System.out.println(personList.toString());

        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job(Role.MEDVIRKENDE, "mickey", 12312));
        jobs.add(new Job(Role.BILLED_OG_LYDREDIGERING, 12312) );
        IPerson thisa = (IPerson) personList.get(0);
        thisa.setJobs(jobs);
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

    private void addTempJob(Job job) {
        tempList.add(job);
        System.out.println("temp job added");

    }

    @Override
    public void addJob(int productionId) {
        for (int i = tempList.size()-1; i > -1; i--) {
            Job job = tempList.get(i);
            for (IPerson person: personList) {
                if (person.getCreditID() == job.getPersonId()) {
                    Job newJob;
                    if (job.getRole() == Role.SKUESPILLER) {
                        newJob = new Job(job.getRole(), job.getCharacterName(), productionId);
                    } else {
                        newJob = new Job(job.getRole(), productionId);
                    }
                    person.getJobs().add(newJob);
                }
            }
            tempList.remove(i);
        }

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
    public void addSeason(String description, String showList) {
        for (IShow s: showList) {
                    if (s.getName() == show) {
                        ISeason season = new Season(
                                "S" + (s.getNumberOfSeason() + 1),
                                new Date(),
                                nextId(),
                                false,
                                description,
                                s.getCreditID(),
                                false
                        );
                        s.getSeasons().add(season);
                        s.setAllSeasonApproved(false);
                        System.out.println("tilføjet " + season.getName() + " til show: " + s.getName());
                    }
                }
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
    private String getNextEpisode(String show, String season) {
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

    public ArrayList<IPerson> getPersonList() {
        return personList;
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

    public String getSearchFieldPlaceholder() {
        return searchFieldPlaceholder;
    }

    public void setSearchFieldPlaceholder(String searchFieldPlaceholder) {
        CreditSystemController.getInstance().searchFieldPlaceholder = searchFieldPlaceholder;
    }

    public ArrayList<IShow> getShowList() {
        return showList;
    }

    public String tempListToString() {
        String temp = "Ingen personer tilføjet";
        if (!tempList.isEmpty()) {
            temp = "";
            for (Job job: tempList) {
                for (ICredit person: personList) {
                    if (person.getCreditID() == job.getPersonId()){
                        if (job.getRole() == Role.SKUESPILLER) {
                            temp += job.getRole() + ": " + person.getName() + " - " + job.getCharacterName() + "\n";
                        } else {
                            temp += job.getRole() + ": " + person.getName() + "\n";
                        }
                    }
                }
            }
        } return temp;
    }

    public void deleteTempList() {
        for (int i = tempList.size()-1; i > -1; i--) {
            tempList.remove(i);
        }
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
}
