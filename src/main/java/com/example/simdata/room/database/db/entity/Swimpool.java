package com.example.simdata.room.database.db.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.simdata.room.database.db.entity.Location;


@Entity(tableName = "swimpool",foreignKeys =
@ForeignKey(entity = Location.class, parentColumns = "id", childColumns = "location_id"))
public class Swimpool {

    @PrimaryKey
    @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="location_id")
    private int location_id;

    @ColumnInfo (name="rect_left")
    private double rect_left;

    @ColumnInfo (name="rect_top")
    private double rect_top;

    @ColumnInfo (name="rect_right")
    private double rect_right;

    @ColumnInfo (name="rect_bottom")
    private double rect_bottom;

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

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public double getRect_left() { return rect_left; }

    public void setRect_left(double rect_left) {
        this.rect_left = rect_left;
    }

    public double getRect_top() { return rect_top; }

    public void setRect_top(double rect_top) {
        this.rect_top = rect_top;
    }

    public double getRect_right() { return rect_right; }

    public void setRect_right(double rect_right) {
        this.rect_right = rect_right;
    }

    public double getRect_bottom() { return rect_bottom; }

    public void setRect_bottom(double rect_bottom) {
        this.rect_bottom = rect_bottom;
    }
}
