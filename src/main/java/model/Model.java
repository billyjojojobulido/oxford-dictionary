package model;

import org.json.simple.JSONObject;

public interface Model {

    boolean hasCached(String word);

    JSONObject getCachedEntry(String word);

    void updateDB(String word, JSONObject info);

    JSONObject processData(JSONObject raw);

    boolean logIn(String apiId, String apiKey);

    void sendEmail(String emailTo, String emailFrom, String emailReply,
                   String targetName, String signature, String subject, String data, String replyName);

    void getWord(String word);

}
