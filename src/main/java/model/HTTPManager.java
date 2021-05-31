package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPManager {

    private String apiId;
    private String apiKey;

    public void setApiId(String apiId){
        this.apiId = apiId;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public boolean authenticate(String appId, String apiKey){
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/can");
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", appId);
            connection.setRequestProperty("app_key", apiKey);
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200){
                throw new RuntimeException("Failed : HTTP Error Code : " + connection.getResponseCode());
            }
            connection.disconnect();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public String getRoots(String wordID){
        String endpoint = "lemmas";
        String languageCode = "en-gb";
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/%s/%s/%s", endpoint, languageCode, wordID);
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", this.apiId);
            connection.setRequestProperty("app_key", this.apiKey);
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200){
                return null;
            }
            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;){
                sb.append((char) c);
            }
            String response = sb.toString();
            System.out.println(response);
            JSONParser parser = new JSONParser();
            connection.disconnect();

            return Decoder.rootDecode((JSONObject) parser.parse(response));
        } catch (Exception e){
            System.out.println("Exception in NetClientGet: - " + e);
        }
        return null;
    }

    public String getWord(String wordID){
        String endpoint = "entries";
        String languageCode = "en-gb";
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/%s/%s/%s", endpoint, languageCode, wordID);
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", this.apiId);
            connection.setRequestProperty("app_key", this.apiKey);
            connection.setRequestProperty("Accept", "application/json");
            if (connection.getResponseCode() != 200){
                return getRoots(wordID);
            }
            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;){
                sb.append((char) c);
            }
            String response = sb.toString();
            System.out.println(response);
            JSONParser parser = new JSONParser();

            connection.disconnect();

            return Decoder.entryDecode((JSONObject) parser.parse(response));
        } catch (Exception e){
            System.out.println("Exception in NetClientGet: - " + e);
        }
        return null;
    }

    public JSONObject sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                                String targetName, String signature, String subject, String type, String value,
                                String replyName){
        String link = "https://api.sendgrid.com/v3/mail/send";
        value = value.replace('\n', ' ');
        value = value.replace('\t', ' ');

        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = String.format("{\"personalizations\":[{\"to\":[{\"email\":\"%s\",\"name\":\"%s\"}]," +
                            "\"subject\":\"%s\"}],\"content\": [{\"type\": \"%s\", \"value\": \"%s\"}],"+
                            "\"from\":{\"email\":\"%s\",\"name\":\"%s\"},\"reply_to\":{\"email\":\"%s\",\"name\":\"%s\"}}",
                    emailTo, targetName, subject, type, value, emailFrom, signature, emailReply, replyName);
            // type: text/plain
            connection.setRequestProperty("Content-Length", String.valueOf(jsonInputString.length()));
            connection.setDoOutput(true);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;){
                sb.append((char) c);
            }
            String response = sb.toString();
            System.out.println(response.length());
            System.out.println(response);
            if (response.length() == 0){
                return null;
            }
            JSONParser parser = new JSONParser();

            connection.disconnect();

            return (JSONObject) parser.parse(response);

        } catch (Exception e){
            System.out.println("Exception    in NetClientPost: - "+ e);
        }
        return null;
    }

}
