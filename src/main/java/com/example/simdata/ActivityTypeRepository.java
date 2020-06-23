package com.example.simdata;

import android.app.Application;
import android.os.AsyncTask;

import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.dao.ActivityTypesDao;
import com.example.simdata.room.database.db.dao.SwimpoolDao;
import com.example.simdata.room.database.db.entity.Activitytype;
import com.example.simdata.room.database.db.entity.Location;

import java.util.List;

public class ActivityTypeRepository {

    private ActivityTypesDao activityTypesDao;

    public ActivityTypeRepository (Application application){
        SwimpoolDatabase database = SwimpoolDatabase.getInstance(application);
        activityTypesDao = database.activityTypesDao();
    }

    public void addActivityType(Activitytype activitytype){
        new InsertActivityTypeAsyncTask(activityTypesDao).execute(activitytype);
    }

    public List<Activitytype>  getActivityType (){
        return activityTypesDao.getActivityType();
    }

    private static class InsertActivityTypeAsyncTask extends AsyncTask<Activitytype, Void, Void> {
        private ActivityTypesDao activityTypesDao;

        private InsertActivityTypeAsyncTask(ActivityTypesDao activityTypesDao) {
            this.activityTypesDao = activityTypesDao;
        }

        @Override
        protected Void doInBackground(Activitytype... activitytypes) {
            activityTypesDao.addActivityType(activitytypes[0]);
            return null;
        }
    }

}
