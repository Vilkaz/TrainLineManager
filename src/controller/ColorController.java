package controller;

import com.google.gson.JsonElement;
import javafx.scene.paint.Color;

/**
 * Created by vkukanauskas on 01/04/2016.
 */
public class ColorController {
    public static String getColorHex(Color color) {
            return String.format( "#%02X%02X%02X",
                    (int)( color.getRed() * 255 ),
                    (int)( color.getGreen() * 255 ),
                    (int)( color.getBlue() * 255 ) );
    }

    public static Color getColorFromHex(String color) {
        int i = Integer.valueOf( color.substring( 6, 8 ), 16);
        return new Color(
                Integer.valueOf( color.substring( 2, 4 ), 16 ),
                Integer.valueOf( color.substring( 4, 6 ), 16 ),
                Integer.valueOf( color.substring( 6, 8 ), 16 ),
               0 );
    }
}
