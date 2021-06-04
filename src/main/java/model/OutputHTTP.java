package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Module that handles all the SendGrid API related HTTP Requests
 * */
public class OutputHTTP {

    /**
     * send the email from the given account to a given email address.<br><br>
     * <b>Preconditions:</b><br>
     * None <br>
     * <b>Postconditions:</b><br>
     * An email will be sent from the emailFrom to emailTo.<br>
     *
     * @param apiKey        The api key of the SendGrid email address.
     * @param emailTo       The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailFrom     The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailReply    The email address you want to use to receive the reply form the receiver, may not be NULL or Empty.
     * @param targetName    The name of the person who receive the email, may not be NULL or Empty.
     * @param signature     The name of the person who send the email, may not be NULL or Empty.
     * @param subject       The subject or title of the email to send.
     * @param type          The type of formatting of the text in email. In this project, it will be set to text/plain by default.
     * @param value         The cotent of the email to send.
     * @param replyName     The name of the person who receive the reply from the receiver, may not be NULL or Empty.
     *
     * @return The response from the SendGrid API in the form of JSONObject
     */
    public JSONObject sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                                String targetName, String signature, String subject, String type, String value,
                                String replyName) {
        String link = "https://api.sendgrid.com/v3/mail/send";
        value = "<p>" + value;
        value = value.replace("\n", "</p><p>");
        value = value.replace("\t", "  ");
        value += "</p>";

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
