package com.example.simdata.ui.swimpoolreports;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.dao.SwimpoolDao;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Location;
import com.example.simdata.room.database.db.entity.Swimpool;

import java.util.ArrayList;
import java.util.List;

public class SwimpoolReportsViewModel extends AndroidViewModel {


    private SwimpoolDao swimpoolDao;
    private LiveData<List<Swimpool>> swimpoolLiveData;
    private LiveData<List<AttendanceReport>> attendanceReportLiveData;

    public SwimpoolReportsViewModel (@NonNull Application application){
        super(application);
        swimpoolDao = SwimpoolDatabase.getInstance(application).swimpoolDao();
        swimpoolLiveData = swimpoolDao.getAllSwimmingPools();


    }
    public LiveData<List<Swimpool>> getSwimpoolLiveData() {
        return swimpoolLiveData;
    }

    public  LiveData<List<AttendanceReport>> getSwimmingPoolReports (int id){
        attendanceReportLiveData = swimpoolDao.getSwimmingPoolReports(id);
        return  attendanceReportLiveData;
    }
}
