package model;

import org.json.simple.JSONObject;

public class HTTPManager {

    private String apiId;
    private String apiKey;
    private InputHTTP input;
    private OutputHTTP output;

    public HTTPManager(InputHTTP input, OutputHTTP output) {
        this.input = input;
        this.output = output;
    }


    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean authenticate(String apiId, String apiKey) {
        return this.input.authenticate(apiId, apiKey);
    }

    public String getRoots(String wordID) {
        return input.getRoots(wordID, this.apiId, this.apiKey);
    }

    public String getWord(String wordID) {
        return input.getWord(wordID, this.apiId, this.apiKey);
    }

    public JSONObject sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                                String targetName, String signature, String subject, String type, String value,
                                String replyName) {
        return output.sendEmail(apiKey, emailTo, emailFrom, emailReply, targetName, signature, subject, type, value, replyName);
    }

}
