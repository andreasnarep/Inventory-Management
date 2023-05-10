package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainPage {
    @FXML
    private Button button;

    @FXML
    private void buttonPressed() {
        button.setText("BUTTON WAS PRESSED!!");
    }
}
