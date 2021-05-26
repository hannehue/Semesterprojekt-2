package Java.data;

import Java.domain.data.Category;
import Java.domain.data.Job;
import Java.domain.data.Person;
import Java.domain.data.Role;
import Java.interfaces.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    public static void main(String[] args) {

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
    public ResultSet searchQueryToPersonList(String searchString) {
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

            return queryStatement.getResultSet();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SQL ERROR at queryToPerson");
        }
        return null;
    }

    public ResultSet queryGetJobsForPerson(IPerson person) {
        try {
            // Job handling
            // Runs new query, using persons, jobs and job_roles
            PreparedStatement jobQueryStatement = getInstance().connection.prepareStatement(
                    "SELECT * FROM persons " +
                            "INNER JOIN jobs " +
                            "    ON persons.person_id = jobs.person_id " +
                            "INNER JOIN job_roles " +
                            "    ON jobs.job_role_id = job_roles.job_role_id " +
                            "WHERE persons.person_id = ? "
            );
            jobQueryStatement.setInt(1, person.getPersonID());
            jobQueryStatement.executeQuery();
            return jobQueryStatement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet searchQueryToMovieList(String searchString) {
        try {
            PreparedStatement queryStatement = getInstance().connection.prepareStatement(
                    "SELECT * FROM credits " +
                            "INNER JOIN productions " +
                            "    ON credits.credit_id = productions.credit_id " +
                            "INNER JOIN movies " +
                            "    ON productions.production_id = movies.production_id " +
                            "INNER JOIN categories " +
                            "    ON productions.category_id = categories.category_id " +
                            "WHERE LOWER(name) LIKE LOWER(?)"
            );
            //inserts searchString into SQL statement
            queryStatement.setString(1, "%" + searchString + "%");
            queryStatement.executeQuery();

            return queryStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception at search query to Movie");
        }
        return null;
    }

    public ArrayList<IGroup> searchQueryToGroup(String searchString) {
        return null;
    }

    public ResultSet searchQueryToShowList(String searchString) {
        try {
            PreparedStatement searchQuery = getConnection().prepareStatement(
                    "SELECT * FROM credits, shows WHERE LOWER(name) LIKE LOWER(?)" +
                            "AND credits.credit_id = shows.credit_id"
            );
            searchQuery.setString(1, "%" + searchString + "%");
            searchQuery.executeQuery();
            return searchQuery.getResultSet();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception at search query to Show");
        }
        return null;
    }

    public ResultSet queryGetSeasonsForShow(IShow show) {
        try {
            PreparedStatement getSeasonQuery = getConnection().prepareStatement(
                    "SELECT * FROM credits, seasons WHERE seasons.show_id = ?" +
                            "AND credits.credit_id = seasons.credit_id"
            );
            getSeasonQuery.setInt(1, show.getIDMap().get("showID"));
            getSeasonQuery.executeQuery();

            return getSeasonQuery.getResultSet();
        } catch (SQLException e) {
            System.out.println("ERROR AT queryGetSeasonForShow");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet queryGetEpisodesForShow(ISeason season) {
        try {

            PreparedStatement getEpisodeQuery = getConnection().prepareStatement(
                    "SELECT *" +
                            "FROM productions " +
                            "INNER JOIN episodes " +
                            "ON productions.production_id = episodes.production_id " +
                            "INNER JOIN seasons " +
                            "ON seasons.season_id = episodes.season_id " +
                            "INNER JOIN credits " +
                            "ON productions.credit_id = credits.credit_id " +
                            "WHERE seasons.season_id = ? ");
            getEpisodeQuery.setInt(1, season.getIDMap().get("seasonID"));
            getEpisodeQuery.executeQuery();

            return getEpisodeQuery.getResultSet();
        } catch (SQLException e) {
            System.out.println("ERROR AT queryGetEpisodeForShow");
            e.printStackTrace();
        }
        return null;
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
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR AT ADD PERSON TO DATABASE");
            getConnection().rollback(beforeAddPerson);
            getConnection().setAutoCommit(true);
        }
        System.out.println("No user added");
        return null;
    }

    public int addCreditToDatabase(ICredit credit) throws SQLException {
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

    public int addProductionToDatabase(IProduction production, int creditID) throws SQLException {
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

    public Map<String, Integer> addMovieToDatabase(IMovie movie) throws SQLException {
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

        } catch (SQLException e) {
            System.out.println("WENT WRONG AT ADD MOVIE TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddMovie);
            getConnection().setAutoCommit(true);
        }
        System.out.println("No movie added");
        return null;
    }

    public Map<String, Integer> addShowToDatabase(IShow show) throws SQLException {
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

        } catch (SQLException e) {
            System.out.println("WENT WRONG AT ADD SHOW TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddShow);
            getConnection().setAutoCommit(true);
        }
        System.out.println("show not added");
        return null;
    }

    public Map<String, Integer> addSeasonToDatabase(ISeason season) throws SQLException {
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

    public Map<String, Integer> addEpisodeToDatabase(IEpisode episode) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeAddEpisode = getConnection().setSavepoint();

        try {

            int creditID = addCreditToDatabase(episode);
            int productionID = addProductionToDatabase(episode, creditID);

            PreparedStatement insertEpisode = getConnection().prepareStatement(
                    "INSERT INTO episodes(production_id, season_id)"
                            + "VALUES(?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);

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

        } catch (SQLException e) {
            System.out.println("WENT WRONG AT ADD EPISODE TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddEpisode);
            getConnection().setAutoCommit(true);
        }
        System.out.println("Episode not added");
        return null;
    }

    public Map<String, Integer> addJobToDatabase(IJob job) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeAddJob = getConnection().setSavepoint();

        try {

            PreparedStatement insertJob = getConnection().prepareStatement(
                    "INSERT INTO jobs(person_id, job_role_id, production_id)"
                            + "VALUES(?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);

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
        } catch (SQLException e) {
            getConnection().rollback();
            System.out.println("WENT WRONG AT ADD JOB TO DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddJob);
            getConnection().setAutoCommit(true);
        }
        System.out.println("Job not added");
        return null;
    }

    public ResultSet getAllUnApprovedCredits() throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeAddJob = getConnection().setSavepoint();
        try {
            PreparedStatement getAllUnApprovedCredits = getConnection().prepareStatement(
                    "SELECT * FROM credits " +
                            "LEFT JOIN persons p on credits.credit_id = p.credit_id " +
                            "LEFT JOIN productions production on credits.credit_id = production.credit_id " +
                            "LEFT JOIN movies m on production.production_id = m.production_id " +
                            "LEFT JOIN seasons s on credits.credit_id = s.credit_id " +
                            "LEFT JOIN shows show on credits.credit_id = show.credit_id " +
                            "LEFT JOIN episodes e on production.production_id = e.production_id " +
                            "WHERE credits.approved = false"
            );
            getAllUnApprovedCredits.execute();
            return getAllUnApprovedCredits.getResultSet();
        } catch (Exception e) {
            System.out.println("WENT WRONG AT GET UNAPPROVED CREDITS IN DATABASE");
            getConnection().rollback(beforeAddJob);
            e.printStackTrace();
        }
        return null;
    }

    public void setCreditApproveState(ICredit credit, boolean bool) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeSetApproveState = getConnection().setSavepoint();
        try {
            PreparedStatement updateApproveState = getConnection().prepareStatement(
                    "UPDATE credits SET approved = ? " +
                            "WHERE credits.credit_id = ?");
            updateApproveState.setBoolean(1, bool);
            updateApproveState.setInt(2, credit.getCreditID());
            updateApproveState.executeUpdate();
            getConnection().commit();
            //set auto commit to true again, as that is the default
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            getConnection().rollback();
            System.out.println("WENT WRONG APPROVE CREDIT in DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeSetApproveState);
            getConnection().setAutoCommit(true);
        }
    }

    public void deletePerson(int creditId) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeAddJob = getConnection().setSavepoint();
        try {
            PreparedStatement updateJob = getConnection().prepareStatement(
                    "DELETE FROM persons WHERE credit_id = ?; "
            );
            updateJob.setInt(1, creditId);
            updateJob.executeUpdate();
            updateJob = getConnection().prepareStatement(
                    "DELETE FROM credits WHERE credit_id = ?; "
            );
            updateJob.setInt(1, creditId);
            updateJob.executeUpdate();
            getConnection().commit();
            //set auto commit to true again, as that is the default
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            getConnection().rollback();
            System.out.println("WENT WRONG APPROVE CREDIT in DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeAddJob);
            getConnection().setAutoCommit(true);
        }
    }

    private Connection getConnection() {
        return connection;
    }
}
