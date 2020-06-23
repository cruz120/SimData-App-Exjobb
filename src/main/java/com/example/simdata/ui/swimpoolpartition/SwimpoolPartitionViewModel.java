package com.example.simdata.ui.swimpoolpartition;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.simdata.SwimmingPoolDataRepository;
import com.example.simdata.room.database.db.SwimpoolDatabase;
import com.example.simdata.room.database.db.dao.SwimpoolDao;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Template;

import java.util.List;

public class SwimpoolPartitionViewModel extends AndroidViewModel {

    private SwimpoolDao swimpoolDao;
    private LiveData<List<Template>> getAllTemplates;
    private LiveData<List<Template>> getTemplate;
    private LiveData <List<Template>> showTemplateId;
    private SwimmingPoolDataRepository swimmingPoolDataRepository;

    public SwimpoolPartitionViewModel (@NonNull Application application){
        super(application);

            swimpoolDao = SwimpoolDatabase.getInstance(application).swimpoolDao();
            getAllTemplates = swimpoolDao.getAllTemplates();
            swimmingPoolDataRepository = new SwimmingPoolDataRepository(application);

    }


    public LiveData<List<Template>> getGetAllTemplates() {
        return getAllTemplates;
    }

    public LiveData <List<Template>> getGetTemplate(int id) {
        getTemplate = swimpoolDao.getTemplates(id);
        return getTemplate;
    }

    public LiveData<List<Template>> getShowTemplateId(int id) {

        showTemplateId = swimpoolDao.showTemplateId(id);
        return showTemplateId;
    }

    public void addAttRep(AttendanceReport attendanceReport){
        swimmingPoolDataRepository.addAttRep(attendanceReport);
    }

}
