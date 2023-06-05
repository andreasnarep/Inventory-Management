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
import objects.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BQWindowsPage implements Initializable {

    private static final Logger logger = Logger.getLogger(BQWindowsPage.class.getName());

    @FXML
    private ChoiceBox<String> windowChoice;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private TableView<CompletedBQWindow> table;

    @FXML
    private TableColumn<CompletedBQWindow, String> nameColumn;

    @FXML
    private TableColumn<CompletedBQWindow, Integer> quantityColumn;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        HashMap<String, BQWindow> windows = DataManager.getBqWindows();

        List<String> list = new ArrayList<>( windows.keySet() );

        windowChoice.setValue( list.get( 0 ) );
        ObservableList<String> observableList = FXCollections.observableList( list );
        windowChoice.setItems( observableList );

        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "windowName" ) );
        quantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
    }

    @FXML
    public void insertButtonPressed(ActionEvent event) {
        CompletedBQWindow window = new CompletedBQWindow( windowChoice.getSelectionModel().getSelectedItem(), LocalDate.now(), quantity.getValue() );
        DataManager.addCompletedBQWindow( window );
        table.getItems().add( window );
        quantity.getValueFactory().setValue( 1 );
        logger.log(Level.INFO, "DATA INSERTED: " + windowChoice.getSelectionModel().getSelectedItem() + " - " + quantity.getValue());
    }

    @FXML
    public void confirmInsertedData(ActionEvent event) {
        DataManager.confirmCompletedBQWindows();
        table.getItems().clear();
        logger.log(Level.INFO, "INSERTED DATA CONFIRMED");
    }

    @FXML
    public void rollbackInsertedData(ActionEvent event) {
        try {
            CompletedBQWindow removed = DataManager.rollbackCompletedBQWindow();
            table.getItems().remove( removed );
            logger.log(Level.INFO, "DATA ROLLBACK: " + removed);
        } catch ( NoSuchElementException e ) {
            logger.log(Level.WARNING, "CURRENT BQ WINDOWS SESSION HOLDS NO DATA");
        }
    }
}
