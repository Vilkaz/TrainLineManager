package controller;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class ContentController {

    private static TrainPlan trainPlan = new TrainPlan();
    private static TrainLine activeTrainline;
    private static TrainStation lastAdedStation;
    private static StationConnector activeConnector;

    private static boolean activeTextDrag = false;

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
        ContentController.trainPlan.addTrainLine(trainLine);
        ContentController.activeTrainline = trainLine;
    }


    public static TrainPlan getTrainPlan() {
        return trainPlan;
    }

    public static void setTrainPlan(TrainPlan trainPlan) {
        ContentController.trainPlan = trainPlan;
    }

    public static void addStationToActualTrainLine(TrainStation station) {
        if (activeTrainline.hasStations()) {
            setLastStationAndThisStationAsNeighbors(station);
        }
        activeTrainline.addStation(station);

    }

    public static boolean isActiveTrainlineEmpty(){
        return !activeTrainline.hasStations();
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

    public static void addConnectorToActiveLine(StationConnector connector) {
        ContentController.activeTrainline.addConnector(connector);
    }

    public static boolean trainlineHasStations() {
        return activeTrainline.getStations().size() > 0;
    }

    public static TrainStation getLastAdedStation() {
        return activeTrainline.getLastStation();
    }

    public static void setActualStationAsEndstationInLine() {
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

    public static StationConnector getActiveConnector() {
            return activeConnector;
    }


    public static Color getActiveLineColor(){
        return activeTrainline.getColor();
    }

    public static void setActiveConnector(StationConnector activeConnector) {
        ContentController.activeConnector = activeConnector;
    }

    public static ArrayList<TrainStation> getAllStations() {
        ArrayList<TrainStation> stations = new ArrayList<>();
        for (TrainLine line:trainPlan.getTrainLines()){
            stations.addAll(line.getStations().stream().collect(Collectors.toList()));
        }
        return stations;
    }

    public static void removeActiveConnector() {
        activeTrainline.getConnectors().remove(activeTrainline.getConnectors().size()-1);
    }

    public static void setActiveTextDrag(boolean activeTextDrag) {
        ContentController.activeTextDrag = activeTextDrag;
    }

    public static boolean isActiveTextDrag() {
        return activeTextDrag;
    }

    public static void saveTrainPlan() {
        String json = trainPlan.toJson();
        System.out.println(json);
        final File saveDirFile = new File(GeneralSettings.getSAVEDIR());
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(saveDirFile);
        File saveFile = fileChooser.showSaveDialog(null);
        try {
            PrintWriter writer = new PrintWriter(saveFile+".json");
            writer.println(json);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    /**
     * the next Trainline simply need the actual size, as index.
     * @return
     */
    public static int getIdForNextTrainLine() {
        return trainPlan.getTrainLines().size();
    }

    /**
     * the next StationConnector simply need the actual size, as index.
     * @return
     */
    public static int getIdForNextStationConnector() {
        return activeTrainline.getConnectors().size();
    }

    public static void loadTrainPlan() {

    }
}
