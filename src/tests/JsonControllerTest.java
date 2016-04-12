package tests;

import controller.JsonController;
import org.junit.Test;

import java.beans.IntrospectionException;

import static org.junit.Assert.*;

/**
 * Created by Vilkazzz on 11/04/2016.
 */
public class JsonControllerTest {

    @Test
    public void testGetJson() throws IntrospectionException {
        String json = JsonController.getJson("abra", "babra");
    }

}