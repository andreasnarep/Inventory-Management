import gui.controllers.MainPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.DataManager;
import main.Logic;

import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger( Main.class.getName() );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Logic.start();
        DataManager.start();
        URL url = new File("src/main/java/gui/fxml/Tabs.fxml").toURI().toURL();
        //Parent root = FXMLLoader.load(getClass().getResource("../../resources/fxml/example.fxml"));
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Varola");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
        logger.log( Level.INFO, "GUI started succesfully." );
    }
}
