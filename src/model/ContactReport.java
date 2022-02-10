package model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class ContactReport {
    private int customerID;
    private int appID;
    private String description;
    private String type;
    private String location;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String title;

    public ContactReport(){}

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setCustomerID(int id){
        this.customerID = id;
    }
    public int getCustomerID(){
        return customerID;
    }

    public void setAppID(int id){
        this.appID = id;
    }

    public int getAppID(){
        return appID;
    }

    public void setDescription(String desc){
        this.description = desc;
    }

    public String getDescription(){
        return description;
    }

    public void setAppType(String type){
        this.type = type;
    }

    public String getAppType(){
        return type;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
    public void setStart(ZonedDateTime start){
        this.start = start;
    }

    public ZonedDateTime getStart(){
        return start;
    }

    public void setEnd(ZonedDateTime end){
        this.end = end;
    }

    public ZonedDateTime getEnd(){
        return end;
    }
}
