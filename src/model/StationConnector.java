package model;

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


    public TrainStation getStationWithID(int id) {
        return (station1.getId() == id) ? station1 : station2;
    }

    public StationConnector(TrainStation station1) {
        this.station1 = station1;
        color = station1.getColor();
        line = new Line();
        line.startXProperty().bindBidirectional(station1.getNode().layoutXProperty());
        line.startYProperty().bindBidirectional(station1.getNode().layoutYProperty());
        setGeneralValuesIntoLine();
    }


    public StationConnector(TrainStation station1, TrainStation station2) {
        this.station1 = station1;
        this.station2 = station2;
        color = station1.getColor();

        double y1 = station1.getNode().getLayoutY();
        double x1 = station1.getNode().getLayoutX();
        double x2 = station2.getNode().getLayoutX();
        double y2 = station2.getNode().getLayoutY();
        line = new Line(x1, y1, x2, y2);
        setGeneralValuesIntoLine();
    }


    public void setSecondStation(TrainStation station2) {
        this.line.endXProperty().bind(station2.getNode().layoutXProperty());
        this.line.endYProperty().bind(station2.getNode().layoutYProperty());
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
            }
        }
    }
}
