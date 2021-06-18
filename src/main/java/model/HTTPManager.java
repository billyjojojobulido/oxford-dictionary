package model;

import org.json.simple.JSONObject;

/**
 * The facade of all the api HTTP requests related modules
 * */
public class HTTPManager {

    private String apiId;
    private String apiKey;
    private InputHTTP input;
    private OutputHTTP output;

    /**
     * Constructor.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param input     An instance of the InputHTTP class.
     * @param output    An instance of the OutputHTTP class.
     */
    public HTTPManager(InputHTTP input, OutputHTTP output) {
        this.input = input;
        this.output = output;
    }

    /**
     * set the api id of the oxford dictionary api.<br><br>
     * <b>Preconditions:</b><br>
     * The api id must be valid<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param apiId the api id of the oxford dictionary api.
     */
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    /**
     * set the api key of the oxford dictionary api.<br><br>
     * <b>Preconditions:</b><br>
     * The api key must be valid and matches the api id<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param apiKey the api id of the oxford dictionary key.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * validate the api id and api key.<br><br>
     * <b>Preconditions:</b><br>
     * None <br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param apiId the api id of the oxford dictionary api to verify.
     * @param apiKey the api key of the oxford dictionary key to verify.
     *
     * @return true if the api id and key are valid and matching. false otherwise.
     */
    public boolean authenticate(String apiId, String apiKey) {
        return this.input.authenticate(apiId, apiKey);
    }



    /**
     * retrieve data about the root forms of a given word input.<br><br>
     * <b>Preconditions:</b><br>
     * The word input should not be a head word <br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param wordID the word given to request
     *
     * @return The root form data about the given word input in the form of String
     */
    public String getRoots(String wordID) {
        return input.getRoots(wordID, this.apiId, this.apiKey);
    }

    /**
     * retrieve data about the entries of a given word input.<br><br>
     * <b>Preconditions:</b><br>
     * The word input should be a head word <br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param wordID the word given to request
     *
     * @return The entry data about the given word input in the form of String
     */
    public String getWord(String wordID) {
        return input.getWord(wordID, this.apiId, this.apiKey);
    }

    /**
     * send the email from the given account to a given email address.<br><br>
     * <b>Preconditions:</b><br>
     * None <br>
     * <b>Postconditions:</b><br>
     * An email will be sent from the emailFrom to emailTo.<br>
     *
     * @param apiKey        The api key of the SendGrid email address.
     * @param emailTo       The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailFrom     The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailReply    The email address you want to use to receive the reply form the receiver, may not be NULL or Empty.
     * @param targetName    The name of the person who receive the email, may not be NULL or Empty.
     * @param signature     The name of the person who send the email, may not be NULL or Empty.
     * @param subject       The subject or title of the email to send.
     * @param type          The type of formatting of the text in email. In this project, it will be set to text/plain by default.
     * @param value         The cotent of the email to send.
     * @param replyName     The name of the person who receive the reply from the receiver, may not be NULL or Empty.
     *
     * @return The response from the SendGrid API in the form of JSONObject
     */
    public JSONObject sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply,
                                String targetName, String signature, String subject, String type, String value,
                                String replyName) {
        return output.sendEmail(apiKey, emailTo, emailFrom, emailReply, targetName, signature, subject, type, value, replyName);
    }

    /**
     * Determine if the entry length exceeds the threshold or not.<br><br>
     * <b>Preconditions:</b><br>
     * text is a response from the api corresponding to a user input word <br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param text The response from api of a given word
     * @param threshold The maximum length of the entry
     *
     * @return true if the entry length exceeds the threshold, false otherwise.
     */
    public boolean exceedThreshold(String text, int threshold){
        String entry = Decoder.getEntry(text);
        if (entry == null) return false;
        return entry.length() > threshold;
    }

}
