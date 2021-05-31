package model;

import org.json.simple.JSONObject;
import org.junit.Test;

public class ModelFacade implements Model{

    private HTTPManager manager;
    private Database db;

    public ModelFacade(HTTPManager manager, Database db){
        this.manager = manager;
        this.db = db;
    }


    @Override
    public boolean hasCached(String word) {
        return null != this.db.entityExists(word);
    }

    @Override
    public String getCachedEntry(String word) {
        return this.db.entityExists(word);
    }

    @Override
    public void updateDB(String word, JSONObject info) {
        this.db.updateEntity(word, info.toJSONString());
    }

    @Override
    public JSONObject processData(JSONObject raw) {
        return null;
    }

    @Override
    public boolean logIn(String apiId, String apiKey) {
        if (this.manager.authenticate(apiId, apiKey)){
            this.manager.setApiId(apiId);
            this.manager.setApiKey(apiKey);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply, String targetName, String signature, String subject, String value, String replyName) {
        JSONObject ret = manager.sendEmail(apiKey, emailTo, emailFrom, emailReply, targetName, signature, subject, "text/plain", value, replyName);
        return ret != null;
    }

    @Override
    public String getWordFromAPI(String word) {
        String ret = this.manager.getWord(word);
        if (ret != null) {
            this.db.updateEntity(word, ret);
        }
        return ret;
    }

}
