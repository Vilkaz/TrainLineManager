package model;

import controller.ColorController;
import controller.JsonController;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class TrainLine {
    private int id;
    private int number;
    private String name = "default TrainLine";
    private List<TrainStation> stations = new ArrayList<TrainStation>();
    private List<StationConnector> connectors = new ArrayList<StationConnector>();
    private Color color;

    public TrainLine(int number, Color color) {
        this.number = number;
        this.color = color;
    }


    public TrainStation getLastStation() {
        return stations.get(stations.size() - 1);
    }

    public void addStation(TrainStation trainStation) {
        stations.add(trainStation);
    }

    public boolean hasStations() {
        return stations.size() != 0;
    }

    public void addConnector(StationConnector connector) {
        connectors.add(connector);
    }

    public String toJson() {
        String json = "{";
        json += JsonController.getJson("id", this);
        json += JsonController.getJson("number", this);
        json += JsonController.getJson("name", this);
        json += JsonController.getJson("color", ColorController.getColorHex(color));
        json += addStationsToJson();
        json += addConnectorToJson();
        return json + "}";
    }

    private String addStationsToJson() {
        String json="\"stations\":[";
        for (TrainStation station : stations){
            json+=station.toJson();
            if (station.getId()!=stations.size()-1){
                json+=",";
            }
        }
        json+="]";
        return json;
    }

    private String addConnectorToJson() {
        String json = "{";
        for (StationConnector connector : connectors) {
            json += connector.toJson();
        }
        return "";
    }


    //region getter and setter

    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (TrainStation trainStation : stations) {
            nodes.add(trainStation.getNode());
        }
        for (StationConnector connector : connectors) {
            nodes.add(connector.getNode());
        }
        return nodes;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TrainStation> getStations() {
        return stations;
    }

    public List<StationConnector> getConnectors() {
        return connectors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }


//endregion getter and setter

}
