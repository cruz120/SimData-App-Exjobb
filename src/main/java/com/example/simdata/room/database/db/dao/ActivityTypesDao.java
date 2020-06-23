package com.example.simdata.room.database.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.simdata.room.database.db.entity.Activitytype;

import java.util.List;

@Dao
public interface ActivityTypesDao {


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void addActivityType(Activitytype activitytypes);
    @Query("select * from activitytype")
    List<Activitytype> getActivityType();

    @Update
    void update(Activitytype activitytypes);

    @Delete
    void delete(Activitytype activitytypes);

}
