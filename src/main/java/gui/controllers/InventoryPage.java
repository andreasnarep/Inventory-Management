package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InventoryPage {
    @FXML private Button buttonOne;

    @FXML
    private void buttonClicked() {
        buttonOne.setText("BUTTON CLICKED!!");
    }
}
