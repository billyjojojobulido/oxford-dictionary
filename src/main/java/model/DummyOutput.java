package model;

import org.json.simple.JSONObject;

public class DummyOutput extends OutputHTTP{

    @Override
    public JSONObject sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                                String targetName, String signature, String subject, String type, String value,
                                String replyName){
        return null;
    }

}
