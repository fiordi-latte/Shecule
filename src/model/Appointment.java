package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {

    private String id;
    private String title;
    private String location;
    private String type;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private String createdBy;
    private LocalDateTime updateTime;
    private String updatedBy;
    private String cid;
    private String uid;
    private String contactID;

    public Appointment(){}

    public void setID(String id){

        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
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

    public void setCid(String cid){
        this.cid = cid;
    }

    public String getCid(){
        return cid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getUid(){
        return uid;
    }

    public void setContactID(String contactID){
        this.contactID = contactID;
    }

    public String getContactID(){
        return contactID;
    }

}
