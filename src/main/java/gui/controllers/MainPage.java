package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPage {

    private static final Logger logger = Logger.getLogger(BQWindowsPage.class.getName());

    @FXML
    private Button button;

    @FXML
    private ChoiceBox months;

    @FXML
    private void buttonPressed() {
        button.setText("BUTTON WAS PRESSED!!");
    }

}
