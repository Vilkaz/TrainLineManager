package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.TrainLine;
import model.TrainPlan;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Vilkazzz on 13/04/2016.
 */
public class TrainPlanController {
    public static TrainPlan getTrainplan(File file) {
        JsonObject data = JsonController.getJsonObject(file);
        int id = Integer.valueOf(data.get("id").toString());
        String name = data.get("name").toString();
        TrainPlan trainplan = new TrainPlan(id,name, TrainLineController.getLinesFromJsonObject(data));
        return trainplan;
    }
}