package model;

public class Division {
    private String divisionName;
    private int divisionID;
    private int countryID;

    public Division(String name, int id){
        this.divisionName = name;
        this.divisionID = id;
    }

    public void setName(String name){
        this.divisionName = name;
    }

    public String getName(){
        return divisionName;
    }

    public void setID(int id){
        this.divisionID = id;
    }

    public int getID(){
        return divisionID;
    }

    public int getCountryID(){
        return countryID;
    }

    public void setCountryID(int cid){
        this.countryID = cid;
    }


}
