package model;

import org.json.simple.JSONObject;

public interface Model {

    boolean hasCached(String word);

    String getCachedEntry(String word);

    void updateDB(String word, JSONObject info);

    JSONObject processData(JSONObject raw);

    boolean logIn(String apiId, String apiKey);

    boolean sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                      String targetName, String signature, String subject, String value, String replyName);

    String getWordFromAPI(String word);

}
