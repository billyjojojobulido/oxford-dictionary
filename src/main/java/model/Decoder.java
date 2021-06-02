package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Decoder {


    public static String rootDecode(JSONObject raw) {
        StringBuilder ret = new StringBuilder();

        JSONArray results = (JSONArray) raw.get("results");
        if (results.size() < 1) {
            return "No Entry and No Root Found\n";
        }
        JSONObject result = (JSONObject) results.get(0);
        ret.append((String) result.get("id"));
        ret.append(" is not a header word\n");
        ret.append("Roots:\n");
        JSONArray entries = (JSONArray) result.get("lexicalEntries");
        if (entries.size() < 1) {
            return "No Entry and No Root Found\n";
        }
        for (int i = 0; i < entries.size(); i++) {
            JSONObject entry = (JSONObject) entries.get(i);
            String root = (String) ((JSONObject) ((JSONArray) entry.get("inflectionOf")).get(0)).get("text");
            String category = (String) ((JSONObject) entry.get("lexicalCategory")).get("text");
            ret.append(root);
            ret.append(": ");
            ret.append(category);
            ret.append("\n");
        }
        return ret.toString();
    }

    public static String entryDecode(JSONObject raw) {
        StringBuilder ret = new StringBuilder("Word: ");
        ret.append((String) raw.get("id"));
        ret.append("\n");
        JSONArray results = (JSONArray) raw.get("results");
        if (results == null || results.size() < 1) {
            return "This is a header word, but None Entry Found";
        }
        JSONObject result = (JSONObject) results.get(0);
        JSONArray lexicalEntries = (JSONArray) result.get("lexicalEntries");
        if (lexicalEntries == null || lexicalEntries.size() < 1) {
            return "This is a header word, but None Entry Found";
        }
        JSONObject lexicalEntry = (JSONObject) lexicalEntries.get(0);
        if (lexicalEntry == null) {
            return "This is a header word, but None Entry Found";
        }
        JSONArray entries = (JSONArray) lexicalEntry.get("entries");
        if (entries == null || entries.size() < 1) {
            return "This is a header word, but None Entry Found";
        }
        JSONObject entry = (JSONObject) entries.get(0);
        if (entry == null) {
            return "This is a header word, but None Entry Found";
        }
        JSONArray pronunciations = (JSONArray) entry.get("pronunciations");
        ret.append("Pronunciations: \n");
        if (pronunciations == null || pronunciations.size() < 1) {
            ret.append("\tNone\n");
        } else {
            for (int i = 0; i < pronunciations.size(); i++) {
                ret.append("\t");
                ret.append((String) ((JSONObject) pronunciations.get(i)).get("phoneticSpelling"));
                ret.append("\n");
            }
        }

        JSONArray origins = (JSONArray) entry.get("etymologies");
        ret.append("Origin: \n");
        if (origins == null || origins.size() < 1) {
            ret.append("\tNone\n");
        } else {
            for (int i = 0; i < origins.size(); i++) {
                ret.append("\t");
                ret.append((String) origins.get(i));
                ret.append("\n");
            }
        }

        JSONArray senses = (JSONArray) entry.get("senses");
        if (senses == null || senses.size() < 1) {
            ret.append("Definitions:\n\tNone\n");
            ret.append("Examples: \n\tNone\n");
        } else {
            for (int i = 0; i < senses.size(); i++) {
                JSONObject sense = (JSONObject) senses.get(i);
                if (sense == null) {
                    continue;
                }
                JSONArray definitions = (JSONArray) sense.get("definitions");
                ret.append("definition: ");
                if (definitions == null || definitions.size() < 1) {
                    ret.append("\tNone\n");
                } else {
                    String definition = (String) definitions.get(0);
                    ret.append("\t");
                    ret.append(definition);
                    ret.append("\n");
                }
                JSONArray examples = (JSONArray) sense.get("examples");
                ret.append("examples: \n");
                if (examples == null || examples.size() < 1) {
                    ret.append("\tNone\n");
                } else {
                    for (int j = 0; j < examples.size(); j++) {
                        ret.append("\t");
                        JSONObject example = (JSONObject) examples.get(j);
                        if (example != null) {
                            ret.append((String) example.get("text"));
                            ret.append("\n");
                        }
                    }
                }
            }
        }


        return ret.toString();
    }


}
