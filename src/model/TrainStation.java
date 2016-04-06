package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
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
        this.node.getChildren().addAll(new Text(this.name), icon.getNode());
        node.setLayoutX(event.getX());
        node.setLayoutY(event.getY());
        y.setValue(node.layoutYProperty().getValue()+icon.getNode().layoutYProperty().getValue());
        x.setValue(node.layoutXProperty().getValue()+icon.getNode().layoutXProperty().getValue());
    }

    private Text getText(String stationName){
        Text text = new Text(stationName);

        text.setOnMouseClicked(getTextOnMouseClickedEventHandler());

        return text;
    }

    public EventHandler<? super MouseEvent> getTextOnMouseClickedEventHandler() {
        return null;
    }


    public void addConnector(StationConnector connector){
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
