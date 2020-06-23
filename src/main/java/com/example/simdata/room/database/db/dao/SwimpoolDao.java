package com.example.simdata.room.database.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.simdata.room.database.db.entity.Activitytype;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Location;
import com.example.simdata.room.database.db.entity.Swimpool;
import com.example.simdata.room.database.db.entity.Template;

import java.util.List;

@Dao
public interface SwimpoolDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLocation(Location location);
    @Query("select * from location")
    LiveData<List<Location>> getLocation();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSwimpool(Swimpool swimpool);
    @Query("select * from swimpool")
    List<Swimpool>  getSwimpool();

    @Query("select * from swimpool")
    LiveData <List<Swimpool>>  getAllSwimmingPools();

    @Query("select * from swimpool where id = :id")
    List<Swimpool> getAllNamesByPoolId (int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTemplate(Template template);

    @Query("select * from template")
    LiveData<List<Template>> getAllTemplates();

    @Query("select * from template where swimpool_id = :id ")
    LiveData <List<Template>> getTemplates (int id);

    @Query("select * from template where id = :id ")
    LiveData <List<Template>> showTemplateId (int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAttRep(AttendanceReport attendanceReports);

    @Query("select * from attendance_report where swimpool_id = :id ")
    LiveData <List<AttendanceReport>> getSwimmingPoolReports (int id);

    @Query("select * from attendance_report")
    List<AttendanceReport> getAllReports();


    @Update
    void update(Location... locations);
    @Update
    void update(Swimpool... swimpools);
    @Update
    void update(Template... templates);
    @Update
    void update(AttendanceReport... attendanceReports);


    @Delete
    void delete(Location locations);
    @Delete
    void delete(Swimpool swimpools);
    @Delete
    void delete(Template templates);
    @Delete
    void deleteAttReport(AttendanceReport attendanceReports);


    @Query("delete from attendance_report where id = :id ")
    void deleteAttReport(int id);

//införa querys här
}
