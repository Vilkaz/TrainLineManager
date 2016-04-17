package controller;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class ClientViewController {

    public static void activateClientView(Node leftMenu, VBox clientViewMainContainer, VBox leftMenuMainContainer) {
        AdminViewController.disableAdminView(leftMenu);
        enableClientView(clientViewMainContainer);
    }

    private static void enableClientView(VBox clientViewMainContainer) {
        clientViewMainContainer.setDisable(false);
        clientViewMainContainer.setVisible(true);
    }
    public static void disableClientView(VBox clientViewMainContainer) {
        clientViewMainContainer.setDisable(true);
        clientViewMainContainer.setVisible(false);
    }
}
