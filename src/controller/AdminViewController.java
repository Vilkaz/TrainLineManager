package controller;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class AdminViewController {
    public static void loadAdminView(Node adminTools) {
        GeneralNodeController.activateNode(adminTools);
    }

    public static void disableAdminView(Node adminTools) {
        GeneralNodeController.deactivateNode(adminTools);
    }



}
