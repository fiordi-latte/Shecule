package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {

    private int id;
    private String title;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private String createdBy;
    private LocalDateTime updateTime;
    private String updatedBy;
    private int cid;
    private int uid;
    private int contactID;

    public Appointment(){}

    public void setID(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime){
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    public void setCreateTime(LocalDateTime createTime){
        this.createTime = createTime;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy){
        this.updatedBy = updatedBy;
    }

    public void setUpdateTime(LocalDateTime updateTime){
        this.updateTime = updateTime;
    }

    public void setCid(int cid){
        this.cid = cid;
    }

    public int getCid(){
        return cid;
    }

    public void setUid(int uid){
        this.uid = uid;
    }

    public int getUid(){
        return uid;
    }

    public void setContactID(int contactID){
        this.contactID = contactID;
    }

    public int getContactID(){
        return contactID;
    }

}
