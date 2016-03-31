package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Vilkazzz on 27/03/2016.
 */
public class StationIcon implements HasNode{
    private double x;
    private double y;
    private double radius = GeneralSettings.getIconRadius();
    private Color color;
    private Circle icon;


    public StationIcon(Color color) {
        this.color = color;
        icon = new Circle(this.radius);
        icon.setFill(color);
    }

    public StationIcon(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        icon = new Circle(this.x, this.y, this.radius);
        icon.setFill(color);
    }

    @Override
    public Node getNode() {
        return icon;
    }


}
