package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class TrainLine {
    private int id;
    private int number;
    private String name;
    private List<TrainStation> stations = new ArrayList<TrainStation>();
    private List<Conector> connectors;
    private Color color;

    public TrainLine(int number,Color color) {
        this.number = number;
        this.color = color;
    }


    public TrainStation getLastStation(){
        return stations.get(stations.size()-1);
    }

    public void addStation(TrainStation trainStation){
        stations.add(trainStation);
    }

    public boolean hasStations(){
        return stations.size()!=0;
    }



    //region getter and setter

    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (TrainStation trainStation:stations){
            nodes.add(trainStation.getNode());
        }
        return nodes ;
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

    public List<Conector> getConnectors() {
        return connectors;
    }




    //endregion getter and setter

}
