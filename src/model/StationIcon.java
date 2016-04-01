package model;

import controller.ContentController;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Created by Vilkazzz on 27/03/2016.
 */
public class StationIcon implements HasNode {
    private double x;
    private double y;
    private double radius = GeneralSettings.getIconRadius();
    private Color color;
    private Circle regularIcon;
    private int lineNr;
    private Node endstationIcon;
    private boolean endstation;
    private int stationId;


    public StationIcon(TrainStation station) {
        this.color = station.getColor();
        this.lineNr = station.getLineNr();
        this.endstation = station.isEndStation();
        this.stationId = station.getId();
        this.endstationIcon = getEndstationIcon();
        this.regularIcon = getRegularIcon(station);
    }

    private Circle getRegularIcon(TrainStation station) {
        regularIcon = new Circle(this.radius);
        regularIcon.setStroke(GeneralSettings.getICON_STROKE_COLOR());
        regularIcon.setStrokeWidth(GeneralSettings.getICON_STROKE_WIDTH());
        regularIcon.setFill(color);
        regularIcon.getStyleClass().add("stationIcon");

        regularIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                station.getCenterPane().setOnMouseMoved(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Pane pane = (Pane) ContentController.getTrainStationById(stationId).getNode();
                        regularIcon.setLayoutX(event.getX()-pane.getLayoutX());
                        regularIcon.setLayoutY(event.getY()-pane.getLayoutY());
                    }
                });

            }
        });


        regularIcon.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("maus released");
//                station.getCenterPane().setOnMouseMoved(null);
            }
        });
        return regularIcon;


    }

    public StationIcon(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        regularIcon = new Circle(this.x, this.y, this.radius);
        regularIcon.setFill(color);
    }

    @Override
    public Node getNode() {
        return (endstation) ? endstationIcon : regularIcon;
    }


    public Node getEndstationIcon() {
        Text text = new Text(Integer.toString(this.lineNr));
        HBox box = new HBox(text);
        box.getStyleClass().add("endStationIcon");
        String borderColor = this.color.toString();
        box.setStyle("-fx-border-color:#"+borderColor);
        return box;
    }
}
