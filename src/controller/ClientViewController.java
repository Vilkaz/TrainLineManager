package controller;

import javafx.scene.Node;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
public class ClientViewController {

    public static void activateClientView(Node adminTools) {
        AdminViewController.disableAdminView(adminTools);
    }
}
