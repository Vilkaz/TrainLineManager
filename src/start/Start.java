package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.GeneralSettings;

public class Start extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/scene.fxml"));
        primaryStage.setTitle("Bahn Plan");
        primaryStage.setScene(new Scene(root, GeneralSettings.getSTART_WINDOW_WIDTH(), GeneralSettings.getSTART_WINDOW_HEIGHT()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
