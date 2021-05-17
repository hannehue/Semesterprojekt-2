package Java.data;

import Java.interfaces.*;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseLoaderFacade {
    //---------------------------------------------------------
    //                        Put
    //Metoder som tager en parameter i forhold til hvad der skal sendes til databsen
    //---------------------------------------------------------
    public void putInDatabase(IPerson person){
        //Ijob følger med her, derfor er der ikke en putInDatabase(IJob)
        //charactername er givet i IJob
    }
    public void putInDatabase(ICompany company){

    }
    public void putInDatabase(ICredit credit) {

    }
    public void putInDatabase(IEpisode episode) {

    }
    public void putInDatabase(ISeason season) {

    }
    public void putInDatabase(IShow show) {

    }
    public void putInDatabase(IMovie movie){

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
