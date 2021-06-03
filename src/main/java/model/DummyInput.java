package model;

/**
 * The Dummy Version of the InputHTTP module
 * */
public class DummyInput extends InputHTTP {

    @Override
    public boolean authenticate(String appId, String apiKey) {
        return true;
    }

    @Override
    public String getRoots(String wordID, String apiId, String apiKey) {
        return "swims is not a header word\n" +
                "Roots:\n" +
                "swim: Verb\n" +
                "swim: Noun\n";
    }

    @Override
    public String getWord(String wordID, String apiId, String apiKey) {
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
