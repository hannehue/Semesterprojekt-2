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
                System.out.println(queryStatement.getResultSet().getRow());
                movieList.add(new Movie(
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
                ));
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

    public Map<String, Integer> addMovieToDatabase(IMovie movie) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeAddMovie = getConnection().setSavepoint();

        try {
            int creditID = addCreditToDatabase(movie);
            int productionID = addProductionToDatabase(movie, creditID);

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
            getConnection().setAutoCommit(false);
            return showIDs;

        } catch (SQLException e){
            System.out.println("WENT WRONG AT ADD SHOW TO DATABASE");
            e.printStackTrace();
        }
        System.out.println("show not added");
        return null;
    }

    public Map<String, Integer> addEpisodeToDatabase(IEpisode episode){

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
            getConnection().setAutoCommit(false);

            return showIDs;

        } catch (SQLException e) {
            System.out.println("WENT WRONG AT ADD SEASON TO DATABASE");
            e.printStackTrace();
        }


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


    }

    private Connection getConnection() {
        return connection;
    }
}
