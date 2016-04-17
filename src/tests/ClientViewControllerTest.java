package tests;

import controller.ClientViewController;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vkukanauskas on 31/03/2016.
 */
public class ClientViewControllerTest {

    @Test
    void testActivateClientView() {
        HBox adminTools = new HBox();
        adminTools.setVisible(false);
        adminTools.setDisable(true);
        ClientViewController.activateClientView(adminTools, new VBox(),  new VBox());
        assertFalse(adminTools.isVisible());
        assertTrue(adminTools.isDisabled());
    }

}