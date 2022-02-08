package util;

public class ErrorCheck {

    public static Boolean isEmpty(String s){
        if(s == null || s.isEmpty()){
            return true;
        }

        return false;
    }

    public static Boolean isInt(String s){
        try{
            Double.parseDouble(s);
            return true;
        } catch(Exception e){
            return false;
        }
    }

}
