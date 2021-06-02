package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DummyInput extends InputHTTP{

    @Override
    public boolean authenticate(String appId, String apiKey){
        return true;
    }

    public String getRoots(String wordID, String apiId, String apiKey){
        return "swims is not a header word\n" +
                "Roots:\n" +
                "swim: Verb\n" +
                "swim: Noun\n";
    }

    public String getWord(String wordID, String apiId, String apiKey){
        return "Word: software\n" +
                "Pronunciations: \n" +
                "\tˈsɒf(t)wɛː\n" +
                "Origin: \n" +
                "\tNone\n" +
                "definition: \tthe programs and other operating information used by a computer\n" +
                "examples: \n" +
                "\tthe software industry\n" +
                "Word: software\n" +
                "Pronunciations: \n" +
                "\tˈsɒf(t)wɛː\n" +
                "Origin: \n" +
                "\tNone\n" +
                "definition: \tthe programs and other operating information used by a computer\n" +
                "examples: \n" +
                "\tthe software industry\n" +
                "Word: software\n" +
                "Pronunciations: \n" +
                "\tˈsɒf(t)wɛː\n" +
                "Origin: \n" +
                "\tNone\n" +
                "definition: \tthe programs and other operating information used by a computer\n" +
                "examples: \n" +
                "\tthe software industry\n";
    }


}
