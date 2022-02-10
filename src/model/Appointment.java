package model;

import util.ContactMgmt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private int id;
    private String title;
    private String location;
    private String type;
    private String description;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private ZonedDateTime localStartTime;
    private ZonedDateTime localEndTime;
    private LocalDateTime createTime;
    private String createdBy;
    private LocalDateTime updateTime;
    private String updatedBy;
    private int cid;
    private int uid;
    private int contactID;
    private String contactName;
    private String formattedEndTime;
    private String formattedStartTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

    public Appointment(){}

    public void setID(int id){

        this.id = id;
    }

    public int getId(){
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

    public void setStartTime(ZonedDateTime startTime){
        this.startTime = startTime;
        setLocalStartTime();
        formatStart();

    }

    public void setLocalStartTime(){
        this.localStartTime = startTime.toLocalDateTime().atZone(ZoneId.systemDefault());
    }

    public ZonedDateTime getLocalStartTime(){
        return localStartTime;
    }


    public ZonedDateTime getStartTime(){
        return startTime;
    }

    public void setEndTime(ZonedDateTime endTime){
        this.endTime = endTime;
        setLocalEndTime();
        formatEnd();

    }

    public void setLocalEndTime(){
        this.localEndTime = endTime.toLocalDateTime().atZone(ZoneId.systemDefault());
    }

    public ZonedDateTime getLocalEndTime(){
        return localEndTime;
    }


    public ZonedDateTime getEndTime(){
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
        setContactName();
    }

    public void setContactName(){
        this.contactName = ContactMgmt.getContactNameByID(contactID);
    }

    public String getContactName(){
        return contactName;
    }
    public int getContactID(){
        return contactID;
    }

    public void formatStart(){
        this.formattedStartTime = localStartTime.format(formatter);
    }

    public String getFormattedStartTime(){
        return formattedStartTime;
    }

    public void formatEnd(){
        this.formattedEndTime = localEndTime.format(formatter);

    }

    public String getFormattedEndTime(){
        return formattedEndTime;
    }

}
