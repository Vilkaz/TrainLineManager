package tests;

import controller.GeneralNodeController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Vilkazzz on 19/03/2016.
 */
class GeneralNodeControllerTest {

    @Test
    void testDeactivateNode() {
        Node node = new Pane();
        node.setVisible(true);
        node.setDisable(false);
        GeneralNodeController.deactivateNode(node);
        assertTrue(node.isDisabled());
        assertFalse(node.isVisible());

    }

    @Test
    void testActivateNode() {
        Node node = new Pane();
        node.setVisible(false);
        node.setDisable(true);
        GeneralNodeController.activateNode(node);
        assertFalse(node.isDisabled());
        assertTrue(node.isVisible());
    }
}
