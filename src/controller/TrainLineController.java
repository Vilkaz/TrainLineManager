package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.TrainLine;

import java.util.ArrayList;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class TrainLineController {

    public static Pane getteTrainLineCreator(Pane parrentNode) {
        Pane trainlineCreator = getTrainLineCreator();
        parrentNode.getChildren().add(trainlineCreator);
        return trainlineCreator;
    }

    private static VBox getTrainLineCreator() {
        ColorPicker color = new ColorPicker(Color.BLACK);
        ChoiceBox choiceBox = TrainLineController.getChoiceBox();
        Text lineNumberPickerDescription = new Text("Liniennummer");
        HBox lineNumberPicker = new HBox(lineNumberPickerDescription, choiceBox);
        VBox lineCreator = new VBox(lineNumberPicker, color);
        lineCreator.setPadding(new Insets(10, 10, 10, 10));
        lineCreator.getStyleClass().add("lineCreator");
        return lineCreator;
    }

    private static ChoiceBox getChoiceBox() {
        ArrayList possibleLineNumbers = ContentController.getFreeTrainLineNumbers();
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll(possibleLineNumbers);
        choiceBox.setValue(choiceBox.getItems().get(0));
        return choiceBox;
    }


    public static TrainLine getTrainlineByTrainlineCreator(Pane trainlineCreator) {
        HBox hBox = (HBox) trainlineCreator.getChildren().get(0);
        ChoiceBox choiceBox = (ChoiceBox) hBox.getChildren().get(1);
        ColorPicker colorPicker = (ColorPicker) trainlineCreator.getChildren().get(1);
        TrainLine trainLine = new TrainLine(ContentController.getIdForNextTrainLine(),
                (int) choiceBox.getValue(),
                colorPicker.getValue());
        return trainLine;
    }

    public static ArrayList<TrainLine> getLinesFromJsonObject(JsonObject data) {
        JsonArray jsonLines = (JsonArray) data.get("lines");
        ArrayList<TrainLine> trainLines = new ArrayList<>();
        for (JsonElement jsonElement : jsonLines) {
            JsonObject obj = (JsonObject) jsonElement;
            TrainLine line = new TrainLine();
            line.setId(Integer.valueOf(obj.get("id").toString()));
            line.setNumber(Integer.valueOf(obj.get("number").toString()));
            line.setColor(ColorController.getColorFromHex(obj.get("color").getAsString()));
            line.setStations(StationController.getStations(obj));
            trainLines.add(line);
        }

        /**
         * nachdem die Trainlines gesetzt sind, will ich erst die connectoren hinzufügen.
         * Grund = ein Station kann alle mögliche Linien in sich haben, am 16.04.2016 ist
         * es noch nicht möglich linien zu erweitern, und es würde auch in erster itteration funktionieren.
         * wenn aber später eine linie mit id 1  auf eine station von linie 2 als nachbar hinzugefügt wird,
         * wird es zu bugs kommen, denn die stationen der linie 1 werden ja zuerst dargestellt, und da versucht er nach
         * man connector auf eine nicht vorhandene station zu machen = null pointer.
         * Um es zu vermeiden baue ich schonmal  die sicherere methode. Ein zweiter durchlauf ist zwar
         * nie optimal, jeder darf es optimieren, nur denkt an meine vorhersage hier ...
         */
        for (JsonElement jsonElement : jsonLines) {
            JsonObject obj = (JsonObject) jsonElement;
            StationConnectorController.setStationConnectors(obj, trainLines);
        }

        return trainLines;
    }


}
