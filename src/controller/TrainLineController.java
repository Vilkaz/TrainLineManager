package controller;

import javafx.geometry.Insets;
import javafx.scene.Node;
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

    private static VBox getTrainLineCreator(){
        ColorPicker color = new ColorPicker(Color.BLACK);
        ChoiceBox choiceBox = TrainLineController.getChoiceBox();
        Text lineNumberPickerDescription = new Text("Liniennummer");
        HBox lineNumberPicker = new HBox(lineNumberPickerDescription, choiceBox);
        VBox lineCreator = new VBox(lineNumberPicker, color);
        lineCreator.setPadding(new Insets(10,10,10,10));
        lineCreator.getStyleClass().add("lineCreator");
        return  lineCreator;
    }

    private static ChoiceBox getChoiceBox(){
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
        TrainLine trainLine = new TrainLine((int)choiceBox.getValue(), colorPicker.getValue());
        return trainLine;
    }
}
