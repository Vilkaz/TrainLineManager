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
    private int stationId;
    private final String COMMA = ",";


    private String toJson() {
        String json = "";
        try {
            json += JsonController.getJson("x", this) + COMMA;
            json += JsonController.getJson("y", this) + COMMA;
            json += JsonController.getJson("radius", this) + COMMA;
            json += JsonController.getJson("color", ColorController.getColorHex(color)) + COMMA;

        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        return json;
    }

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
        regularIcon.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                Pane pane = (Pane) station.getNode();
                pane.getChildren().add(new Text("Has Rechte Maustaste Geklickt ! das geht jetzt nimmer weg ! "));
            }
        });


        regularIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pane pane = (Pane) ContentController.getTrainStationById(stationId).getNode().getParent().getParent();
                pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        double xFactor = station.getNode().getParent().getLayoutX() + station.getNode().getLayoutX();
                        double yFactor = station.getNode().getParent().getLayoutY() + station.getNode().getLayoutY();

                        double xNow = event.getX()-station.getNode().getParent().getLayoutX();
                        double yNow = event.getY();
                        regularIcon.setLayoutX(event.getX() - xFactor);
                        regularIcon.setLayoutY(event.getY() - yFactor);

                        station.xProperty().setValue(xNow);
                        station.yProperty().setValue(yNow);
                    }
                });

                pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        pane.setOnMouseMoved(null);
                    }
                });
            }
        });


        regularIcon.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("maus released");
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
        box.setStyle("-fx-border-color:#" + borderColor);
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

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }


    //endregion
}
