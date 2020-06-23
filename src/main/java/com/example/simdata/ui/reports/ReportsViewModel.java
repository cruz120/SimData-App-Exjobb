package com.example.simdata.ui.reports;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;


import com.example.simdata.SwimmingPoolDataRepository;
import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.dao.SwimpoolDao;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Swimpool;

import java.util.List;

public class ReportsViewModel extends AndroidViewModel {

    private SwimmingPoolDataRepository sRepo;
    private List<AttendanceReport> allReports;
    private List<Swimpool> allSwimmingPoolsNames;

    public ReportsViewModel (@NonNull Application application){
        super(application);
        sRepo = new SwimmingPoolDataRepository(application);
        allReports =  sRepo.getAllReports();
        allSwimmingPoolsNames = sRepo.getAllSwimmingPoolsNames();


    }
    public List<AttendanceReport> getAllReports() {
        return allReports;
    }

    public List<Swimpool> getAllSwimmingPoolsNames(){return allSwimmingPoolsNames;}

    public void deleteAttReport(AttendanceReport attendanceReport){ sRepo.deleteAttReport(attendanceReport); }

}
