package com.example.simdata.room.database.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "location")
public class Location {


          @PrimaryKey
          @ColumnInfo(name="id")
          private int id;

          //name | varchar(30)
          @ColumnInfo(name= "name")
          private String name;

          //login_user | varchar(30)
         // @ColumnInfo(name = "user")
         // private String user;


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

  //  public String getUser() { return user; }

 //   public void setUser(String user) {this.user = user;}
}
