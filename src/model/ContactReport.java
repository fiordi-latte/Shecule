package model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class ContactReport {
    private int customerID;
    private int appID;
    private String appDescription;
    private String appType;
    private String location;
    private ZonedDateTime appStart;
    private ZonedDateTime appEnd;
    private String appTitle;

    public ContactReport(){}

    public void setAppTitle(String title){
        this.appTitle = title;
    }

    public String getAppTitle(){
        return appTitle;
    }

    public void setCustomerId(int id){
        this.customerID = id;
    }
    public int getCustomerId(){
        return customerID;
    }

    public void setAppId(int id){
        this.appID = id;
    }

    public int getAppId(){
        return appID;
    }

    public void setAppDescription(String desc){
        this.appDescription = desc;
    }

    public String getAppDescription(){
        return appDescription;
    }

    public void setAppType(String type){
        this.appType = type;
    }

    public String getAppType(){
        return appType;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }
    public void setAppStart(ZonedDateTime start){
        this.appStart = start;
    }

    public ZonedDateTime getAppStart(){
        return appStart;
    }

    public void setAppEnd(ZonedDateTime end){
        this.appEnd = end;
    }

    public ZonedDateTime getAppEnd(){
        return appEnd;
    }
}
