package model;

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

    //region getter and setter

    public int getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }


    //region getter
}
