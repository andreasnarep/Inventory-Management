package gui.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.DataManager;
import objects.Material;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, Material> inventory = DataManager.getInventory();
        List<Material> lowInventory = DataManager.getLowerLimitInventory();

        List<Material> values = new ArrayList<>(inventory.values());

        inventoryTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        lowInventoryTableNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lowInventoryTableQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        inventoryTable.setItems(FXCollections.observableArrayList(values));
        lowInventoryTable.setItems(FXCollections.observableArrayList(lowInventory));

        inventoryTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Material>() {
            @Override
            public void changed(ObservableValue<? extends Material> observableValue, Material oldValue, Material newValue) {
                Stage stage = new Stage();
                //BorderPane borderPane = new BorderPane();
                InventoryPopup.setStringName(newValue.getName());
                InventoryPopup.setIntOldQuantity(newValue.getQuantity());

                try {
                    URL url = new File("src/main/resources/fxml/InventoryPopup.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);
                    Scene scene = new Scene(root, 350, 350);
                    stage.setScene(scene);
                    stage.show();
                    //Thread.sleep(5000);
                    //InventoryPopup.giveData(newValue.getName(), newValue.getQuantity());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Label name = new Label("Nimi: " + newValue.getName());
                //Label quantityOldValue = new Label("Vana väärtus: " + newValue.getQuantity());

                //Label newQuantityLabel = new Label("Uus väärtus: ");
                //TextField quantityNewValue = new TextField();

                //HBox hbox = new HBox();
                //hbox.getChildren().addAll(newQuantityLabel, quantityNewValue);
                //hbox.setPadding(new Insets( 5));

                //GridPane elements = new GridPane();
                //elements.addColumn(0, name, quantityOldValue, hbox);
                //elements.setPadding(new Insets(10));

                //Scene scene = new Scene(elements, 300, 300);
                //Scene scene = new Scene(root, 300, 300);
                //stage.setScene(scene);
                //stage.show();

                //System.out.println("first - " + observableValue);
                //System.out.println("second - " + oldValue);
                //System.out.println("third - " + newValue);
            }
        });
    }

    @FXML
    public void refreshTables(MouseEvent mouseEvent) {
        lowInventoryTable.getItems().clear();
        inventoryTable.getItems().clear();
        DataManager.createLowerLimitInventory();
        HashMap<String, Material> inventory = DataManager.getInventory();
        List<Material> lowInventory = DataManager.getLowerLimitInventory();

        inventoryTable.setItems(FXCollections.observableArrayList(new ArrayList<>(inventory.values())));
        lowInventoryTable.setItems(FXCollections.observableArrayList(lowInventory));
    }
}
