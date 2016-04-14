package model;

import controller.JsonController;

/**
 * Created by Vilkaz on 19.02.2016.
 */
public class Neighbor {
    int distance;
    int id;


    public Neighbor(int id) {
        this.id = id;
        this.distance=1;
    }

    public Neighbor(int range, int id) {
        this.distance = range;
        this.id = id;
    }


    public String toJson(){
        String json ="{";
        json += JsonController.getJson("id", this);
        json += JsonController.getJson("distance", this,false);
        return json+"}";

    }
    //region getter and setter

    public int getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setId(int id) {
        this.id = id;
    }

//region getter
}
