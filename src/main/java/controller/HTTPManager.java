package controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPManager {

    private String id;
    private String key;
    private String emailKey;

    public HTTPManager(String id, String key, String emailKey){
        this.id = id;
        this.key = key;
        this.emailKey = emailKey;
    }


    public JSONObject getWord(String endpoint, String languageCode, String wordID){
        return null;
    }

    public boolean credentialIsValid(String appId, String appKey) {
        String link = String.format("https://od-api.oxforddictionaries.com/api/v2/entries/en-us/can");
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("app_id", appId);
            connection.setRequestProperty("app_key", appKey);
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

    public JSONObject sendEmail(String emailTo, String emailFrom, String emailReply,
                                       String targetName, String signature, String subject, String type, String value,
                                       String replyName){
        String link = "https://api.sendgrid.com/v3/mail/send";
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + this.emailKey);
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
