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
import objects.CompletedPoloDoor;
import objects.PoloDoor;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoloDoorsPage implements Initializable {

    private static final Logger logger = Logger.getLogger( PoloDoorsPage.class.getName());

    @FXML
    private ChoiceBox<String> doorChoice;

    @FXML
    private Spinner<Integer> quantity;


    @FXML
    private TableView<CompletedPoloDoor> table;

    @FXML
    private TableColumn<CompletedPoloDoor, String> nameColumn;

    @FXML
    private TableColumn<CompletedPoloDoor, Integer> quantityColumn;


    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        HashMap<String, PoloDoor> doors = DataManager.getPoloDoors();

        List<String> list = new ArrayList<>( doors.keySet() );

        doorChoice.setValue( list.get( 0 ) );
        ObservableList<String> observableList = FXCollections.observableList( list );
        doorChoice.setItems( observableList );

        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "doorName" ) );
        quantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
    }

    @FXML
    public void insertButtonPressed(ActionEvent event) {
        CompletedPoloDoor door = new CompletedPoloDoor( doorChoice.getSelectionModel().getSelectedItem(), LocalDate.now(), quantity.getValue() );
        DataManager.addCompletedPoloDoor( door );
        table.getItems().add( door );
        quantity.getValueFactory().setValue( 1 );
        logger.log(Level.INFO, "DATA INSERTED: " + doorChoice.getSelectionModel().getSelectedItem() + " - " + quantity.getValue());
    }

    @FXML
    public void confirmInsertedData(ActionEvent event) {
        DataManager.confirmCompletedPoloDoors();
        table.getItems().clear();
        logger.log(Level.INFO, "INSERTED DATA CONFIRMED");
    }

    @FXML
    public void rollbackInsertedData(ActionEvent event) {
        try {
            CompletedPoloDoor removed = DataManager.rollbackCompletedPoloDoor();
            table.getItems().remove( removed );
            logger.log(Level.INFO, "DATA ROLLBACK: " + removed);
        } catch ( NoSuchElementException e ) {
            logger.log(Level.WARNING, "CURRENT POLO DOORS SESSION HOLDS NO DATA");
        }
    }
}
