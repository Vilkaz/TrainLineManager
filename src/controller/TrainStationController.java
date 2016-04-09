package controller;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.TrainStation;

/**
 * Created by Vilkazzz on 08/04/2016.
 */
public class TrainStationController {

    public static VBox getAddExsistingTrainStaion(TrainStation station) {
        Text line1 = new Text("Möchten Sie die Station:" + station.getName());
        Text line2 = new Text("von der Linie Nr." + station.getLineNr());
        Text line3 = new Text("als Nachbarstation hinzufügen?");
        VBox vBox = new VBox(line1, line2, line3);
        return vBox;
    }

    public static void disableAllStationClickListeners() {
        for (TrainStation station : ContentController.getAllStations()) {
            station.getNode().setOnMouseClicked(null);
        }
    }
}
