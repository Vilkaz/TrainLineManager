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
    public static List<StationConnector> getStationConnectors(JsonObject obj, TrainLine line) {
        ArrayList<StationConnector> connectors = new ArrayList<>();
        JsonArray jsonConnectors = obj.getAsJsonArray("connectors");
        for (JsonElement jsonElement:jsonConnectors){
            JsonObject o = (JsonObject)  jsonElement;
            System.out.println();
            StationConnector connector = new StationConnector(
                    o.get("id").getAsInt(),
                    line,
                    line.getStationById(o.get("station1Id").getAsInt()),
                    line.getStationById(o.get("station2Id").getAsInt()));
            connectors.add(connector);
        }
        return  connectors;
    }
}
