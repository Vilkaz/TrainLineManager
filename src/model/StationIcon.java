package model;

import controller.ColorController;
import controller.ContentController;
import controller.JsonController;
import controller.Reflections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.beans.IntrospectionException;
import java.util.ArrayList;

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
    private String stationId;
    private final String COMMA = ",";


    public  String toJson() {
        String json = "{";
            json += JsonController.getJson("x", this) ;
            json += JsonController.getJson("y", this) ;
            json += JsonController.getJson("color", ColorController.getColorHex(color),false);
        return json+"}";
    }

    public StationIcon(TrainStation station) {
        this.color = station.getColor();
        this.lineNr = station.getLineNr();
        this.endstation = station.isEndStation();
        this.stationId = station.getId();
        this.endstationIcon = getEndstationIcon(station);
        this.regularIcon = getRegularIcon(station);
    }

    private Circle getRegularIcon(TrainStation station) {
        regularIcon = new Circle(this.radius);
        regularIcon.setStroke(GeneralSettings.getICON_STROKE_COLOR());
        regularIcon.setStrokeWidth(GeneralSettings.getICON_STROKE_WIDTH());
        regularIcon.setFill(color);
        regularIcon.getStyleClass().add("stationIcon");
        regularIcon.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                Pane pane = (Pane) station.getNode();
                pane.getChildren().add(new Text("Has Rechte Maustaste Geklickt ! das geht jetzt nimmer weg ! "));
            }
        });
        regularIcon.setOnMouseClicked(getStationIconMouseClickEvent(station));
        return regularIcon;
    }


    private EventHandler<MouseEvent> getStationIconMouseClickEvent(TrainStation station) {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pane pane = (Pane) ContentController.getTrainStationById(stationId).getNode().getParent();
                pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        getStationIconMouseMoveEvent(event, station);
                    }
                });

                pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pane.setOnMouseMoved(null);
                    }
                });
            }
        };
        return eventHandler;
    }

    private void getStationIconMouseMoveEvent(MouseEvent event, TrainStation station) {
        double parentsX = station.getNode().getLayoutX();
        double parentsY = station.getNode().getLayoutY();
        regularIcon.setLayoutX(event.getX() - parentsX);
        regularIcon.setLayoutY(event.getY() - parentsY);
        /**
         * code duplication, schlechte lösung, beide icons sollten sich die selben koordinaten teilen !
         * Soll iwelcher Azubi lösen :D
         */
        endstationIcon.setLayoutX(event.getX() - parentsX);
        endstationIcon.setLayoutY(event.getY() - parentsY);
        station.xProperty().setValue(event.getX());
        station.yProperty().setValue(event.getY());
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


    public Node getEndstationIcon(TrainStation station) {
        Text text = new Text(Integer.toString(this.lineNr));
        HBox box = new HBox(text);
        box.getStyleClass().add("endStationIcon");
        String borderColor = this.color.toString();
        box.setStyle("-fx-border-color:#" + borderColor);
        box.setOnMouseClicked(getStationIconMouseClickEvent(station));
        return box;
    }

    //region geter and setter

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Circle getRegularIcon() {
        return regularIcon;
    }

    public void setRegularIcon(Circle regularIcon) {
        this.regularIcon = regularIcon;
    }

    public int getLineNr() {
        return lineNr;
    }

    public void setLineNr(int lineNr) {
        this.lineNr = lineNr;
    }

    public void setEndstationIcon(Node endstationIcon) {
        this.endstationIcon = endstationIcon;
    }

    public boolean isEndstation() {
        return endstation;
    }

    public void setEndstation(boolean endstation) {
        this.endstation = endstation;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }


    //endregion
}
