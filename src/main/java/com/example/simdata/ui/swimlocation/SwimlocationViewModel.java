package com.example.simdata.ui.swimlocation;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.dao.SwimpoolDao;
import com.example.simdata.room.database.db.entity.Location;
import com.example.simdata.room.database.db.entity.Swimpool;

import java.util.List;


public class SwimlocationViewModel extends AndroidViewModel {

    private SwimpoolDao swimpoolDao;
    private LiveData<List<Location>> locationLiveData;
    private LiveData<List<Swimpool>> swimpoolLiveData;

    public SwimlocationViewModel(@NonNull Application application) {
         super(application);
        swimpoolDao = SwimpoolDatabase.getInstance(application).swimpoolDao();
        locationLiveData = swimpoolDao.getLocation();
        swimpoolLiveData = swimpoolDao.getAllSwimmingPools();
    }

    public LiveData<List<Location>> getLocation(){

        return locationLiveData;

    }

    public LiveData<List<Swimpool>> getSwimpoolLiveData() {
        return swimpoolLiveData;
    }
}
