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

    @FXML
    private ChoiceBox<String> months;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        HashMap<String, BQWindow> windows = DataManager.getBqWindows();

        List<String> list = new ArrayList<>( windows.keySet() );

        LocalDate date = LocalDate.now();
        setMonthsBoxValue(date.getMonthValue());

        windowChoice.setValue( list.get( 0 ) );
        ObservableList<String> observableList = FXCollections.observableList( list );
        windowChoice.setItems( observableList );

        nameColumn.setCellValueFactory( new PropertyValueFactory<>( "windowName" ) );
        quantityColumn.setCellValueFactory( new PropertyValueFactory<>( "quantity" ) );
    }

    private void setMonthsBoxValue(int month) {
        switch (month) {
            case 1:
                months.setValue("Jaanuar");
                break;
            case 2:
                months.setValue("Veebruar");
                break;
            case 3:
                months.setValue("Märts");
                break;
            case 4:
                months.setValue("Aprill");
                break;
            case 5:
                months.setValue("Mai");
                break;
            case 6:
                months.setValue("Juuni");
                break;
            case 7:
                months.setValue("Juuli");
                break;
            case 8:
                months.setValue("August");
                break;
            case 9:
                months.setValue("September");
                break;
            case 10:
                months.setValue("Oktoober");
                break;
            case 11:
                months.setValue("November");
                break;
            case 12:
                months.setValue("Detsember");
                break;
        }
    }

    private int getMonthAsInteger(String month) {
        switch (month) {
            case "Jaanuar":
                return 1;
            case "Veebruar":
                return 2;
            case "Märts":
                return 3;
            case "Aprill":
                return 4;
            case "Mai":
                return 5;
            case "Juuni":
                return 6;
            case "Juuli":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "Oktoober":
                return 10;
            case "November":
                return 11;
            case "Detsember":
                return 12;
        }
        return -1;
    }

    @FXML
    public void insertButtonPressed(ActionEvent event) {
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), getMonthAsInteger(months.getValue()), 1);
        CompletedBQWindow window = new CompletedBQWindow( windowChoice.getSelectionModel().getSelectedItem(), date, quantity.getValue() );
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
