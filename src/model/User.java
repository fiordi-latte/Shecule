package model;

public class User {
    private String userName;
    private String userPassword;

    public User(){}

        public String getUsername(){
            return userName;
        }

        public String getPassword(){
            return userPassword;
        }

        public void setUserName(String userName){
            this.userName = userName;
        }

        public void setUserPassword(String userPassword){
            this.userPassword = userPassword;
        }
    }

