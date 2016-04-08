package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class TrainStation implements HasNode {
    private int id;
    private Color color;
    private StationIcon icon;
    private String name;
    private int zone;
    private boolean endStation;
    private int lineNr;
    private List neighbors = new ArrayList<Neighbor>();
    private ArrayList<StationConnector> connectors = new ArrayList<StationConnector>();
    private Pane node;
    private Pane centerPane;
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();

    public TrainStation() {
    }

    public TrainStation(int id, String name, int zone, boolean endStation, Color color, int lineNr, MouseEvent event, Pane centerPane) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.endStation = endStation;
        this.color = color;
        this.lineNr = lineNr;
        this.centerPane = centerPane;
        this.node = new Pane();
        this.icon = new StationIcon(this);
        this.node.getChildren().addAll(getText(this.name), icon.getNode());
        node.setLayoutX(event.getX());
        node.setLayoutY(event.getY());
        y.setValue(node.layoutYProperty().getValue() + icon.getNode().layoutYProperty().getValue());
        x.setValue(node.layoutXProperty().getValue() + icon.getNode().layoutXProperty().getValue());
    }



    private VBox getText(String stationName) {
        Text text = new Text(stationName);
        VBox textContainer = new VBox(text);
        text.setOnMouseClicked(getTextOnMouseClickedEventHandler(text));
        return textContainer;
    }

    public EventHandler<? super MouseEvent> getTextOnMouseClickedEventHandler(Text text) {
        VBox textContainer = (VBox) text.getParent();
        text.setOnMouseClicked(null);
        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Slider slider = new Slider(-180, 180, 0);
                slider.valueProperty().bindBidirectional(text.rotateProperty());
                ImageView image = new ImageView("img/move.png");
                image.setFitWidth(GeneralSettings.getMOVE_ICON_WIDTH());
                image.setFitHeight(GeneralSettings.getMOVE_ICON_HEIGTH());
                centerPane = (Pane) node.getParent();
                image.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        centerPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                textContainer.setLayoutX(event.getX() - node.getLayoutX());
                                textContainer.setLayoutY(event.getY() - node.getLayoutY());
                            }
                        });
                        centerPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                centerPane.setOnMouseMoved(null);
                                textContainer.getChildren().removeAll(slider, image);
                            }
                        });
                    }
                });
                textContainer.getChildren().addAll(slider, image);
            }
        };
        return event;
    }


    public void addConnector(StationConnector connector) {
        this.getConnectors().add(connector);
    }

    public void addNeighbor(Neighbor neighbor) {
        this.neighbors.add(neighbor);
    }

    public Pane getNode() {
        return node;
    }

    //region getter and setter


    public List getNeighbors() {
        return neighbors;
    }

    public ArrayList<StationConnector> getConnectors() {
        return connectors;
    }

    public Pane getCenterPane() {
        return centerPane;
    }

    public void setEndStation(boolean endStation) {
        this.endStation = endStation;
    }

    public int getLineNr() {
        return lineNr;
    }

    public void setNode(Pane node) {
        this.node = node;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public StationIcon getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getZone() {
        return zone;
    }

    public boolean isEndStation() {
        return endStation;
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }


//endregion getter and setter


}
