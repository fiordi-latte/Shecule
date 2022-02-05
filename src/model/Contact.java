package model;

public class Contact {

    private int id;
    private String name;
    private String email;

    public Contact(){}

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setID(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

}
