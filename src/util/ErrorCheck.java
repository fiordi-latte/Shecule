package util;

import javafx.scene.control.Alert;

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

    public static void displayError(String error){
        Alert invalidIput = new Alert(Alert.AlertType.ERROR);
        invalidIput.setTitle("ERROR");
        invalidIput.setContentText(error);
        invalidIput.showAndWait();

    }

}
