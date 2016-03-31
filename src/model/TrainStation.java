package model;

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
    private boolean endzone;
    private List neighbors = new ArrayList<Neighbor>();
    private Pane node;

    public TrainStation(int id, String name, int zone, boolean endzone, Color color,MouseEvent event) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.endzone = endzone;
        this.icon = new StationIcon(color);
        this.node = new Pane(new Text(this.name), icon.getNode());
        node.setLayoutX(event.getX());
        node.setLayoutY(event.getY());
    }


    public void addNeighbor(Neighbor neighbor){
        this.neighbors.add(neighbor);
    }

    public Node getNode(){
        return node;
    }

    //region getter and setter



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

    public boolean isEndzone() {
        return endzone;
    }


    //endregion getter and setter


}
