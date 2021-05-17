package Java.domain.services;

import Java.domain.data.Job;
import Java.domain.data.Role;
import Java.interfaces.IJob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JobManager {
    private static JobManager instance = new JobManager();
    private JobManager(){}

    private final ObservableList<IJob> tempList = FXCollections.observableArrayList();

    public void addJob(int productionId) {
        for (IJob job : tempList) {
            PersonManager.getInstance().getPersonById(job.getPersonId()).addJob(job);
        }
        tempList.clear();
    }


    public void addTempJob(int personId, Role role, String charactername, int productionId) {
        tempList.add(new Job(
                personId,
                role,
                charactername,
                productionId
        ));
        System.out.println("temp job added");
    }

    public void addTempJob(int personId, Role role, int productionId) {
        tempList.add(new Job(
                personId,
                role,
                productionId
        ));
        System.out.println("temp job added");
    }


    public void clearTempJobs(){
        tempList.clear();
    }

    public ObservableList<IJob> getTempList() {
        return tempList;
    }

    public static JobManager getInstance() {
        return instance;
    }
}
