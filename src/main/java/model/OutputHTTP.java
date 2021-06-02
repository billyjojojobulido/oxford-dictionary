package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OutputHTTP {

    public JSONObject sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                                String targetName, String signature, String subject, String type, String value,
                                String replyName) {
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
                            "\"subject\":\"%s\"}],\"content\": [{\"type\": \"%s\", \"value\": \"%s\"}]," +
                            "\"from\":{\"email\":\"%s\",\"name\":\"%s\"},\"reply_to\":{\"email\":\"%s\",\"name\":\"%s\"}}",
                    emailTo, targetName, subject, type, value, emailFrom, signature, emailReply, replyName);
            // type: text/plain
            connection.setRequestProperty("Content-Length", String.valueOf(jsonInputString.length()));
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0; ) {
                sb.append((char) c);
            }
            String response = sb.toString();

            JSONParser parser = new JSONParser();

            connection.disconnect();

            return (JSONObject) parser.parse(response);

        } catch (Exception e) {
            System.out.println("Exception    in NetClientPost: - " + e);
        }
        return null;
    }
}
