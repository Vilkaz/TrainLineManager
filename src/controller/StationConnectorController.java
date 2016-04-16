package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.StationConnector;
import model.TrainLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vilkazzz on 15/04/2016.
 */
public class StationConnectorController {
    public static void setStationConnectors(JsonObject obj, ArrayList<TrainLine> trainLines) {
        ArrayList<StationConnector> connectors = new ArrayList<>();
        JsonArray jsonConnectors = obj.getAsJsonArray("connectors");
        for (JsonElement jsonElement : jsonConnectors) {
            JsonObject o = (JsonObject) jsonElement;
            StationConnector connector = new StationConnector(
                    o.get("id").getAsInt(),
                    trainLines,
                    o.get("station1Id").getAsInt(),
                    o.get("station1LineNr").getAsInt(),
                    o.get("station2Id").getAsInt(),
                    o.get("station2LineNr").getAsInt(),
                    obj);
            connectors.add(connector);
        }

        addConnectorsToLine(connectors, obj, trainLines);

    }

    private static void addConnectorsToLine(ArrayList<StationConnector> connectors, JsonObject obj, ArrayList<TrainLine> trainLines) {
        TrainLine line = getLineFromArrayById(trainLines, obj.get("id").getAsInt());
    }

    private static TrainLine getLineFromArrayById(ArrayList<TrainLine> trainLines, int id) {
        TrainLine result = new TrainLine();
        for (TrainLine line : trainLines) {
            if (line)
        }
    }
}
