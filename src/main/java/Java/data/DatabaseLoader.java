package Java.data;

import Java.domain.data.Category;
import Java.domain.data.Job;
import Java.domain.data.Person;
import Java.domain.data.Role;
import Java.interfaces.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        try{
            getInstance().getConnection().setAutoCommit(false);
            PreparedStatement deleteStmt = getInstance().getConnection().prepareStatement(
                    "DELETE FROM credits WHERE credit_id = 128"
            );
            deleteStmt.executeUpdate();
            deleteStmt.executeUpdate();
            getInstance().getConnection().commit();
            getInstance().getConnection().setAutoCommit(true);

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

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
        insertProduction.setInt(2, Category.valueOf(production.getCategories()[0].toString().toUpperCase()).ordinal() + 1);
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

            int characterNameID = 0;
            if (job.getRole().ordinal() + 1 == 1){
                PreparedStatement insertCharacterName = getConnection().prepareStatement(
                        "INSERT INTO character_names(job_id, character_name) " +
                                "VALUES(?, ?)"
                , Statement.RETURN_GENERATED_KEYS);
                insertCharacterName.setInt(1, jobID);
                insertCharacterName.setString(2, job.getCharacterName());
                insertCharacterName.executeUpdate();
                insertCharacterName.getGeneratedKeys().next();
                characterNameID = insertCharacterName.getGeneratedKeys().getInt(1);
            }


            Map<String, Integer> IDs = new HashMap<>();
            IDs.put("jobID", jobID);
            if (characterNameID != 0) IDs.put("characterNameID", characterNameID);
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

    public ResultSet getAllJobRoles() throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeAddJob = getConnection().setSavepoint();
        try {
            PreparedStatement getAllJobRoles = getConnection().prepareStatement(
                    "SELECT * FROM job_roles"
            );
            getAllJobRoles.execute();
            return getAllJobRoles.getResultSet();
        } catch (Exception e){
            System.out.println("WENT WRONG AT GETTING JOB ROLES IN DATABASE");
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



    public void updateMovie(IMovie movie) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeUpdateMovie = getConnection().setSavepoint();
        try {
            PreparedStatement updateMovie = getConnection().prepareStatement(
                    "WITH updated_credit AS(" +
                            "UPDATE credits " +
                            "SET name = ?, " +
                            "approved = ?, " +
                            "description = ? " +
                            "WHERE credit_id = ?) " +
                            "UPDATE productions " +
                            "SET category_id = ?, " +
                            "length_in_secs = ?, " +
                            "release_date = ? " +
                            "WHERE productions.credit_id = ?"
            );
            updateMovie.setString(1, movie.getName());
            updateMovie.setBoolean(2, false);
            updateMovie.setString(3, movie.getDescription());
            updateMovie.setInt(4, movie.getCreditID());
            updateMovie.setInt(5, Category.valueOf(movie.getCategories()[0].toString().toUpperCase()).ordinal());
            updateMovie.setInt(6, movie.getLengthInSecs());
            updateMovie.setString(7, movie.getReleaseDate().toString());
            updateMovie.setInt(8, movie.getCreditID());
            updateMovie.executeUpdate();
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            getConnection().rollback(beforeUpdateMovie);
            getConnection().setAutoCommit(true);
            e.printStackTrace();
        }
    }

    public void updatePerson(IPerson person) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeUpdatePerson = getConnection().setSavepoint();

        try {

            PreparedStatement updatePerson = getConnection().prepareStatement(
                    "WITH update_credit AS(" +
                            "UPDATE credits " +
                            "SET name = ?, " +
                            "approved = ?, " +
                            "description = ?" +
                            "WHERE credit_id = ?) " +
                            "UPDATE persons " +
                            "SET phone_number = ?, " +
                            "email = ?, " +
                            "personal_info = ? " +
                            "WHERE persons.credit_id = ?"
            );

            updatePerson.setString(1, person.getName());
            updatePerson.setBoolean(2, false);
            updatePerson.setString(3, person.getDescription());
            updatePerson.setInt(4, person.getCreditID());
            updatePerson.setString(5, person.getPhoneNumber());
            updatePerson.setString(6, person.getPersonEmail());
            updatePerson.setString(7, person.getPersonalInfo());
            updatePerson.setInt(8, person.getCreditID());
            updatePerson.executeUpdate();
            getConnection().commit();

            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            getConnection().rollback(beforeUpdatePerson);
            getConnection().setAutoCommit(true);
            e.printStackTrace();
        }


    }

    public void updateShow(IShow show) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeUpdateShow = getConnection().setSavepoint();
        try {
            PreparedStatement updateShow = getConnection().prepareStatement(
                    "WITH updated_credit AS(" +
                            "UPDATE credits " +
                            "SET name = ?, " +
                            "approved = ?, " +
                            "description = ? " +
                            "WHERE credit_id = ?) " +
                            "UPDATE shows " +
                            "all_seasons_approved = ?" +
                            "WHERE shows.credit_id = ?"
            );
            updateShow.setString(1, show.getName());
            updateShow.setBoolean(2, false);
            updateShow.setString(3, show.getDescription());
            updateShow.setInt(4, show.getCreditID());
            updateShow.setBoolean(5, show.isAllSeasonApproved());
            updateShow.setInt(6, show.getCreditID());
            updateShow.executeUpdate();
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            getConnection().rollback(beforeUpdateShow);
            getConnection().setAutoCommit(true);
            e.printStackTrace();
        }

    }

    public void updateSeason(ISeason season) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeUpdateSeason = getConnection().setSavepoint();
        try {

            PreparedStatement updateSeason = connection.prepareStatement(
                    "WITH updated_credit AS(" +
                            "UPDATE credits " +
                            "SET name = ?, " +
                            "approved = ?, " +
                            "description = ?, " +
                            "WHERE credit_id = ?) " +
                            "UPDATE seasons " +
                            "SET all_episodes_approved = ? " +
                            "WHERE seaons.credit_id = ?"
            );
            updateSeason.setString(1, season.getName());
            updateSeason.setBoolean(2, false);
            updateSeason.setString(3, season.getDescription());
            updateSeason.setInt(4, season.getCreditID());
            updateSeason.setBoolean(5, season.isAllEpisodesApproved());
            updateSeason.setInt(6, season.getCreditID());
            updateSeason.executeUpdate();
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException e){
            getConnection().rollback(beforeUpdateSeason);
            getConnection().setAutoCommit(true);
            e.printStackTrace();
        }
    }

    public void updateEpisode(IEpisode episode) throws SQLException{
        getConnection().setAutoCommit(false);
        Savepoint beforeUpdateEpisode = getConnection().setSavepoint();
        try {
            PreparedStatement updateEpisode = getConnection().prepareStatement(
                    "WITH updated_credit AS(" +
                            "UPDATE credits " +
                            "SET name = ?, " +
                            "approved = ?, " +
                            "description = ?" +
                            "WHERE credit_id = ?) " +
                            "UPDATE productions " +
                            "SET category_id = ?, " +
                            "length_in_secs = ?, " +
                            "release_date = ? " +
                            "WHERE productions.credit_id = ?"
            );

            updateEpisode.setString(1, episode.getName());
            updateEpisode.setBoolean(2, false);
            updateEpisode.setString(3, episode.getDescription());
            updateEpisode.setInt(4, episode.getCreditID());
            updateEpisode.setInt(5, Category.valueOf(episode.getCategories()[0].toString().toUpperCase()).ordinal());
            updateEpisode.setInt(6, episode.getLengthInSecs());
            updateEpisode.setString(7, episode.getReleaseDate().toString());
            updateEpisode.setInt(8, episode.getCreditID());
            updateEpisode.executeUpdate();
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException e){
            getConnection().rollback(beforeUpdateEpisode);
            getConnection().setAutoCommit(true);
            e.printStackTrace();
        }


    }



    //------------------------------------------------------------
    // DELETE METHODS
    //------------------------------------------------------------
    public void deletePerson(int creditId) throws SQLException {
        getConnection().setAutoCommit(false);
        Savepoint beforeDeletePerson = getConnection().setSavepoint();
        try {
            PreparedStatement deletePersonStatement = getConnection().prepareStatement(
                    "DELETE FROM persons WHERE credit_id = ?; "
            );
            deletePersonStatement.setInt(1, creditId);
            deletePersonStatement.executeUpdate();
            deletePersonStatement = getConnection().prepareStatement(
                    "DELETE FROM credits WHERE credit_id = ?; "
            );
            deletePersonStatement.setInt(1, creditId);
            deletePersonStatement.executeUpdate();
            getConnection().commit();
            //set auto commit to true again, as that is the default
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            getConnection().rollback();
            System.out.println("WENT WRONG DELETE PERSON in DATABASE");
            e.printStackTrace();
            getConnection().rollback(beforeDeletePerson);
            getConnection().setAutoCommit(true);
        }
    }


    public void deleteCredit(ICredit credit){
        try {
            getConnection().setAutoCommit(false);
            if (credit instanceof IShow){
                deleteShow((IShow) credit);
            }
            getConnection().commit();
        }
            catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void deleteShow(IShow show)throws SQLException{
        for (ISeason season : show.getSeasons()){
            deleteSeason(season);
        }
        PreparedStatement deleteShow = getConnection().prepareStatement(
                "DELETE FROM credits WHERE credit_id = ?"
        );
        deleteShow.setInt(1, show.getCreditID());
        deleteShow.executeUpdate();
    }

    public void deleteSeason(ISeason season) throws SQLException{
        for (IEpisode episode : season.getEpisodes()){
            deleteEpisode(episode);
        }
        PreparedStatement deleteSeason = getConnection().prepareStatement(
                "DELETE FROM credits WHERE credit_id = ?"
        );
        deleteSeason.setInt(1, season.getCreditID());
        deleteSeason.executeUpdate();
    }

    public void deleteEpisode(IEpisode episode) throws SQLException{

        PreparedStatement deleteEpisode = getConnection().prepareStatement(
                "DELETE FROM credits WHERE credit_id = ?"
        );
        deleteEpisode.setInt(1, episode.getCreditID());
        deleteEpisode.executeUpdate();
    }

    private Connection getConnection() {
        return connection;
    }
}
