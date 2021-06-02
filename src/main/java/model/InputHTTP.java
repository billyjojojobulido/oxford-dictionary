package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InputHTTP {

    public boolean authenticate(String appId, String apiKey) {
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/can");
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", appId);
            connection.setRequestProperty("app_key", apiKey);
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error Code : " + connection.getResponseCode());
            }
            connection.disconnect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getRoots(String wordID, String apiId, String apiKey) {
        String endpoint = "lemmas";
        String languageCode = "en-gb";
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/%s/%s/%s", endpoint, languageCode, wordID);
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", apiId);
            connection.setRequestProperty("app_key", apiKey);
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200) {
                return null;
            }
            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; ) {
                sb.append((char) c);
            }
            String response = sb.toString();
            System.out.println(response);
            JSONParser parser = new JSONParser();
            connection.disconnect();

            return Decoder.rootDecode((JSONObject) parser.parse(response));
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet: - " + e);
        }
        return null;
    }

    public String getWord(String wordID, String apiId, String apiKey) {
        String endpoint = "entries";
        String languageCode = "en-gb";
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/%s/%s/%s", endpoint, languageCode, wordID);
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", apiId);
            connection.setRequestProperty("app_key", apiKey);
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200) {
                return getRoots(wordID, apiId, apiKey);
            }
            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; ) {
                sb.append((char) c);
            }
            String response = sb.toString();
            System.out.println(response);
            JSONParser parser = new JSONParser();

            connection.disconnect();

            return Decoder.entryDecode((JSONObject) parser.parse(response));
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet: - " + e);
        }
        return null;
    }

}
