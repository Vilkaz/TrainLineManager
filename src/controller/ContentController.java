package controller;

import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class ContentController {

    private static TrainPlan trainPlan = new TrainPlan();
    private static TrainLine activeTrainline;
    private static TrainStation lastAdedStation;

    public static ArrayList<Integer> getFreeTrainLineNumbers() {
        return ContentController.trainPlan.getFreeTrainLineNumbers();
    }


    public static int getActualLineNr() {
        return activeTrainline.getNumber();
    }


    public static Color getActiveColor() {
        return activeTrainline.getColor();
    }

    public static List<TrainLine> getTrainLines() {
        return trainPlan.getTrainLines();
    }

    public static void addTrainLine(TrainLine trainLine) {
        trainPlan.addTrainLine(trainLine);
        ContentController.activeTrainline = trainLine;

    }


    public static TrainPlan getTrainPlan() {
        return trainPlan;
    }

    public static void addStationToActualTrainLine(TrainStation station) {
        if (activeTrainline.hasStations()) {
            setLastStationAndThisStationAsNeighbors(station);
        }
        activeTrainline.addStation(station);
        System.out.println("");

    }

    public static int getIdForNextStation() {
        return activeTrainline.getStations().size();
    }

    private static void setLastStationAndThisStationAsNeighbors(TrainStation trainStation) {
        TrainStation lastStation = activeTrainline.getLastStation();
        Neighbor neighborForLastStation = new Neighbor(trainStation.getId());
        Neighbor neighborForThisStation = new Neighbor(lastStation.getId());
        lastStation.addNeighbor(neighborForLastStation);
        trainStation.addNeighbor(neighborForThisStation);
    }

    public static void addConnectorToActiveLine(LineConnector connector) {
        ContentController.activeTrainline.addConnector(connector);
    }


    public static boolean trainlineHasStations() {
        return activeTrainline.getStations().size() > 0;
    }

    public static TrainStation getLastAdedStation() {
        return activeTrainline.getLastStation();
    }

    public static void markEndstation() {
        activeTrainline.getLastStation().setEndStation(true);
        System.out.println();
    }

    public static TrainStation getTrainStationById(int id) {
        TrainStation result = new TrainStation();
        for (TrainLine line : getTrainLines()) {
            for (TrainStation station : line.getStations()) {
                if (station.getId() == id) {
                    result = station;
                }
            }
        }
        return result;
    }
}
