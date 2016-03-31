package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Vilkazzz on 27/03/2016.
 */
public class StationNode{
    private Text name;
    private Pane textPane = new Pane();
    private double x;
    private double y;
    private StationIcon icon;


    public StationNode(String  name, double x, double y, Color color) {
        this.name = new Text(name);
        textPane.getChildren().add(this.name);
        this.x = x;
        this.y = y;
        this.icon = new StationIcon(this.x, this.y, color);
        this.name.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textPane.setLayoutX(event.getX());
                textPane.setLayoutY(event.getY());
            }
        });
    }



}
