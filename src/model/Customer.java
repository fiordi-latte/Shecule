package model;
import java.time.LocalDateTime;

public class Customer {

    private int custID;
    private String custName;
    private String custAddress;
    private String custZip;
    private String custPhone;
    private String custDiv;
    private int custCid;
    private LocalDateTime custCreateTime;
    private LocalDateTime lastUpdate;

    public Customer(String name){
        this.custName = name;
    }

    public void setCustCid(int cid){
        this.custCid = cid;
    }

    public int getCustCid(){
        return custCid;
    }

    public void setCustName(String name){
        this.custName = name;
    }

    public String getCustName(){
        return custName;
    }

    public void setCustID(int id){
        this.custID = id;
    }

    public int getCustID(){
        return custID;
    }

    public void setCustAddress(String address){
        this.custAddress = address;
    }

    public void setCustZip(String zip){
        this.custZip = zip;
    }

    public String getCustZip(){
        return custZip;
    }

    public String getCustAdress(){
        return custAddress;
    }

    public void setCustPhone(String phone){
        this.custPhone = phone;
    }

    public String getCustPhone(){
        return custPhone;
    }


    public void setCustDiv(String div){
        this.custDiv = div;
    }

    public String getCustDiv(){
        return custDiv;
    }

    public void setCreateTime(LocalDateTime createTime){
        this.custCreateTime = createTime;
    }

    public LocalDateTime getCustCreateTime(){
        return custCreateTime;
    }

    public void setLastUpdate(LocalDateTime updateTime){
        this.lastUpdate = updateTime;
    }

    public LocalDateTime getLastUpdate(){
        return lastUpdate;
    }

}
