package controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPManager {

    public JSONObject getWord(String appId, String appKey, String endpoint, String languageCode, String wordID){
        return null;
    }

    public boolean credentialIsValid(String appId, String appKey) {
        return false;
    }

    public JSONObject sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                                       String targetName, String signature, String subject, String type, String value,
                                       String replyName){
        return null;
    }

}
