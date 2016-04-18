package controller;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class AdminViewController {
    public static void loadAdminView(Node adminTools, VBox clientViewMainContainer, VBox leftMenuMainContainer) {
        GeneralNodeController.activateNode(adminTools);
        ClientViewController.disableClientView(clientViewMainContainer);
    }

    public static void disableAdminView(Node adminTools) {
        GeneralNodeController.deactivateNode(adminTools);
    }



}
