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
import objects.BQWindow;
import objects.CompletedBQDoor;
import objects.CompletedBQWindow;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
        BQWindow[] windows = DataManager.getBqWindows();
        List<String> list = new ArrayList<>();

        for (BQWindow window : windows)
            list.add( window.getWindowName() );

        windowChoice.setValue( list.get( 0 ) );
        ObservableList<String> observableList = FXCollections.observableList( list );
        windowChoice.setItems( observableList );

        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "windowName" ) );
        quantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
    }

    @FXML
    public void insertButtonPressed(ActionEvent event) { //TODO Add handling for exceptions (e.g 0 entered as quantity).
        CompletedBQWindow window = new CompletedBQWindow( windowChoice.getSelectionModel().getSelectedItem(), LocalDate.now(), quantity.getValue() );
        table.getItems().add( window );
        logger.log(Level.INFO, "DATA INSERTED: " + windowChoice.getSelectionModel().getSelectedItem() + " - " + quantity.getValue());
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
