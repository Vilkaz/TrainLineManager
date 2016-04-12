package controller;

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
}
