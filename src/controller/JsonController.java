package controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by vkukanauskas on 01/04/2016.
 */
public class JsonController {

    public static String getJson(String name, String value)  {
            return getJson(name, value, true);
    }

    public static String getJson(String name, String value, boolean comma)  {
        return putJsonQuotes(name) + putQuotesOn(value) + ((comma) ? "," : "");
    }


    public static String getJson(String name, Number value)  {
            return getJson(name, value, true);
    }

    public static String getJson(String name, Number value, boolean comma)  {
        return putJsonQuotes(name) + value + ((comma) ? "," : "");
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
    public static String getJson(String propertyName, Object dto) {
        return getJson(propertyName, dto, true);
    }

    public static String getJson(String propertyName, Object dto, boolean comma) {
        PropertyDescriptor xProp = null;
        try {
            xProp = new PropertyDescriptor(propertyName, dto.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        String json = getJsonFromPropertyDescriptor(xProp, dto);
        return (comma) ? json += "," : json;
    }


    public static String getJsonFromPropertyDescriptor(PropertyDescriptor propertyDescriptor, Object dto) {
        String result = putJsonQuotes(propertyDescriptor.getName());
        Method method = propertyDescriptor.getReadMethod();
        try {
            String value = method.invoke(dto, new Object[]{}).toString();
            /**
             * ein Versuch alles was String ist, in klammer zu setzen, alle "numbers" sollten somit ohne klammer auskommen
             */
            result += (method.getReturnType().equals(String.class)) ? putQuotesOn(value) : value;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }




    public static String putJsonQuotes(String name) {
        return "\"" + name + "\":";
    }

    public static String putQuotesOn(String name) {
        return "\"" + name + "\"";
    }

    public static String getStringFromFile(String path)
    {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded,StandardCharsets.UTF_8);
    }

    public static JsonObject getJsonObject(File file) {
        String string = (getStringFromFile(file.getPath()));
        JsonObject data = new JsonParser().parse(string).getAsJsonObject();
        return data;
    }
}
