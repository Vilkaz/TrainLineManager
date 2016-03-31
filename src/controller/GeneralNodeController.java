package controller;

import javafx.scene.Node;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class GeneralNodeController {

    public static void deactivateNode(Node node){
        node.setDisable(true);
        node.setVisible(false);
    }

    public static void activateNode(Node node){
        node.setDisable(false);
        node.setVisible(true);
    }
}
