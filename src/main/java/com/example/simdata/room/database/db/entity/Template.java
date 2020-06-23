package com.example.simdata.room.database.db.entity;




import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "template", foreignKeys =
@ForeignKey(entity = Swimpool.class,parentColumns = "id",childColumns = "swimpool_id"))
public class Template {

    @PrimaryKey
    @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="swimpool_id")
    private  int swimpool_id;

    @ColumnInfo(name="partition")
    private String partition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSwimpool_id() {
        return swimpool_id;
    }

    public void setSwimpool_id(int swimpool_id) {
        this.swimpool_id = swimpool_id;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }
}
