package Java.data;

import Java.domain.data.*;
import Java.interfaces.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.*;
import java.sql.*;

public class DatabaseLoader {

    private static DatabaseLoader instance;

    private final SimpleDateFormat formatter;

    private Connection connection;


    private DatabaseLoader() {

        formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        //Create connection to database
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            this.connection = DriverManager.getConnection(
                    "jdbc:postgresql://hattie.db.elephantsql.com:5432/bpmfwbjk",
                    "bpmfwbjk",
                    "Q2w3lGuOmhrrTotMtUu8dyp6Hh4tbbl6");
        } catch (SQLException e) {
            System.out.println("Went wrong at connection creation");
            e.printStackTrace();
        }
    }

    static DatabaseLoader getInstance() {
        if (instance == null) {
            instance = new DatabaseLoader();
        }
        return instance;
    }

    public IPerson searchQueryToPerson(String searchString) {
        //Deaclare person to be returned
        IPerson tempPerson = null;
        //Query & person creation try block
        try {
            //PreparedStatement that gets all results from credits, persons, jobs & job_roles tables where a name looks
            //like the search string, case insensitive
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                    "SELECT * FROM credits, persons, jobs, job_roles WHERE LOWER(name) LIKE LOWER(?)"
            );
            //inserts searchString into SQL statement
            queryStatement.setString(1, "%" + searchString + "%");
            //gets a SINGLE result set that matches query. This most likely need to be reworked!!!!
            ResultSet queryResult = queryStatement.executeQuery();
            //Instantiates a new person in the tempPerson variable
            while (queryResult.next()) {
                System.out.println(queryResult.toString());
                tempPerson = new Person(
                        /* Name         */ queryResult.getString("name"),
                        /* Date         */ formatter.parse(queryResult.getString("date_added")),
                        /* CreditID     */ queryResult.getInt("credit_id"),
                        /* Approved     */ queryResult.getBoolean("approved"),
                        /* description  */ queryResult.getString("description"),
                        /* personID     */ queryResult.getInt("person_id"),
                        /* phone number */ queryResult.getString("phone_number"),
                        /* personal info*/ queryResult.getString("personal_info"),
                        /* email        */ queryResult.getString("email")
                );
                //JobList for person. ALSO NEEDS TO BE REWORKED FOR EVENTUAL MULTIPLE JOBS
                ArrayList<IJob> jobs = new ArrayList<>();
                jobs.add(new Job(queryResult.getInt("person_id"), ((Role.values()[queryResult.getInt("job_role_id") - 1])), queryResult.getInt("production_id")));
                tempPerson.setJobs(jobs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SQL ERROR at queryToPerson");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Parse error at queryToPerson");
        }
        return tempPerson;
    }

    //Needs to be implemented to return an arraylist of matching searches
    public ArrayList<IPerson> searchQueryToPersonList(String searchString) {
        ArrayList<IPerson> personObservableList = new ArrayList<>();
        try {
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                    "SELECT * FROM credits, persons WHERE LOWER(name) LIKE LOWER(?) " +
                            "AND credits.credit_id = persons.credit_id " /* +
                            "AND jobs.person_id = persons.person_id " +
                            "AND jobs.job_role_id = job_roles.job_role_id" */
            );
            queryStatement.setString(1, "%" + searchString + "%");
            queryStatement.executeQuery();
            while (queryStatement.getResultSet().next()) {
                ResultSet queryResult = queryStatement.getResultSet();
                System.out.println(queryResult.getString("name"));
                IPerson tempPerson = new Person(
                        /* Name         */ queryResult.getString("name"),
                        /* Date         */ formatter.parse(queryResult.getString("date_added")),
                        /* CreditID     */ queryResult.getInt("credit_id"),
                        /* Approved     */ queryResult.getBoolean("approved"),
                        /* description  */ queryResult.getString("description"),
                        /* personID     */ queryResult.getInt("person_id"),
                        /* phone number */ queryResult.getString("phone_number"),
                        /* personal info*/ queryResult.getString("personal_info"),
                        /* email        */ queryResult.getString("email")
                );
                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID" ,tempPerson.getCreditID());
                IDs.put("personID" ,tempPerson.getPersonID());

                // Job handling
                // Runs new query, using persons, jobs and job_roles
                PreparedStatement jobQueryStatement = getInstance().connection.prepareStatement(
                        "SELECT * FROM persons, jobs, job_roles WHERE jobs.person_id = persons.person_id " +
                                "AND jobs.job_role_id = job_roles.job_role_id"
                );
                jobQueryStatement.executeQuery();
                //Temporary job list
                ArrayList<IJob> jobs = new ArrayList<>();
                //Result set handling
                while (jobQueryStatement.getResultSet().next()) {
                    //Gets net result set
                    ResultSet jobResult = jobQueryStatement.getResultSet();
                    // Job initialization
                    // Checks whether the current job belongs to the current tempPerson, from the first query
                    if (tempPerson.getPersonID() == jobResult.getInt("person_id")) {
//                        if (jobResult.getInt("job_role_id") != 1) {
                            jobs.add(new Job(
                                    /* PersonID         */        jobResult.getInt("person_id"),
                                    /* Role from roleID */ ((Role.values()[jobResult.getInt("job_role_id") - 1])),
                                    /* ProductionID     */ jobResult.getInt("production_id")));
//                        } else {
//                            jobs.add(new Job(
//                                    /* PersonID         */        jobResult.getInt("person_id"),
//                                    /* Role from roleID */ ((Role.values()[jobResult.getInt("job_role_id") - 1])),
//                                    /* Character name   */ /* need name in database */
//                                    /* ProductionID     */ jobResult.getInt("production_id")));
//                        }
                    }
                }
                // Takes the temporary job list, and sets it to the current tempPerson
                tempPerson.setJobs(jobs);

                //Adds the current temp person to the observable list to be returned
                personObservableList.add(tempPerson);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SQL ERROR at queryToPerson");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Parse error at queryToPerson");
        }
        return personObservableList;
    }

    public ArrayList<IMovie> searchQueryToMovieList(String searchString) {
        try {
            ArrayList<IMovie> movieList = new ArrayList<>();
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                    "SELECT * FROM credits, movies, productions, categories WHERE LOWER(name) LIKE LOWER(?)" +
                            "AND credits.credit_id = productions.credit_id " +
                            "AND productions.production_id = movies.production_id " +
                            "AND productions.category_id = categories.category_id"
            );
            //inserts searchString into SQL statement
            queryStatement.setString(1, "%" + searchString + "%");
            queryStatement.executeQuery();

            while (queryStatement.getResultSet().next()) {
                ResultSet queryResult = queryStatement.getResultSet();
                IMovie movie = new Movie(
                        /* Name         */ queryResult.getString("name"),
                        /* Date         */ formatter.parse(queryResult.getString("date_added")),
                        /* CreditID     */ queryResult.getInt("credit_id"),
                        /* Approved     */ queryResult.getBoolean("approved"),
                        /* description  */ queryResult.getString("description"),
                        /* productionID */ queryResult.getInt("production_id"),
                        /** Genovervej **/
                        /* category     */ new Category[]{Category.values()[queryResult.getInt("category_id") - 1]},
                        /** .... **/
                        /* lengthInSecs */ queryResult.getInt("length_in_secs"),
                        /* Releasedate  */ formatter.parse(queryResult.getString("release_date"))
                );
                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID" ,movie.getCreditID());
                IDs.put("productionID" ,movie.getProductionID());
                IDs.put("movieID" ,queryResult.getInt("movie_id"));
                movieList.add(movie);
            }
            return movieList;

        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Failed when initializing movie search string");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception at search query to Movie");
        }
        return null;
    }

    public ArrayList<IShow> searchQueryToShowList(String searchString){
        try {
            ArrayList<IShow> showList = new ArrayList<>();
            PreparedStatement searchQuery = getConnection().prepareStatement(
                "SELECT * FROM credits, shows WHERE LOWER(name) LIKE LOWER(?)" +
                        "AND credits.credit_id = shows.credit_id"
            );
            searchQuery.setString(1, "%" + searchString + "%");
            searchQuery.executeQuery();

            while (searchQuery.getResultSet().next()){
                ResultSet queryResult = searchQuery.getResultSet();
                Map<String, Integer> IDs = new HashMap<>();
                IShow show =                         new Show(
                        /* Name         */ queryResult.getString("name"),
                        /* Date         */ formatter.parse(queryResult.getString("date_added")),
                        /* CreditID     */ queryResult.getInt("credit_id"),
                        /* Approved     */ queryResult.getBoolean("approved"),
                        /* description  */ queryResult.getString("description"),
                        /* IsAllSeasonsApproved*/ queryResult.getBoolean("all_seasons_approved")
                );
                IDs.put("showID", queryResult.getInt("show_id"));
                IDs.put("creditID", show.getCreditID());
                show.setIDMap(IDs);
                showList.add(show);
            }
            return showList;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL Exception at search query to Show");
        } catch (ParseException e) {
            System.out.println("Parse exception a search query to show");
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<ISeason> queryGetSeasonForShow(IShow show){

        try {
            ArrayList<ISeason> seasonList = new ArrayList<>();
            PreparedStatement getSeasonQuery = getConnection().prepareStatement(
                    "SELECT * FROM credits, seasons WHERE seasons.show_id = ?" +
                            "AND credits.credit_id = seasons.credit_id"
            );
            getSeasonQuery.setInt(1, show.getIDMap().get("showID"));
            getSeasonQuery.executeQuery();

            while (getSeasonQuery.getResultSet().next()){
                ResultSet seasonResult = getSeasonQuery.getResultSet();
                ISeason season = new Season(
                        /* Name         */ seasonResult.getString("name"),
                        /* Date         */ formatter.parse(seasonResult.getString("date_added")),
                        /* CreditID     */ seasonResult.getInt("credit_id"),
                        /* Approved     */ seasonResult.getBoolean("approved"),
                        /* description  */ seasonResult.getString("description"),
                        /* showID       */ seasonResult.getInt("show_id"),
                        /* AllEpisodesApproved*/ seasonResult.getBoolean("all_episodes_approved")
                );
                Map<String, Integer> IDs = new HashMap<>();
                IDs.put("creditID", season.getCreditID());
                IDs.put("showID", season.getShowID());
                IDs.put("seasonID", seasonResult.getInt("season_id"));
                seasonList.add(season);
            }
            return seasonList;


        } catch (SQLException e){
            System.out.println("ERROR AT queryGetSeasonForShow");
        } catch (ParseException e) {
            System.out.println("PARSE ERROR AT queryGetSeasonForShow");
            e.printStackTrace();

        }

        return null;
    }

    public IGroup queryToGroup(String[] strings) {
        IGroup tempGroup = null;
        try {
            tempGroup = new Group(strings[0], formatter.parse(strings[1]), Integer.parseInt(strings[2]),
                    Boolean.parseBoolean(strings[3]), strings[4], Integer.parseInt(strings[5]));
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Failed when initializing group from string array");
            return null;
        }
        return tempGroup;
    }

    public IEpisode queryToEpisode(String[] strings) {
        IEpisode tempEpisode = null;
        try {
            tempEpisode = new Episode(strings[0], formatter.parse(strings[1]), Integer.parseInt(strings[2]),
                    Boolean.parseBoolean(strings[3]), strings[4], Integer.parseInt(strings[5]),
                    Category.getCategoriesFromString(strings[6]), Integer.parseInt(strings[7]), formatter.parse(strings[8])
                    , Integer.parseInt(strings[10]));

            for (String staff : new ArrayList<String>(Arrays.asList(strings[9].split(";")))) {
                tempEpisode.addStaffID(Integer.parseInt(staff));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Failed when initializing episode from string array");
            return null;
        }
        return tempEpisode;
    }

    public Map<String, Integer> addPersonToDatabase(IPerson person) throws SQLException {
        // Set autoCommit to false, so only both prepared statements run
        getConnection().setAutoCommit(false);
        Savepoint beforeAddPerson = getConnection().setSavepoint();
        try {
            int creditID = addCreditToDatabase(person);
            // insert statement to insert indo into persons table
            PreparedStatement insertPerson = getConnection().prepareStatement(
                    "INSERT INTO persons(credit_id, phone_number, email, personal_info)"
                    + "VALUES(?, ?, ?, ?)"
                    //set prepared statement to return generated person_ID
            , Statement.RETURN_GENERATED_KEYS);
            //insert values from IPerson to preparedStatement
            insertPerson.setInt(1, creditID);
            insertPerson.setString(2, person.getPhoneNumber());
            insertPerson.setString(3, person.getPersonEmail());
            insertPerson.setString(4, person.getPersonalInfo());
            //execute but NOT commit
            insertPerson.executeUpdate();
            // get generated person_ID
            insertPerson.getGeneratedKeys().next();
            int personID = insertPerson.getGeneratedKeys().getInt(1);
            // Insert generated IDs to a map, with corresponding key names
            Map<String, Integer> returnIDs = new HashMap<>();
            returnIDs.put("creditID", creditID);
            returnIDs.put("personID", personID);
            //commit changes to database
            getConnection().commit();
            //set auto commit to true again, as that is the default
            getConnection().setAutoCommit(true);
            //return ID map
            return returnIDs;
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR AT ADD PERSON TO DATABASE");
            getConnection().rollback(beforeAddPerson);
            getConnection().setAutoCommit(true);
        }
        System.out.println("No user added");
        return null;
    }

    public int addCreditToDatabase(ICredit credit) throws SQLException{
        PreparedStatement insertCredit = getConnection().prepareStatement(
                "INSERT INTO credits(name, date_added, approved, description)"
                        + "VALUES(?, ?, ?, ?)"
                , Statement.RETURN_GENERATED_KEYS);

        insertCredit.setString(1, credit.getName());
        insertCredit.setString(2, credit.getDateAdded().toString());
        insertCredit.setBoolean(3, credit.isApproved());
        insertCredit.setString(4, credit.getDescription());
        insertCredit.executeUpdate();

        insertCredit.getGeneratedKeys().next();
        return insertCredit.getGeneratedKeys().getInt(1);
    }

    public int addProductionToDatabase(IProduction production, int creditID) throws SQLException{
        PreparedStatement insertProduction = getConnection().prepareStatement(
                "INSERT INTO productions(credit_id, category_id, length_in_secs, release_date)"
                        + "VALUES(?, ?, ?, ?)"
                , Statement.RETURN_GENERATED_KEYS);

        insertProduction.setInt(1, creditID);
        insertProduction.setInt(2, Category.valueOf(production.getCategories()[0].toString().toUpperCase()).ordinal());
        insertProduction.setInt(3, production.getLengthInSecs());
        insertProduction.setString(4, production.getReleaseDate().toString());
        insertProduction.executeUpdate();
        insertProduction.getGeneratedKeys().next();
        return insertProduction.getGeneratedKeys().getInt(1);
    }

    public Map<String, Integer> addMovieToDatabase(IMovie movie) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeAddMovie = getConnection().setSavepoint();

        try {
            int creditID = addCreditToDatabase(movie);
            int productionID = addProductionToDatabase(movie, creditID);
            System.out.println(productionID);
            PreparedStatement insertMovie = getConnection().prepareStatement(
                    "INSERT INTO movies(production_id)"
                            + "VALUES(?)"
                    , Statement.RETURN_GENERATED_KEYS);
            insertMovie.setInt(1, productionID);
            insertMovie.executeUpdate();
            insertMovie.getGeneratedKeys().next();
            int movieID = insertMovie.getGeneratedKeys().getInt(1);

            Map<String, Integer> movieIDs = new HashMap<>();
            movieIDs.put("creditID", creditID);
            movieIDs.put("productionID", productionID);
            movieIDs.put("movieID", movieID);

            getConnection().commit();
            getConnection().setAutoCommit(true);

            return movieIDs;

        } catch (SQLException e){
            System.out.println("WENT WRONG AT ADD MOVIE TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddMovie);
            getConnection().setAutoCommit(true);
        }
        System.out.println("No movie added");
        return null;
    }

    public Map<String, Integer> addShowToDatabase(IShow show) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeAddShow = getConnection().setSavepoint();
        try {
            int creditID = addCreditToDatabase(show);

            PreparedStatement insertShow = getConnection().prepareStatement(
                    "INSERT INTO shows(credit_id, all_seasons_approved)"
                            + "VALUES(?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);

            insertShow.setInt(1, creditID);
            insertShow.setBoolean(2, show.isAllSeasonApproved());
            insertShow.executeUpdate();
            insertShow.getGeneratedKeys().next();
            int showID = insertShow.getGeneratedKeys().getInt(1);

            HashMap<String, Integer> showIDs = new HashMap<>();
            showIDs.put("creditID", creditID);
            showIDs.put("showID", showID);
            getConnection().commit();
            getConnection().setAutoCommit(true);
            return showIDs;

        } catch (SQLException e){
            System.out.println("WENT WRONG AT ADD SHOW TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddShow);
            getConnection().setAutoCommit(true);
        }
        System.out.println("show not added");
        return null;
    }

    public Map<String, Integer> addSeasonToDatabase(ISeason season) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeAddSeason = getConnection().setSavepoint();
        try {
            int creditID = addCreditToDatabase(season);

            PreparedStatement insertSeason = getConnection().prepareStatement(
                    "INSERT INTO seasons(credit_id, show_id, all_episodes_approved)"
                            + "VALUES(?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);

            insertSeason.setInt(1, creditID);
            insertSeason.setInt(2, season.getShowID());
            insertSeason.setBoolean(3, season.isAllEpisodesApproved());
            insertSeason.executeUpdate();
            insertSeason.getGeneratedKeys().next();
            int seasonID = insertSeason.getGeneratedKeys().getInt(1);

            Map<String, Integer> showIDs = new HashMap<>();
            showIDs.put("creditID", creditID);
            showIDs.put("showID", season.getShowID());
            showIDs.put("seasonID", seasonID);

            getConnection().commit();
            getConnection().setAutoCommit(true);

            return showIDs;

        } catch (SQLException e) {
            System.out.println("WENT WRONG AT ADD SEASON TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddSeason);
            getConnection().setAutoCommit(true);
        }


        return null;
    }

    public Map<String, Integer> addEpisodeToDatabase(IEpisode episode) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeAddEpisode = getConnection().setSavepoint();

        try {

            int creditID = addCreditToDatabase(episode);
            int productionID = addProductionToDatabase(episode, creditID);

            PreparedStatement insertEpisode = getConnection().prepareStatement(
                    "INSERT INTO episodes(production_id, season_id)"
                            + "VALUES(?, ?)"
            ,Statement.RETURN_GENERATED_KEYS);

            insertEpisode.setInt(1, productionID);
            insertEpisode.setInt(2, episode.getSeasonID());
            insertEpisode.executeUpdate();
            insertEpisode.getGeneratedKeys().next();
            int episodeID = insertEpisode.getGeneratedKeys().getInt(1);

            Map<String, Integer> IDs = new HashMap<>();
            IDs.put("creditID", creditID);
            IDs.put("productionID", productionID);
            IDs.put("episodeID", episodeID);

            getConnection().commit();
            getConnection().setAutoCommit(true);

            return IDs;

        } catch (SQLException e){
            System.out.println("WENT WRONG AT ADD EPISODE TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddEpisode);
            getConnection().setAutoCommit(true);
        }
        System.out.println("Episode not added");
        return null;
    }

    public Map<String, Integer> addJobToDatabase(IJob job) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeAddJob = getConnection().setSavepoint();

        try{

            PreparedStatement insertJob = getConnection().prepareStatement(
                    "INSERT INTO jobs(person_id, job_role_id, production_id)"
                    + "VALUES(?, ?, ?)"
            ,Statement.RETURN_GENERATED_KEYS);

            insertJob.setInt(1, job.getPersonId());
            insertJob.setInt(2, (job.getRole().ordinal() + 1));
            insertJob.setInt(3, job.getProductionID());
            insertJob.executeUpdate();
            insertJob.getGeneratedKeys().next();
            int jobID = insertJob.getGeneratedKeys().getInt(1);

            Map<String, Integer> IDs = new HashMap<>();
            IDs.put("jobID", jobID);

            getConnection().commit();
            getConnection().setAutoCommit(true);

            return IDs;
        } catch (SQLException e){
            getConnection().rollback();
            System.out.println("WENT WRONG AT ADD JOB TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddJob);
            getConnection().setAutoCommit(true);
        }
        System.out.println("Job not added");
        return null;
    }

    public static void main(String[] args) {

//        try {
            /*
            Map<String, Integer> IDs = getInstance().addPersonToDatabase(
                    new Person(
                            "Hans Pedersen",
                            "Jeg er sej",
                            "12345678",
                            "Yeet",
                            "bareMig@gmail.com"
                    )
            );

             */
            /*
            Category[] categories = {Category.FILM};

            Map<String, Integer> IDs = getInstance().addMovieToDatabase(
                    new Movie(
                            "Interstellar",
                            "A very good movie",
                            categories,
                            10140,
                            new Date(1415228400000L)
                    )
            );
             */
            /*
            Map<String, Integer> IDs = getInstance().addShowToDatabase(
                    new Show(
                            "The 100",
                            "Yeet a couple 100 people to die on earth",
                            false
                    )
            );
             */

            /** NOT DONE **/

            /*
            Map<String, Integer> IDs = getInstance().addSeasonToDatabase(
                    new Season(
                            "Season 1",
                            "100 people are sent to the earth",

                            false
                    )
            );
             */


//            System.out.println(IDs.get("creditID") + " --- " + IDs.get("productionID") + " --- " + IDs.get("movieID"));
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
/*
        try {
            getInstance().addJobToDatabase(
                    new Job(
                            1,
                            Role.DUKKEFØRER,
                            1
                    )
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

 */


    }

    private Connection getConnection() {
        return connection;
    }
}
