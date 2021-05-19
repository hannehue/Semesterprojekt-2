package Java.data;

import Java.interfaces.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

    //---------------------------------------------------------
    //                        Put
    //Metoder som tager en parameter i forhold til hvad der skal sendes til databsen
    //---------------------------------------------------------
    public void putInDatabase(IPerson person){
        //Ijob følger med her, derfor er der ikke en putInDatabase(IJob)
        //charactername er givet i IJob
        try {
            DatabaseLoader.getInstance().addPersonToDatabase(person);
        } catch (SQLException e) {
            System.out.println("Person not added. Exception thrown by DBloaderFacade." +
                    " Most likely error at setAutoCommit to false");
            e.printStackTrace();
        }
    }
    public void putInDatabase(ICompany company){

    }
    public void putInDatabase(ICredit credit) {

    }
    public void putInDatabase(IEpisode episode) {

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
    public void putInDatabase(IProduction production) {

    }

    //---------------------------------------------------------
    //                       Get
    //Metoder som tager en parameter og returnere det fra databasen
    //---------------------------------------------------------
    //returns ændres når metode der skal kaldes er lavet
    public IPerson getFromDatabase(IPerson person){
        return null;
    }
    public ICompany getFromDatabase(ICompany company){
        return null;
    }
    public ICredit getFromDatabase(ICredit credit){
        return null;
    }
    public IEpisode getFromDatabase(IEpisode episode){
        return null;
    }
    public ISeason getFromDatabase(ISeason season){
        return null;
    }
    public IShow getFromDatabase(IShow show){
        return null;
    }
    public IMovie getFromDatabase(IMovie movie){
        return null;
    }
    public IProduction getFromDatabase(IProduction production){
        return null;
    }

}
