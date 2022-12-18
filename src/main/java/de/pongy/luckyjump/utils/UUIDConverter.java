package de.pongy.luckyjump.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class UUIDConverter {

    private static UUIDConverter instance;

    public static UUIDConverter getInstance() {
        if (instance == null) {
            instance = new UUIDConverter();
        }
        return instance;
    }

    private UUIDConverter() {
        // private constructor used to secure singleton
    }

    public String getUsernameByUUID(String uuid) {
        UUIDObject object = getUUIDObjectByUUID(uuid);
        return object == null ? null : object.name;
    }

    public UUID getUUIDByUsername(String username) {
        UUIDObject object = getUUIDObjectByUsername(username);
        return object == null ? null : getUUIDFromString(object.id);
    }

    private UUIDObject getUUIDObjectByUsername(String username) {
        BufferedReader reader = getContentFromUrl("https://api.mojang.com/users/profiles/minecraft/" + username);
        Gson gson = new Gson();
        try {
            return gson.fromJson(reader, UUIDObject.class);
        } catch (Exception e) {
            return null;
        }
    }
    private UUIDObject getUUIDObjectByUUID(String uuid) {
        BufferedReader reader = getContentFromUrl("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
        Gson gson = new Gson();
        try {
            return gson.fromJson(reader, UUIDObject.class);
        } catch (Exception e) {
            return null;
        }
    }

    private UUID getUUIDFromString(String id) {
        String result = "";
        for (int i = 0; i < id.length(); i++) {
            char[] c = id.toCharArray();
            result = result + c[i];
            if (i == 8 - 1 || i == 12 - 1 || i == 16 - 1 || i == 20 - 1)
                result = result + "-";
        }
        return UUID.fromString(result);
    }

    private BufferedReader getContentFromUrl(String url) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            return new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (Exception e) {
            return null;
        }
    }

    private class UUIDObject {
        public String name;
        public String id;
    }

}
