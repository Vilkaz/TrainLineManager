package model;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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
    private Node endstationIcon = getEndstationIcon();
    private boolean endstation;


    public StationIcon(TrainStation station) {
        this.color = station.getColor();
        this.lineNr = station.getLineNr();
        this.endstation = station.isEndStation();
        regularIcon = new Circle(this.radius);
        regularIcon.setStroke(GeneralSettings.getICON_STROKE_COLOR());
        regularIcon.setStrokeWidth(GeneralSettings.getICON_STROKE_WIDTH());
        regularIcon.setFill(color);
        regularIcon.getStyleClass().add("stationIcon");

        regularIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Geklickt");
                regularIcon.setLayoutX(event.getX());
                regularIcon.setLayoutY(event.getY());
            }
        });
        regularIcon.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("DRAG DETECTED");
                regularIcon.setLayoutX(event.getX() - GeneralSettings.getIconRadius() / 2);
                regularIcon.setLayoutY(event.getY() - GeneralSettings.getIconRadius() / 2);
            }
        });
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


    public Text getEndstationIcon() {
        Text text = new Text("" + this.lineNr);
        text.getStyleClass().add("endstationIcon");
        return text;
    }
}
