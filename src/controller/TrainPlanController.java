package controller;

import com.google.gson.JsonObject;
import model.TrainPlan;

import java.io.File;

/**
 * Created by Vilkazzz on 13/04/2016.
 */
public class TrainPlanController {
    public static TrainPlan getTrainplan(File file) {
        JsonObject data = JsonController.getJsonObject(file);
        int id = data.get("id").getAsInt();
        String name = data.get("name").getAsString();
        TrainPlan trainplan = new TrainPlan(id,name, TrainLineController.getLinesFromJsonObject(data));
        return trainplan;
    }
}