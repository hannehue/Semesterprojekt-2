package Java.data;

import Java.interfaces.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class DatabaseLoaderFacade {

    private static DatabaseLoaderFacade instance;


    private DatabaseLoaderFacade(){};
    public static DatabaseLoaderFacade getInstance() {
        if (instance == null){
            instance = new DatabaseLoaderFacade();
        }
        return instance;
    }

    //---------------------------------------------------------------------------------------------------
    //PUT METHODS. TAKES A NED OBJECT WITHOUT IDS AND ADDS IT TO DB. RETURNS A HASHMAP WITH GENERATED IDS
    //---------------------------------------------------------------------------------------------------
    public void putInDatabase(ICredit credit) {

    } // As you never put a raw credit into database, this is not needed
    public void putInDatabase(IProduction production) {

    } // As you never put a raw production into database, this is not needed
    public Map<String, Integer> putInDatabase(IPerson person){
        //Ijob følger med her, derfor er der ikke en putInDatabase(IJob)
        //charactername er givet i IJob
        try {
            return DatabaseLoader.getInstance().addPersonToDatabase(person);
        } catch (SQLException e) {
            System.out.println("Person not added. Exception thrown by DBloaderFacade." +
                    " Most likely error at setAutoCommit to false");
            e.printStackTrace();
            return null;
        }
    }
    public Map<String, Integer> putInDatabase(IJob job){

        try {
            return DatabaseLoader.getInstance().addJobToDatabase(job);
        } catch (SQLException e){
            System.out.println("Job not added. Exception thrown by DBloaderFacade." +
                    " Most likely error at setAutoCommit to false");
            e.printStackTrace();
        }


        return null;
    }
    public Map<String, Integer> putInDatabase(IEpisode episode) {
        try {
            return DatabaseLoader.getInstance().addEpisodeToDatabase(episode);
        } catch (SQLException e) {
            System.out.println("Episode not added. Exception thrown by DBloaderFacade." +
                    " Most likely error at setAutoCommit to false");
            e.printStackTrace();
            return null;
        }
    }
    public Map<String, Integer> putInDatabase(ISeason season) {
        try {
            return DatabaseLoader.getInstance().addSeasonToDatabase(season);
        } catch (SQLException e) {
            System.out.println("Season not added. Exception thrown by DBloaderFacade." +
                    " Most likely error at setAutoCommit to false");
            e.printStackTrace();
            return null;
        }
    }
    public Map<String, Integer> putInDatabase(IShow show) {
        try {
            return DatabaseLoader.getInstance().addShowToDatabase(show);
        } catch (SQLException e) {
            System.out.println("Show not added. Exception thrown by DBloaderFacade." +
                    " Most likely error at setAutoCommit to false");
            e.printStackTrace();
            return null;
        }

    }
    public Map<String, Integer> putInDatabase(IMovie movie){
        try {
            return DatabaseLoader.getInstance().addMovieToDatabase(movie);
        } catch (SQLException e) {
            System.out.println("Error at put movie in database");
            e.printStackTrace();
            return null;
        }
    }
    public void putInDatabase(ICompany company){

    } // Company not added in gui, and therefore never added to databse
    //----------------------------------------------------------------------
    // SPECIFIC GETTERS. DEPEND ON EXISTING OBJECT (OR STATE I.E. UNAPPROVED
    //----------------------------------------------------------------------
    //returns ændres når metode der skal kaldes er lavet
    public ICompany getFromDatabase(ICompany company){
        return null;
    }
    public ICredit getFromDatabase(ICredit credit){
        return null;
    }
    public ResultSet getFromDatabase(IShow show){
        //This function takes a show, and finds all matching seasons, and thereby episodes also, in the database
        return DatabaseLoader.getInstance().queryGetSeasonsForShow(show);
    }
    public ResultSet getFromDatabase(ISeason season){
        return DatabaseLoader.getInstance().queryGetEpisodesForShow(season);
    }
    public IMovie getFromDatabase(IMovie movie){
        return null;
    }
    public IProduction getFromDatabase(IProduction production){
        return null;
    }
    public ResultSet getJobsForPerson(IPerson person){
        return DatabaseLoader.getInstance().queryGetJobsForPerson(person);
    }
    public ResultSet getJobRoles() throws SQLException {
        return DatabaseLoader.getInstance().getAllJobRoles();
    }
    public ResultSet getAllUnApprovedCredits() throws SQLException {
        return DatabaseLoader.getInstance().getAllUnApprovedCredits();
    }
    //-----------------------------------------------------------
    // SEARCH METHODS
    //-----------------------------------------------------------
    public ResultSet searchPersonsFromDatabase(String searchString){
        return DatabaseLoader.getInstance().searchQueryToPersonList(searchString);
    }
    public ResultSet searchMoviesFromDatabase(String searchString){
        return DatabaseLoader.getInstance().searchQueryToMovieList(searchString);
    }
    public ResultSet searchShowsFromDatabase(String searchString){
        return DatabaseLoader.getInstance().searchQueryToShowList(searchString);
    }
    //-----------------------------------------------------------
    // UPDATE METHODS
    //-----------------------------------------------------------
    public void updateCredit(IMovie movie){
        try {
            DatabaseLoader.getInstance().updateMovie(movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCredit(IPerson person){
        try {
            DatabaseLoader.getInstance().updatePerson(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCredit(IShow show){
        try {
            DatabaseLoader.getInstance().updateShow(show);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCredit(ISeason season){
        try {
            DatabaseLoader.getInstance().updateSeason(season);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCredit(IEpisode episode){
        try {
            DatabaseLoader.getInstance().updateEpisode(episode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setCreditApproveState(ICredit credit, boolean bool) throws SQLException{
        DatabaseLoader.getInstance().setCreditApproveState(credit, bool);
    }
    //------------------------------------------------------------
    // DELETE METHODS
    //------------------------------------------------------------
    public void deletePerson(int creditid) throws SQLException{
        DatabaseLoader.getInstance().deletePerson(creditid);
    }

    public void deleteCredit(ICredit credit){
        DatabaseLoader.getInstance().deleteCredit(credit);
    }
}
