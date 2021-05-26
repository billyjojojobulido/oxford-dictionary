package model;

import org.json.simple.JSONObject;

public class DummyModelFacade implements Model{
    @Override
    public boolean hasCached(String word) {
        return false;
    }

    @Override
    public JSONObject getCachedEntry(String word) {
        return null;
    }

    @Override
    public void updateDB(String word, JSONObject info) {

    }

    @Override
    public JSONObject processData(JSONObject raw) {
        return null;
    }

    @Override
    public boolean logIn(String apiId, String apiKey) {
        return false;
    }

    @Override
    public void sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply, String targetName, String signature, String subject, String replyName) {

    }

    @Override
    public void getWord(String word) {

    }
}
