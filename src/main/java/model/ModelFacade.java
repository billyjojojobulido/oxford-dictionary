package model;

import org.json.simple.JSONObject;
import org.junit.Test;

public class ModelFacade{

    private HTTPManager manager;
    private Database db;

    public ModelFacade(HTTPManager manager, Database db){
        this.manager = manager;
        this.db = db;
    }


    public boolean hasCached(String word) {
        return null != this.db.entityExists(word);
    }

    public String getCachedEntry(String word) {
        return this.db.entityExists(word);
    }

    public void updateDB(String word, JSONObject info) {
        this.db.updateEntity(word, info.toJSONString());
    }

    public boolean logIn(String apiId, String apiKey) {
        if (this.manager.authenticate(apiId, apiKey)){
            this.manager.setApiId(apiId);
            this.manager.setApiKey(apiKey);
            return true;
        } else {
            return false;
        }
    }

    public boolean sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply, String targetName, String signature, String subject, String value, String replyName) {
        JSONObject ret = manager.sendEmail(apiKey, emailTo, emailFrom, emailReply, targetName, signature, subject, "text/plain", value, replyName);
        return ret != null;
    }

    public String getWordFromAPI(String word) {
        String ret = this.manager.getWord(word);
        if (ret != null) {
            this.db.updateEntity(word, ret);
        }
        return ret;
    }

}
