package model;

import com.google.gson.JsonObject;
import controller.ColorController;
import controller.ContentController;
import controller.JsonController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Created by Vilkazzz on 13/03/2016.
 */
public class StationConnector implements HasNode {
    private int id;
    private TrainStation station1 = null;
    private TrainStation station2 = null;
    private Line line;
    private Color color;


    public TrainStation getStationWithID(String id) {
        return (station1.getId().equals(id)) ? station1 : station2;
    }

    public StationConnector(TrainStation station1) {
        this.id = ContentController.getIdForNextStationConnector();
        this.station1 = station1;
        color = ContentController.getActiveColor();
        line = new Line();
        line.startXProperty().bind(station1.xProperty());
        line.startYProperty().bind(station1.yProperty());
        setGeneralValuesIntoLine();
    }

    public StationConnector(int id, ArrayList<TrainLine> trainLines, String station1ID, int station1LineNr, String station2ID, int station2LineNr, JsonObject jsonObject) {
        this.id = id;
        this.station1 = getStation(trainLines, station1ID, station1LineNr);
        this.station2 = getStation(trainLines, station2ID, station2LineNr);
        color = getColor(jsonObject);
        line = new Line();
        line.startXProperty().bind(station1.xProperty());
        line.startYProperty().bind(station1.yProperty());
        line.endXProperty().bind(station2.xProperty());
        line.endYProperty().bind(station2.yProperty());
        setGeneralValuesIntoLine();
    }

    private TrainStation getStation(ArrayList<TrainLine> trainLines, String stationID, int stationLineNr) {
        TrainLine line = getLine(trainLines, stationLineNr);
        return  line.getStationById(stationID);
    }

    private TrainLine getLine(ArrayList<TrainLine> trainLines, int stationLineNr) {
        TrainLine result = new TrainLine();
        for (TrainLine line : trainLines) {
            if (line.getNumber()==stationLineNr){
                result=line;
            }
        }
        return result;
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


    public String toSaveJson() {
        String json = "{";
        json += JsonController.getJson("id", this);
        json += JsonController.getJson("station1Id", station1.getId());
        json += JsonController.getJson("station1LineNr", station1.getLineNr());
        json += JsonController.getJson("station2Id", station2.getId());
        json += JsonController.getJson("station2LineNr", station2.getLineNr(),false);
        return json + "}";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor(JsonObject jsonObject) {
        return ColorController.getColorFromHex(jsonObject.get("color").getAsString());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TrainStation getStation2() {
        return station2;
    }
}
