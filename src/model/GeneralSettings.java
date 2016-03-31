package model;

/**
 * Created by Vilkazzz on 20/03/2016.
 */
public class GeneralSettings {
    final static int MAX_NUMBER_OF_LINES = 20;
    final static int LINES_STARTING_NUMBER = 0;

    final static double ICON_RADIUS = 10;

    final static double CONNECTOR_WIDTH = 5;

    final private static int MIN_ZONE = 1;
    final private static int MAX_ZONE = 2;

    //region getter and setter


    public static int getMinZone() {
        return MIN_ZONE;
    }

    public static int getMaxZone() {
        return MAX_ZONE;
    }

    public static int getLinesStartingNumber() {
        return LINES_STARTING_NUMBER;
    }

    public static int getMaxNumberOfLines() {
        return MAX_NUMBER_OF_LINES;
    }

    public static double getIconRadius() {
        return ICON_RADIUS;
    }

    public static double getCONNECTOR_WIDTH() {
        return CONNECTOR_WIDTH;
    }


    //endregion getter and setter
}
