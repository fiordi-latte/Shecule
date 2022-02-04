package model;

public class Customer {

    private int custID;
    private String custName;
    private String custAddress;
    private int custZip;
    private String custPhone;
    private int custDiv;


    public Customer(String name){
        this.custName = name;
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

    public String getCustAdress(){
        return custAddress;
    }

    public void setCustPhone(String phone){
        this.custPhone = phone;
    }

    public String getCustPhone(){
        return custPhone;
    }

    public void setCustDiv(int div){
        this.custDiv = div;
    }

    public int getCustDiv(){
        return custDiv;
    }




}
