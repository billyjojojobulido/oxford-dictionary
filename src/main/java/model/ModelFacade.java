package model;

import controller.HTTPManager;
import org.json.simple.JSONObject;

public class ModelFacade implements Model{

    private Database db;
    private HTTPManager manager;

    public ModelFacade(Database db, HTTPManager manager){
        this.db = db;
        this.manager = manager;
    }


    @Override
    public boolean hasCached(String word) {
        JSONObject ret = this.db.entityExists(word);
        return null == ret;
    }

    @Override
    public JSONObject getCachedEntry(String word) {
        return this.db.entityExists(word);
    }

    @Override
    public void updateDB(String word, JSONObject info) {
        this.db.updateEntity(word, info);
    }

    @Override
    public JSONObject processData(JSONObject raw) {
        return null;
    }

    @Override
    public boolean logIn(String apiId, String apiKey) {
        return this.manager.credentialIsValid(apiId, apiKey);
    }


    @Override
    public void sendEmail(String emailTo, String emailFrom, String emailReply, String targetName, String signature, String subject, String data, String replyName) {
        this.manager.sendEmail(emailTo, emailFrom, emailReply, targetName, signature, subject, "text/plain",data, replyName);
    }

    @Override
    public JSONObject getWord(String word) {
        JSONObject ret = this.db.entityExists(word);
        if (ret != null){
            return ret;
        }
        return this.manager.getWord("entries", "en-gb", word);
    }

}
