package gui.controllers;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.DataManager;
import objects.Material;

import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.*;

public class InventoryPage implements Initializable {

    @FXML
    private TableView<Material> inventoryTable;

    @FXML
    private TableView<Material> lowInventoryTable;

    @FXML
    private TableColumn<Material, String> inventoryTableNameColumn;

    @FXML
    private TableColumn<Material, Integer> inventoryTableQuantityColumn;

    @FXML
    private TableColumn<Material, String> lowInventoryTableNameColumn;

    @FXML
    private TableColumn<Material, Integer> lowInventoryTableQuantityColumn;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        HashMap<String, Material> inventory = DataManager.getInventory();
        List<Material> lowInventory = DataManager.getLowerLimitInventory();

        List<Material> values = new ArrayList<>(inventory.values());

        inventoryTableNameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        inventoryTableQuantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
        lowInventoryTableNameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        lowInventoryTableQuantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );

        inventoryTable.setItems( FXCollections.observableArrayList(values) ) ;
        lowInventoryTable.setItems( FXCollections.observableArrayList(lowInventory) );
    }

    @FXML
    public void refreshTables( MouseEvent mouseEvent ) {
        lowInventoryTable.getItems().clear();
        inventoryTable.getItems().clear();
        DataManager.createLowerLimitInventory();
        HashMap<String, Material> inventory = DataManager.getInventory();
        List<Material> lowInventory = DataManager.getLowerLimitInventory();

        inventoryTable.setItems( FXCollections.observableArrayList(new ArrayList<>(inventory.values())) );
        lowInventoryTable.setItems( FXCollections.observableArrayList(lowInventory) );
    }
}
