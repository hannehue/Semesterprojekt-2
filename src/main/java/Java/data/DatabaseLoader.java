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

    private ArrayList<String[]> personArraylist;

    private ArrayList<String[]> groupArraylist;

    private ArrayList<String[]> movieArrayList;

    private ArrayList<String[]> showArrayList;

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

    public static DatabaseLoader getInstance() {
        if (instance == null) {
            instance = new DatabaseLoader();
        }
        return instance;
    }

    public String[] creditToStringArray(ICredit credit) {

        return credit.toFileString().split(",");
    }

    public void addCreditToDatabase(ICredit credit) {
        if (credit instanceof IPerson) {
            personArraylist.add(creditToStringArray(credit));

        } else if (credit instanceof IMovie) {
            movieArrayList.add(creditToStringArray(credit));

        } else if (credit instanceof IGroup) {
            groupArraylist.add(creditToStringArray(credit));

        } else if (credit instanceof IShow) {
            showArrayList.add(creditToStringArray(credit));
        }
    }

    public void addCreditsToDatabase(ArrayList<? extends ICredit> readList) {
        if (readList.size() == 0 || readList == null) {
            return;
        }

        ArrayList<String[]> tempList = new ArrayList<>();
        for (ICredit p : readList) {
            if (p != null) {
                tempList.add(creditToStringArray(p));
            }
        }
        if (readList.get(0) instanceof IPerson) {
            personArraylist = tempList;

        } else if (readList.get(0) instanceof IMovie) {
            movieArrayList = tempList;

        } else if (readList.get(0) instanceof IGroup) {
            groupArraylist = tempList;
        } else if (readList.get(0) instanceof IShow) {
            showArrayList = tempList;
        }
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
        try {
            // Set autoCommit to false, so only both prepared statements run
            getConnection().setAutoCommit(false);

            // insert statement to insert info into creditsTable
            PreparedStatement insertToCredits = getInstance().connection.prepareStatement(
                    "INSERT INTO credits(name, date_added, approved, description)"
                    + "VALUES(?, ?, ?, ?)"
                    //set prepared statement to return generated credit_ID
            , Statement.RETURN_GENERATED_KEYS);

            //insert values from IPerson to preparedStatement
            insertToCredits.setString(1, person.getName());
            insertToCredits.setString(2, person.getDateAdded().toString());
            insertToCredits.setBoolean(3, person.isApproved());
            insertToCredits.setString(4, person.getDescription());
            //execute but NOT commit
            insertToCredits.executeUpdate();
            //get generated credit_ID
            int creditID = 0;
            while (insertToCredits.getGeneratedKeys().next()) {
                creditID = insertToCredits.getGeneratedKeys().getInt(1);
            }

            // insert statement to insert indo into persons table
            PreparedStatement insertPerson = getInstance().connection.prepareStatement(
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
            int personID = 0;
            while (insertPerson.getGeneratedKeys().next()) {
                personID = insertPerson.getGeneratedKeys().getInt(1);
            }

            // Insert generated IDs to a map, with corresponding key names
            Map<String, Integer> returnIDs = new HashMap<>();
            returnIDs.put("creditID", creditID);
            returnIDs.put("personID", personID);

            //commit changes to database
            getInstance().getConnection().commit();
            //set auto commit to true again, as that is the default
            getInstance().getConnection().setAutoCommit(true);
            //return ID map
            return returnIDs;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR AT ADD PERSON TO DATABASE");
            getInstance().getConnection().setAutoCommit(true);
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

    public static void main(String[] args) {

        try {
            Map<String, Integer> IDs = getInstance().addPersonToDatabase(
                    new Person(
                            "Hans Pedersen",
                            "Jeg er sej",
                            "12345678",
                            "Yeet",
                            "bareMig@gmail.com"
                    )
            );
            System.out.println(IDs.get("creditID") + " --- " + IDs.get("personID"));
        } catch (SQLException e){
            e.printStackTrace();
        }


    }


    private Connection getConnection() {
        return connection;
    }

    public ArrayList<String[]> getPersonArraylist() {
        return personArraylist;
    }

    public ArrayList<String[]> getGroupArraylist() {
        return groupArraylist;
    }

    public ArrayList<String[]> getMovieArrayList() {
        return movieArrayList;
    }

    public ArrayList<String[]> getShowArrayList() {
        return showArrayList;
    }

}
