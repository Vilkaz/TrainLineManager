package model;

import controller.ContentController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class StationConnector implements HasNode {
    private TrainStation station1 = null;
    private TrainStation station2 = null;
    private Line line;
    private Color color;
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();


    public TrainStation getStationWithID(int id) {
        return (station1.getId() == id) ? station1 : station2;
    }

    public StationConnector(TrainStation station1) {
        this.station1 = station1;
        color = ContentController.getActiveColor();
        line = new Line();
        line.startXProperty().bind(station1.xProperty());
        line.startYProperty().bind(station1.yProperty());
        setGeneralValuesIntoLine();
    }

    private void setGeneralValuesIntoLine() {
        line.setFill(color);
        line.setStrokeWidth(GeneralSettings.getCONNECTOR_WIDTH());
        line.setStroke(color);

    }

    public void setEndY(double y) {
        this.line.setEndY(y);
    }

    public void setEndX(double x) {
        this.line.setEndX(x);
    }

    @Override
    public Line getNode() {
        return line;
    }

    public void addNextStation(TrainStation station) {
        if (station1 == null) {
            station1 = station;
        } else {
            if (station2 == null) {
                station2 = station;
                line.endXProperty().bind(station2.xProperty());
                line.endYProperty().bind(station2.yProperty());
            }
        }
    }
}
