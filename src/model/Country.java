package model;

public class Country {
    private String countryName;
    private int countryID;

    public Country(String name){
    this.countryName = name;

    }
    public void setName(String name){
        this.countryName = name;
    }

    public String getName(){
        return countryName;
    }

    public void setID(int id){
        this.countryID = id;
    }

    public int getID(){
        return countryID;
    }


}
