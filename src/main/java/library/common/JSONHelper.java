package library.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONHelper {

    private JSONHelper() {
    }

    private static Logger getLogger() {
        return LogManager.getLogger(JSONHelper.class);
    }

    public static JSONObject getJSONObject(String filepath, String... key) {
        try {
            FileReader reader = new FileReader(filepath);
            JSONTokener token = new JSONTokener(reader);
            JSONObject jsonObject = (JSONObject) (key.length > 0 ? new JSONObject(token).get(key[0]) : new JSONObject(token));
            return jsonObject;
        } catch (FileNotFoundException e) {
            getLogger().error(e);
            return null;
        }
    }

    public static JSONArray getJSONArray(String filepath, String... key) {
        try {
            FileReader reader = new FileReader(filepath);
            JSONTokener token = new JSONTokener(reader);
            JSONArray jsonArray = (JSONArray) (key.length > 0 ? new JSONObject(token).get(key[0]) : new JSONObject(token));
            return jsonArray;
        } catch (FileNotFoundException e) {
            getLogger().error(e);
            return null;
        }
    }

    public static <T> T getDataPOJO(String filepath, Class<T> clazz) throws IOException {
        Gson gson = new Gson();
        File file = new File(filepath);
        T dataobject = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            dataobject = gson.fromJson(bufferedReader, clazz);
        } catch (FileNotFoundException e) {
            getLogger().error(e);
        }
        return dataobject;
    }

    public static Map<String, String> getJSONToMap(JSONObject json) {
        Map<String, String> map = new HashMap<>();
        String[] keys = JSONObject.getNames(json);
        for (String key : keys) {
            map.put(key, json.get(key).toString());
        }
        return map;
    }

    public static List<Map<String, String>> getJSONAsListOfMaps(String path) {
        Gson gson = new GsonBuilder().create();
        try {
            return gson.fromJson(new FileReader(path), List.class);
        } catch (FileNotFoundException e) {
            return Collections.emptyList();
        }
    }

    public static Map<String, String> getJSONDataAsMap(String filepath) {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(filepath);
            return gson.fromJson(reader, Map.class);
        } catch (FileNotFoundException fileNotFoundException) {
            getLogger().error(fileNotFoundException);
        }
        return null;
    }

    public static String mapToJSONArrayString(Map<String, String> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static String mapToJSON(Map<String, Object> map, String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter jsonWriter = new FileWriter(path)) {
            gson.toJson(map, Map.class, jsonWriter);
            File jsonFile = new File(path);
            if (jsonFile.exists()) {
                return jsonFile.getAbsolutePath();
            } else {
                return null;
            }
        } catch (IOException ioException) {
            getLogger().error("unable to write file at path: {}", path);
            return null;
        } catch (JsonSyntaxException jsonSyntaxException) {
            getLogger().debug("not a valid JSON: {}. unable to ");
            return null;
        }
    }

    public static Map<String, String> getJSONObjectToMap(String filepath) {
        return getJSONToMap(getJSONObject(filepath));
    }

    public static Map<String, String> getJSONToListOfMap(String filepath) {
        return getJSONToMap(getJSONObject(filepath));
    }
}

