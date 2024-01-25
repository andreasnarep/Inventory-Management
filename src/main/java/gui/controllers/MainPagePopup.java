package gui.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPagePopup implements Initializable {
    private static String monthName;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {

    }

    public static void setMonthName( String monthName ) {
        MainPagePopup.monthName = monthName;
    }
}
