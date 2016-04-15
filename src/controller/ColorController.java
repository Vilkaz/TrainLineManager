package controller;

import com.google.gson.JsonElement;
import javafx.scene.paint.Color;

/**
 * Created by vkukanauskas on 01/04/2016.
 */
public class ColorController {
    public static String getColorHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public static Color getColorFromHex(String color) {
        int red = Integer.valueOf(color.substring(1, 3), 16);
        int green = Integer.valueOf(color.substring(3, 5), 16);
        int blue = Integer.valueOf(color.substring(5, 7), 16);

        return Color.rgb(red,green,blue);
    }
}
