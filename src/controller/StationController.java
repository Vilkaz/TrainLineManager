package controller;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.GeneralSettings;
import model.StationIcon;
import model.StationNode;
import model.TrainStation;

/**
 * Created by Vilkazzz on 25/03/2016.
 */
public class StationController {

    private static int minZone = GeneralSettings.getMinZone();
    private static int maxZone = GeneralSettings.getMaxZone();

    public static Pane getStationCreator() {
        TextField name = getTextField();
        Text zoneDescription = getText();
        ChoiceBox zoneChooser = getChoiceBox();
        VBox stationCreator = getvBox(name, zoneDescription, zoneChooser);
        return stationCreator;
    }

    private static VBox getvBox(TextField name, Text zoneDescription, ChoiceBox zoneChooser) {
        HBox zoneChoseHBox = gethBox(zoneDescription, zoneChooser);
        VBox stationCreator = getvBox(name, zoneChoseHBox);
        stationCreator.paddingProperty().setValue(new Insets(10, 10, 10, 10));
        stationCreator.getStyleClass().add("stationCreator");
        return stationCreator;
    }

    private static VBox getvBox(TextField name, HBox zoneChoseHBox) {
        VBox stationCreator = new VBox(name, zoneChoseHBox);
        stationCreator.getStyleClass().add("stationCreatorVBox");
        return stationCreator;
    }

    private static HBox gethBox(Text zoneDescription, ChoiceBox zoneChooser) {
        HBox zoneChoseHBox = new HBox(zoneDescription, zoneChooser);
        zoneChoseHBox.getStyleClass().add("stationCreatorZoneChoseHBox");
        return zoneChoseHBox;
    }

    private static ChoiceBox getChoiceBox() {
        ChoiceBox zoneChooser = getZoneChoser();
        zoneChooser.getStyleClass().add("stationCreatorZoneChooser");
        return zoneChooser;
    }

    private static Text getText() {
        Text zoneDescription = new Text("Zone");
        zoneDescription.getStyleClass().add("stationCreatorZoneLabel");
        return zoneDescription;
    }

    private static TextField getTextField() {
        TextField name = new TextField();
        name.setPromptText("Stationname");
        name.getStyleClass().add("stationCreatorName");
        return name;
    }

    private static ChoiceBox getZoneChoser() {
        ChoiceBox zoneChoser = new ChoiceBox();
        for (int i = minZone; i <= maxZone; i++) {
            zoneChoser.getItems().add(i);
        }
        zoneChoser.setValue(zoneChoser.getItems().get(0));
        return zoneChoser;
    }

    public static TrainStation getStationByClick(Pane stationCreator, MouseEvent event, Pane centerPane) {
        TextField nameField = (TextField) stationCreator.getChildren().get(0);
        String name = nameField.getText();
        HBox zoneHBox = (HBox) stationCreator.getChildren().get(1);
        ChoiceBox zoneSelector = (ChoiceBox) zoneHBox.getChildren().get(1);
        int zone = (int) zoneSelector.getValue();
        boolean endzone = !ContentController.trainlineHasStations();
        int id = ContentController.getIdForNextStation();
        Color color = ContentController.getActiveColor();

        TrainStation trainStation = new TrainStation(id, name, zone, endzone, color, ContentController.getActualLineNr(), event, centerPane);
            /**
            * node creation
            */
        return trainStation;
    }

}
