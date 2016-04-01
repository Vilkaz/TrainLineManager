package controller;

import javafx.scene.paint.Color;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by vkukanauskas on 01/04/2016.
 */
public class JsonController {

    public static String getJson(String name, String value) throws IntrospectionException {
        return putJsonQuotes(name)+value;
    }

    /**
     * this gives an JSon String. It needs to have normal getter and setter,
     * result is like "propertyName":value
     * value will be taken from the propperty
     *
     * @param propertyName
     * @param dto
     * @return
     * @throws IntrospectionException
     */
    public static String getJson(String propertyName, Object dto) throws IntrospectionException {
        PropertyDescriptor xProp = new PropertyDescriptor(propertyName, dto.getClass());
        return getJsonFromPropertyDescriptor(xProp, dto);
    }




    public static String getJsonFromPropertyDescriptor(PropertyDescriptor propertyDescriptor, Object dto) {
        String result = putJsonQuotes(propertyDescriptor.getName());
        Method method = propertyDescriptor.getReadMethod();
        try {
            result += method.invoke(dto, new Object[]{}).toString();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String putJsonQuotes(String name) {
        return "\"" + name + "\":";
    }



}
