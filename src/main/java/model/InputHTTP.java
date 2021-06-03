package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Module that handles all the Oxford Dictionary API related HTTP Requests
 * */
public class InputHTTP {

    /**
     * validate the api id and api key.<br><br>
     * <b>Preconditions:</b><br>
     * None <br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param apiId the api id of the oxford dictionary api to verify.
     * @param apiKey the api key of the oxford dictionary key to verify.
     *
     * @return ture of both api id and key are valid and matching, false otherwise.
     */
    public boolean authenticate(String apiId, String apiKey) {
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/can");
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", apiId);
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

    /**
     * retrieve data about the root forms of a given word input.<br><br>
     * <b>Preconditions:</b><br>
     * The word input should not be a head word <br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param wordID the word given to request
     * @param apiId the api id of the oxford dictionary api to verify.
     * @param apiKey the api key of the oxford dictionary key to verify.
     *
     * @return The root form data about the given word input in the form of String
     */
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

    /**
     * retrieve data about the entries of a given word input.<br><br>
     * <b>Preconditions:</b><br>
     * The word input should be a head word <br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param wordID the word given to request
     * @param apiId the api id of the oxford dictionary api to verify.
     * @param apiKey the api key of the oxford dictionary key to verify.
     *
     * @return The entry data about the given word input in the form of String
     */
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
