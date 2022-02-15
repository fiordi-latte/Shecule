package util;

import javafx.scene.control.Alert;

public class ErrorCheck {

    /**
     * Returns true if the passed in string is null or empty
     * Used to verify input on the various forms
     * @param s
     * @return Boolean
     */
    public static Boolean isEmpty(String s){
        if(s == null || s.isEmpty()){
            return true;
        }

        return false;
    }

    /**
     * Returns true if the passed in string can be converted to a number
     * Used to verify input on the various forms
     * @param s
     * @return
     */
    public static Boolean isInt(String s){
        try{
            Double.parseDouble(s);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    /**
     * All-purpose function to display an error
     * @param error
     */
    public static void displayError(String error){
        Alert invalidIput = new Alert(Alert.AlertType.ERROR);
        invalidIput.setTitle("ERROR");
        invalidIput.setContentText(error);
        invalidIput.showAndWait();

    }

    public static void displayAlert(String message){
        Alert invalidIput = new Alert(Alert.AlertType.INFORMATION);
        invalidIput.setTitle("Appointment alert");
        invalidIput.setContentText(message);
        invalidIput.showAndWait();

    }

}
