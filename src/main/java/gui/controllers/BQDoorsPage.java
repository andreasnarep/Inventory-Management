package gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.DataManager;
import objects.BQDoor;
import objects.CompletedBQDoor;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BQDoorsPage implements Initializable {

    private static final Logger logger = Logger.getLogger(BQDoorsPage.class.getName());

    @FXML
    private ChoiceBox<String> doorChoice;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private TableView<CompletedBQDoor> table;

    @FXML
    private TableColumn<CompletedBQDoor, String> nameColumn;

    @FXML
    private TableColumn<CompletedBQDoor, Integer> quantityColumn;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        BQDoor[] doors = DataManager.getBqDoors();
        List<String> list = new ArrayList<>();

        for (BQDoor door : doors)
            list.add( door.getDoorName() );

        doorChoice.setValue( list.get( 0 ) );
        ObservableList<String> observableList = FXCollections.observableList( list );
        doorChoice.setItems( observableList );

        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "doorName" ) );
        quantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
    }

    @FXML
    public void insertButtonPressed(ActionEvent event) { //TODO Add handling for exceptions (e.g 0 entered as quantity).
        CompletedBQDoor door = new CompletedBQDoor( doorChoice.getSelectionModel().getSelectedItem(), LocalDate.now(), quantity.getValue() );
        table.getItems().add( door );
        logger.log(Level.INFO, "DATA INSERTED: " + doorChoice.getSelectionModel().getSelectedItem() + " - " + quantity.getValue());
    }

    @FXML
    public void confirmInsertedData(ActionEvent event) {
        logger.log(Level.INFO, "INSERTED DATA CONFIRMED");
    }

    @FXML
    public void rollbackInsertedData(ActionEvent event) {
        logger.log(Level.INFO, "DATA ROLLBACK");
    }
}
