package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.Neighbor;

import java.util.ArrayList;

/**
 * Created by Vilkazzz on 15/04/2016.
 */
public class NeighborController {
    public static ArrayList<Neighbor> getNeighbors(JsonObject jObj) {
        JsonArray jsonNeighbors = jObj.get("neighbors").getAsJsonArray();
        ArrayList<Neighbor> result = new ArrayList<>();
        for (JsonElement jsonElement:jsonNeighbors){
            JsonObject data = (JsonObject) jsonElement;
            String id = data.get("id").getAsString();
            result.add(new Neighbor(id));
        }
        return result;
    }
}
