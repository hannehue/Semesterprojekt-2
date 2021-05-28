package Java.domain.objectMapping;

import Java.persistence.DatabaseLoaderFacade;
import Java.domain.services.*;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Factory {

    private static Factory instance;
    private SimpleDateFormat formatter;

    private Factory() {
        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    public ObservableList<IPerson> getPersons(String searchString) {
        ObservableList<IPerson> personObservableList = FXCollections.observableArrayList();
        ResultSet personsResultSet = DatabaseLoaderFacade.getInstance().searchPersonsFromDatabase(searchString);

        try {
            while (personsResultSet.next()) {
                IPerson tempPerson = Mapper.getInstance().mapPerson(personsResultSet);

                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID", tempPerson.getCreditID());
                IDs.put("personID", tempPerson.getPersonID());
                tempPerson.setIDMap(IDs);

                //Temporary job list
                ArrayList<IJob> jobs = new ArrayList<>();
                //Result set handling
                jobs.addAll(getInstance().getJobs(tempPerson));
                // Takes the temporary job list, and sets it to the current tempPerson
                tempPerson.setJobs(jobs);
                //Adds the current temp person to the observable list to be returned
                personObservableList.add(tempPerson);
            }
            return personObservableList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<IJob> getJobs(IPerson person) {
        ResultSet jobResultSet = DatabaseLoaderFacade.getInstance().getJobsForPerson(person);
        ArrayList<IJob> jobs = new ArrayList<>();
        try {
            while (jobResultSet.next()) {
                jobs.add(Mapper.getInstance().mapJob(jobResultSet));
            }
            return jobs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<IMovie> getMovies(String searchString) {
        ObservableList<IMovie> movieList = FXCollections.observableArrayList();
        ResultSet movieResultSet = DatabaseLoaderFacade.getInstance().searchMoviesFromDatabase(searchString);
        try {
            while (movieResultSet.next()) {
                IMovie movie = Mapper.getInstance().mapMovie(movieResultSet);
                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID", movie.getCreditID());
                IDs.put("productionID", movie.getProductionID());
                IDs.put("movieID", movieResultSet.getInt("movie_id"));
                movie.setIDMap(IDs);
                movieList.add(movie);
            }
            return movieList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<IShow> getShow(String searchString) {
        ResultSet showResultSet = DatabaseLoaderFacade.getInstance().searchShowsFromDatabase(searchString);
        ObservableList<IShow> showList = FXCollections.observableArrayList();
        try {
            while (showResultSet.next()) {
                Map<String, Integer> IDs = new HashMap<>();
                IShow show = Mapper.getInstance().mapShow(showResultSet);
                IDs.put("showID", showResultSet.getInt("show_id"));
                IDs.put("creditID", show.getCreditID());
                show.setIDMap(IDs);
                ObservableList<ISeason> seasons = FXCollections.observableArrayList();
                seasons.addAll(getInstance().getSeasonsForShow(show));
                show.setSeasons(seasons);
                showList.add(show);
            }
            return showList;
        } catch (SQLException e) {
            System.out.println("WENT WRONG AT SHOW MAPPING");
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<ISeason> getSeasonsForShow(IShow show) {
        ResultSet seasonResultSet = DatabaseLoaderFacade.getInstance().getFromDatabase(show);
        ObservableList<ISeason> seasonList = FXCollections.observableArrayList();
        try {

            while (seasonResultSet.next()) {
                ISeason season = Mapper.getInstance().mapSeason(seasonResultSet);
                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID", season.getCreditID());
                IDs.put("showID", season.getShowID());
                IDs.put("seasonID", seasonResultSet.getInt("season_id"));
                season.setIDMap(IDs);
                ObservableList<IEpisode> episodeObservableList = FXCollections.observableArrayList();
                episodeObservableList.addAll(getEpisodesForShow(season));
                season.setEpisodes(episodeObservableList);
                seasonList.add(season);
            }
            return seasonList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ObservableList<IEpisode> getEpisodesForShow(ISeason season) {
        ObservableList<IEpisode> episodeList = FXCollections.observableArrayList();
        ResultSet episodeResultSet = DatabaseLoaderFacade.getInstance().getFromDatabase(season);
        try {
            while (episodeResultSet.next()) {
                IEpisode episode = Mapper.getInstance().mapEpisode(episodeResultSet);
                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID", episode.getCreditID());
                IDs.put("productionID", episode.getProductionID());
                IDs.put("seasonID", episode.getSeasonID());
                IDs.put("episodeID", episodeResultSet.getInt("episode_id"));
                episode.setIDMap(IDs);
                episodeList.add(episode);
            }
            return episodeList;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getAllUnapprovedCredits(){
        try{
            ResultSet unapprovedResultSet = DatabaseLoaderFacade.getInstance().getAllUnApprovedCredits();
            while (unapprovedResultSet.next()) {
                /*
                String name = unapprovedResultSet.getString("name");
                Date date_added = formatter.parse(unapprovedResultSet.getString("date_added"));
                boolean approved = unapprovedResultSet.getBoolean("approved");
                String description = unapprovedResultSet.getString("description");

                 */
                int credit_id = unapprovedResultSet.getInt("credit_id");
                int person_id = unapprovedResultSet.getInt("person_id");
                int movie_id = unapprovedResultSet.getInt("movie_id");
                int show_id = unapprovedResultSet.getInt("show_id");
                int season_id = unapprovedResultSet.getInt("season_id");
                int episode_id = unapprovedResultSet.getInt("episode_id");
                if (person_id != 0) {
                    IPerson tempPerson = Mapper.getInstance().mapPerson(unapprovedResultSet);
                    /*
                            new Person(
                            name,
                            date_added,
                            credit_id,
                            approved,
                            description,
                            person_id,
                            unapprovedResultSet.getString("phone_number"),
                            unapprovedResultSet.getString("personal_info"),
                            unapprovedResultSet.getString("email")
                    );
                     */
                    PersonManager.getInstance().getPersonList().add(tempPerson);
                    System.out.println("person " + tempPerson);
                }else if (movie_id != 0) {
                    IMovie movie = Mapper.getInstance().mapMovie(unapprovedResultSet);
                            /*
                            new Movie(
                            name,
                            date_added,
                            credit_id,
                            approved,
                            description,
                            unapprovedResultSet.getInt("production_id"),
                            new Category[]{Category.values()[unapprovedResultSet.getInt("category_id") - 1]},
                            unapprovedResultSet.getInt("length_in_secs"),
                            formatter.parse(unapprovedResultSet.getString("release_date"))
                    );

                             */
                    MovieManager.getInstance().getMovies().add(movie);
                    System.out.println("movie " + movie);
                } else if (show_id != 0) {
                    IShow show = Mapper.getInstance().mapShow(unapprovedResultSet);
                            /*new Show(
                            name,
                            date_added,
                            credit_id,
                            approved,
                            description,
                            unapprovedResultSet.getBoolean("all_seasons_approved")
                    );

                             */
                    ShowManager.getInstance().getShowList();
                    System.out.println("show " + show);
                } else if (season_id != 0) {
                    ISeason season = Mapper.getInstance().mapSeason(unapprovedResultSet);
                            /* new Season(
                            name,
                            date_added,
                            credit_id,
                            approved,
                            description,
                            unapprovedResultSet.getInt("show_id"),
                            unapprovedResultSet.getBoolean("all_episodes_approved")
                    );

                             */
                    System.out.println("season " + season);
                    SeasonManager.getInstance().getSeasonList().add(season);
                }else if (episode_id != 0) {
                    IEpisode episode = Mapper.getInstance().mapEpisode(unapprovedResultSet);
                            /*new Episode(
                            name,
                            date_added,
                            credit_id,
                            approved,
                            description,
                            unapprovedResultSet.getInt("production_id"),
                            new Category[]{Category.values()[unapprovedResultSet.getInt("category_id") - 1]},
                            unapprovedResultSet.getInt("length_in_secs"),
                            formatter.parse(unapprovedResultSet.getString("release_date")),
                            unapprovedResultSet.getInt("season_id")
                    );

                             */
                    System.out.println("episode " + episode);
                    EpisodeManager.getInstance().getEpisodeList().add(episode);
                }
            }


        } catch (SQLException e){
            e.printStackTrace();
        }


    }

}
