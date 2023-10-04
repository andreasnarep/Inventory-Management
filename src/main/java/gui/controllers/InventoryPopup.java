package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.DataManager;
import objects.Material;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InventoryPopup implements Initializable {

    private static String stringName;

    private static int intOldQuantity;
    @FXML
    private Label name;
    @FXML
    private Label oldQuantity;
    @FXML
    private TextField newQuantity;


    @FXML
    public void confirmButtonPressed(ActionEvent event) { //TODO: make the button confirm the changes

        HashMap<String, Material> inventory = DataManager.getInventory();

        try {
            int intNewQuantity = Integer.parseInt(newQuantity.getText());
            Material material = inventory.get(name.getText());

            material.setQuantity(intNewQuantity);
            oldQuantity.setText(String.valueOf(intNewQuantity));
            newQuantity.setText("");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sisestama peab täisarvu!");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle ) {
        name.setText(stringName);
        oldQuantity.setText(String.valueOf(intOldQuantity));
    }

    public static void setStringName(String stringName) {
        InventoryPopup.stringName = stringName;
    }

    public static void setIntOldQuantity(int intOldQuantity) {
        InventoryPopup.intOldQuantity = intOldQuantity;
    }
}
