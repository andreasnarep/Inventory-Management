package gui.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.DataManager;
import objects.Material;

import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

        List<Material> values = new ArrayList<>( inventory.values() );

        inventoryTableNameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        inventoryTableQuantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
        lowInventoryTableNameColumn.setCellValueFactory( new PropertyValueFactory<>( "name" ) );
        lowInventoryTableQuantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );

        inventoryTable.setItems( FXCollections.observableArrayList( values ) );
        lowInventoryTable.setItems( FXCollections.observableArrayList( lowInventory ) );

        inventoryTable.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<Material>() {
            @Override
            public void changed( ObservableValue<? extends Material> observableValue, Material oldValue, Material newValue ) {
                String file = "src/main/resources/data/pictures/" + newValue.getName() + ".jpg";

                try {
                    Image image = new Image( new FileInputStream( file ) );
                    ImageView imageView = new ImageView();
                    imageView.setImage( image );
                    imageView.setFitWidth( 300 );
                    imageView.setFitHeight( 300 );

                    Stage stage = new Stage();
                    BorderPane borderPane = new BorderPane();
                    borderPane.getChildren().add( imageView );
                    Scene scene = new Scene( borderPane, 300, 300 );
                    stage.setScene( scene );
                    stage.show();
                } catch ( FileNotFoundException e ) {
                    e.printStackTrace();
                }

                System.out.println( "first - " + observableValue );
                System.out.println( "second - " + oldValue );
                System.out.println( "third - " + newValue );
            }
        } );
    }

    @FXML
    public void refreshTables( MouseEvent mouseEvent ) {
        lowInventoryTable.getItems().clear();
        inventoryTable.getItems().clear();
        DataManager.createLowerLimitInventory();
        HashMap<String, Material> inventory = DataManager.getInventory();
        List<Material> lowInventory = DataManager.getLowerLimitInventory();

        inventoryTable.setItems( FXCollections.observableArrayList( new ArrayList<>( inventory.values() ) ) );
        lowInventoryTable.setItems( FXCollections.observableArrayList( lowInventory ) );
    }
}
