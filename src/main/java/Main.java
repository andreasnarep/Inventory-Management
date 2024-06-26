import gui.controllers.MainPage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.DataManager;
import main.Logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger( Main.class.getName() );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Logic.start();
        Enumeration<String> loggerNames = LogManager.getLogManager().getLoggerNames();

        while (loggerNames.hasMoreElements()) {
            String loggerName = loggerNames.nextElement();
            Logger logger = LogManager.getLogManager().getLogger(loggerName);
            logger.setLevel(Level.OFF);
        }


        DataManager.start();
        //URL url = new File("src/main/java/gui/fxml/Tabs.fxml").toURI().toURL();
        URL url = new File("src/main/resources/fxml/Tabs.fxml").toURI().toURL();
        //Parent root = FXMLLoader.load(getClass().getResource("../../resources/fxml/example.fxml"));
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setMinHeight( 487 );
        primaryStage.setMinWidth( 668 );
        primaryStage.show();
        logger.log( Level.INFO, "GUI started succesfully." );

        primaryStage.setOnCloseRequest( new EventHandler<WindowEvent>() {
            @Override
            public void handle( WindowEvent windowEvent ) {
                DataManager.writeData();
            }
        } );
    }
}
