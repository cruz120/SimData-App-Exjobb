package com.example.simdata.room.database.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "attendance_report",foreignKeys =
@ForeignKey(entity = Swimpool.class, parentColumns = "id", childColumns = "swimpool_id"))
public class AttendanceReport {

     @PrimaryKey(autoGenerate = true)
     @ColumnInfo(name="id")
     private int id;

     @ColumnInfo(name="reporttime")
     private String reporttime;

     @ColumnInfo(name="swimpool_id")
     private  int swimpool_id;

     @ColumnInfo(name="partition_result")
     private String partitionResult;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public int getSwimpool_id() {
        return swimpool_id;
    }

    public void setSwimpool_id(int swimpool_id) {
        this.swimpool_id = swimpool_id;
    }

    public String getPartitionResult() {
        return partitionResult;
    }

    public void setPartitionResult(String partitionResult) {
        this.partitionResult = partitionResult;
    }
}
