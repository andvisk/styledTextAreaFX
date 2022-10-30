package styledtextareafx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class App extends Application {

    private static Logger log = LogManager.getLogger(App.class);

    private Stage primaryStage;
    private ControllerMain controller;

    public static void main(String[] args) {

        Configurator.initialize(null, System.getProperty("user.dir") + File.separator
                + "src/main/resources/log4j2.properties");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {

            this.primaryStage = primaryStage;
            initPrimaryStage(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPrimaryStage(Stage primaryStage) {

        StackPane rootElement = new StackPane();
        rootElement.setAlignment(Pos.TOP_LEFT);
        rootElement.setBackground(Background.EMPTY);

        controller = new ControllerMain();
        controller.init(primaryStage, rootElement);

    }

    public void stop(){
 int ii = 0;
    }

}
