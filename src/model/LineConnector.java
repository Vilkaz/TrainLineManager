package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class LineConnector implements HasNode{
    private TrainStation station1;
    private TrainStation station2;
    private Line line;
    private Color color;


    public LineConnector(TrainStation station1) {
        this.station1 = station1;
        color = station1.getColor();
        double y1 = station1.getNode().getLayoutY();
        double x1 = station1.getNode().getLayoutX();
        line = new Line(x1,y1,x1,y1);
        line.startXProperty().bind(station1.getNode().layoutXProperty());
        line.startYProperty().bind(station1.getNode().layoutYProperty());
        setGeneralValuesIntoLine();
    }


    public LineConnector(TrainStation station1, TrainStation station2) {
        this.station1 = station1;
        this.station2 = station2;
        color = station1.getColor();

        double y1 = station1.getNode().getLayoutY();
        double x1 = station1.getNode().getLayoutX();
        double x2 = station2.getNode().getLayoutX();
        double y2 = station2.getNode().getLayoutY();
        line = new Line(x1,y1,x2,y2);
        setGeneralValuesIntoLine();
    }

    private void setGeneralValuesIntoLine(){
        line.setFill(color);
        line.setStrokeWidth(GeneralSettings.getCONNECTOR_WIDTH());
        line.setStroke(color);

    }

    public void setEndY(double y){
        this.line.setEndY(y);
    }

    public void setEndX(double x){
        this.line.setEndX(x);
    }

    @Override
    public Line getNode() {
        return line;
    }
}
