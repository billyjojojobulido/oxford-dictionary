package model;

import java.util.Map;
import java.util.HashMap;
import org.json.simple.JSONObject;

public class DummyModelFacade implements Model{

    private Map<String, String> cache;

    public DummyModelFacade(){
        cache = new HashMap<>();
    }

    @Override
    public boolean hasCached(String word) {
        return cache.containsKey(word);
    }

    @Override
    public String getCachedEntry(String word) {
        return cache.get(word);
    }

    @Override
    public void updateDB(String word, JSONObject info) {
        this.cache.put(word, info.toJSONString());
    }

    @Override
    public JSONObject processData(JSONObject raw) {
        return null;
    }

    @Override
    public boolean logIn(String apiId, String apiKey) {
        return true;
    }

    @Override
    public boolean sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply, String targetName,
                             String signature, String subject, String value, String replyName) {
        return true;
    }

    @Override
    public String getWordFromAPI(String word) {
        String ret = "Word: software\n" +
                "Pronunciations: \n" +
                "\tˈsɒf(t)wɛː\n" +
                "Origin: \n" +
                "\tNone\n" +
                "definition: \tthe programs and other operating information used by a computer\n" +
                "examples: \n" +
                "\tthe software industry";
        this.cache.put(word, ret);
        return  ret;
    }
}
