package gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import main.DataManager;
import objects.CompletedBQDoor;
import objects.CompletedBQWindow;
import objects.CompletedPoloDoor;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainPage implements Initializable {

    private static final Logger logger = Logger.getLogger( BQWindowsPage.class.getName() );

    @FXML
    private ChoiceBox<String> months;

    @FXML
    private Label bqDoorsMonthlyCounter;

    @FXML
    private Label bqWindowsMonthlyCounter;

    @FXML
    private Label poloDoorsMonthlyCounter;

    @FXML
    private Label bqDoorsAllTimeCounter;

    @FXML
    private Label bqWindowsAllTimeCounter;

    @FXML
    private Label poloDoorsAllTimeCounter;

    @FXML
    private Label mainLabel;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {
        setMainLabel( LocalDate.now().getMonthValue() );

        bqDoorsMonthlyCounter.setText( String.valueOf( calculateMonthlyBQDoors( LocalDate.now().getMonthValue() ) ) );
        bqWindowsMonthlyCounter.setText( String.valueOf( calculateMonthlyBQWindows( LocalDate.now().getMonthValue() ) ) );
        poloDoorsMonthlyCounter.setText( String.valueOf( calculateMonthlyPoloDoors( LocalDate.now().getMonthValue() ) ) );

        bqDoorsAllTimeCounter.setText( String.valueOf( calculateAllTimeBQDoors() ) );
        bqWindowsAllTimeCounter.setText( String.valueOf( calculateAllTimeBQWindows() ) );
        poloDoorsAllTimeCounter.setText( String.valueOf( calculateAllTimePoloDoors() ) );

        months.getSelectionModel().selectedIndexProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed( ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue ) {
                setMainLabel( newValue.intValue() + 1 );
            }
        } );
    }

    private void setMainLabel(int month) {
        switch ( month ) {
            case 1:
                mainLabel.setText( "Jaanuari kuus valmistatud" );
                break;
            case 2:
                mainLabel.setText( "Veebruari kuus valmistatud" );
                break;
            case 3:
                mainLabel.setText( "Märtsi kuus valmistatud" );
                break;
            case 4:
                mainLabel.setText( "Aprilli kuus valmistatud" );
                break;
            case 5:
                mainLabel.setText( "Mai kuus valmistatud" );
                break;
            case 6:
                mainLabel.setText( "Juuni kuus valmistatud" );
                break;
            case 7:
                mainLabel.setText( "Juuli kuus valmistatud" );
                break;
            case 8:
                mainLabel.setText( "Augusti kuus valmistatud" );
                break;
            case 9:
                mainLabel.setText( "Septembri kuus valmistatud" );
                break;
            case 10:
                mainLabel.setText( "Oktoobri kuus valmistatud" );
                break;
            case 11:
                mainLabel.setText( "Novembri kuus valmistatud" );
                break;
            case 12:
                mainLabel.setText( "Detsembri kuus valmistatud" );
                break;
        }
    }

    private int calculateMonthlyBQDoors( int month ) {
        List<CompletedBQDoor> completedBQDoors = DataManager.getCompletedBQDoors();
        int count = 0;

        for (CompletedBQDoor completedDoor : completedBQDoors) {
            if (completedDoor.getDate().getMonthValue() == month) {
                count += completedDoor.getQuantity();
            }
        }

        return count;
    }

    private int calculateMonthlyBQWindows( int month ) {
        List<CompletedBQWindow> completedBQWindows = DataManager.getCompletedBQWindows();
        int count = 0;

        for (CompletedBQWindow completedWindow : completedBQWindows) {
            if (completedWindow.getDate().getMonthValue() == month) {
                count += completedWindow.getQuantity();
            }
        }

        return count;
    }

    private int calculateMonthlyPoloDoors( int month ) {
        List<CompletedPoloDoor> completedPoloDoors = DataManager.getCompletedPoloDoors();
        int count = 0;

        for (CompletedPoloDoor completedDoor : completedPoloDoors) {
            if (completedDoor.getDate().getMonthValue() == month) {
                count += completedDoor.getQuantity();
            }
        }

        return count;
    }

    private int calculateAllTimeBQDoors() {
        List<CompletedBQDoor> completedBQDoors = DataManager.getCompletedBQDoors();
        int count = 0;

        for (CompletedBQDoor completedDoor : completedBQDoors) {
                count += completedDoor.getQuantity();
        }

        return count;
    }

    private int calculateAllTimeBQWindows( ) {
        List<CompletedBQWindow> completedBQWindows = DataManager.getCompletedBQWindows();
        int count = 0;

        for (CompletedBQWindow completedWindow : completedBQWindows) {
                count += completedWindow.getQuantity();
        }

        return count;
    }

    private int calculateAllTimePoloDoors(  ) {
        List<CompletedPoloDoor> completedPoloDoors = DataManager.getCompletedPoloDoors();
        int count = 0;

        for (CompletedPoloDoor completedDoor : completedPoloDoors) {
                count += completedDoor.getQuantity();
        }

        return count;
    }
}
