package com.example.simdata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.dao.SwimpoolDao;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Location;
import com.example.simdata.room.database.db.entity.Swimpool;
import com.example.simdata.room.database.db.entity.Template;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SwimmingPoolDataRepository {

    private SwimpoolDao swimpoolDao;
    private LiveData<List<Swimpool>> allSwimmingPools;
    private List<Swimpool>  swimmingPoolList;
    private List<AttendanceReport> allReports;
    private List<Swimpool> allSwimmingPoolsNames;
    private List<AttendanceReport> deleteAttReport;

    public SwimmingPoolDataRepository(Application application){
        SwimpoolDatabase database = SwimpoolDatabase.getInstance(application);
        swimpoolDao = database.swimpoolDao();
        allReports = swimpoolDao.getAllReports();
        allSwimmingPoolsNames = swimpoolDao.getSwimpool();
        //locationLiveData = swimpoolDao.getSwimpool();

    }
    public void addLocation(Location location){
         new InsertLocationAsyncTask(swimpoolDao).execute(location);
    }
    public void addSwimpool(Swimpool swimpool){
         new InsertSwimpoolAsyncTask(swimpoolDao).execute(swimpool);
    }
    public void addTemplate(Template template){
         new InsertTemplateAsyncTask(swimpoolDao).execute(template);
    }
    public void addAttRep(AttendanceReport attendanceReports){
        new InsertAttRepAsyncTask(swimpoolDao).execute(attendanceReports);
    }
    public void deleteAttReport(AttendanceReport attendanceReport){
        new DeleteAttReportAsyncTask(swimpoolDao).execute(attendanceReport);
    }

    public LiveData <List<Swimpool>>  getAllSwimmingPools() {
       return swimpoolDao.getAllSwimmingPools();
    }
    public List<Swimpool> getSwimpool() {
        return  swimpoolDao.getSwimpool();
    }

    public List<Swimpool> getAllSwimmingPoolsNames(){
        return allSwimmingPoolsNames;
    }

    public List<AttendanceReport> getAllReports() {return  allReports;}

    public List<Swimpool>  getAllNamesByPoolId(int id){
        return swimpoolDao.getAllNamesByPoolId(id);
    }


    private static class DeleteAttReportAsyncTask extends AsyncTask<AttendanceReport, Void, Void> {
        private SwimpoolDao swimpoolDao;

        private DeleteAttReportAsyncTask(SwimpoolDao swimpoolDao) {
            this.swimpoolDao = swimpoolDao;
        }

        @Override
        protected Void doInBackground(AttendanceReport... attendanceReports) {
            swimpoolDao.deleteAttReport(attendanceReports[0]);
            return null;
        }
    }

    private static class InsertLocationAsyncTask extends AsyncTask<Location, Void, Void> {
        private SwimpoolDao swimpoolDao;

        private InsertLocationAsyncTask(SwimpoolDao swimpoolDao) {
            this.swimpoolDao = swimpoolDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            swimpoolDao.addLocation(locations[0]);
            return null;
        }
    }

    private static class InsertSwimpoolAsyncTask extends AsyncTask<Swimpool, Void, Void> {
        private SwimpoolDao swimpoolDao;

        private InsertSwimpoolAsyncTask(SwimpoolDao swimpoolDao) {
            this.swimpoolDao = swimpoolDao;
        }

        @Override
        protected Void doInBackground(Swimpool... swimpools) {
            swimpoolDao.addSwimpool(swimpools[0]);
            return null;
        }
    }
    private static class InsertTemplateAsyncTask extends AsyncTask<Template, Void, Void> {
        private SwimpoolDao swimpoolDao;

        private InsertTemplateAsyncTask(SwimpoolDao swimpoolDao) {
            this.swimpoolDao = swimpoolDao;
        }

        @Override
        protected Void doInBackground(Template... templates) {
            swimpoolDao.addTemplate(templates[0]);
            return null;
        }
    }
    private static class InsertAttRepAsyncTask extends AsyncTask<AttendanceReport, Void, Void> {
        private SwimpoolDao swimpoolDao;

        private InsertAttRepAsyncTask(SwimpoolDao swimpoolDao) {
            this.swimpoolDao = swimpoolDao;
        }

        @Override
        protected Void doInBackground(AttendanceReport... attendanceReports) {
            swimpoolDao.addAttRep(attendanceReports[0]);
            return null;
        }
    }

}
