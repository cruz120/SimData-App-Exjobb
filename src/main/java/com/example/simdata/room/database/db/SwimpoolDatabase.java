package com.example.simdata.room.database.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.simdata.room.database.db.dao.ActivityTypesDao;
import com.example.simdata.room.database.db.dao.SwimpoolDao;
import com.example.simdata.room.database.db.entity.Activitytype;
import com.example.simdata.room.database.db.entity.AttendanceReport;
import com.example.simdata.room.database.db.entity.Location;
import com.example.simdata.room.database.db.entity.Swimpool;
import com.example.simdata.room.database.db.entity.Template;

@Database(entities = {Location.class, Template.class, Swimpool.class,
        AttendanceReport.class, Activitytype.class},version = 4)
public abstract class SwimpoolDatabase extends RoomDatabase {

    private static SwimpoolDatabase instance;

    public abstract SwimpoolDao swimpoolDao();
    public abstract ActivityTypesDao activityTypesDao();

    public static synchronized SwimpoolDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SwimpoolDatabase.class, "sim_database").allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
