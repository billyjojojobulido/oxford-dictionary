package model;

import org.json.simple.JSONObject;

/**
 * The facade of all the database, logic, computation related modules
 * */
public class ModelFacade{

    private HTTPManager manager;
    private Database db;


    /**
     * Constructor.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     *
     * @param manager   An instance of the HTTPManager class. Must not be NULL
     * @param db        An instance of the Database class. Must not be NULL
     */
    public ModelFacade(HTTPManager manager, Database db){
        this.manager = manager;
        this.db = db;
    }


    /**
     * Determine if the given word input has been stored in cache or not.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param word The given word from user input.
     *
     * @return true if the given word has been stored in cache before, false otherwise.
     */
    public boolean hasCached(String word) {
        return null != this.db.entityExists(word);
    }


    /**
     * Retrieve the data describing the given word either from database.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * If the given word has been stored in the cache before, the data will be return, otherwise an empty string
     * will be returned.<br>
     *
     * @param word The given word from user input.
     *
     * @return the data about hte given word input in the form of String retrieved from database.
     */
    public String getCachedEntry(String word) {
        return this.db.entityExists(word);
    }

    /**
     * Update the data describing the given word into the database.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * If the given word has been stored in the cache before, the data will be return, otherwise an empty string
     * will be returned.<br>
     *
     * @param word The given word from user input.
     * @param info The text describing the word in the form of JSONObject, will need the Decoder to decode it.
     */
    public void updateDB(String word, JSONObject info) {
        this.db.updateEntity(word, info.toJSONString());
    }


    /**
     * Verify and then set the apiId and apiKey field of the instance.<br><br>
     * <b>Preconditions:</b><br>
     * The apiId and apiKey must be valid and matching<br>
     * <b>Postconditions:</b><br>
     * return false if the authentication fails, otherwise true.<br>
     *
     * @param apiId The given api id to verify.
     * @param apiKey The given api key to verify.
     *
     * @return true if logged in successfully, false otherwise.
     */
    public boolean logIn(String apiId, String apiKey) {
        if (this.manager.authenticate(apiId, apiKey)){
            this.manager.setApiId(apiId);
            this.manager.setApiKey(apiKey);
            return true;
        } else {
            return false;
        }
    }

    /**
     * send the email from the given account to a given email address.<br><br>
     * <b>Preconditions:</b><br>
     * None <br>
     * <b>Postconditions:</b><br>
     * An email will be sent from the emailFrom to emailTo.<br>
     *
     * @param apiKey the api key of the SendGrid email address.
     * @param emailTo       The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailFrom     The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailReply    The email address you want to use to receive the reply form the receiver, may not be NULL or Empty.
     * @param targetName    The name of the person who receive the email, may not be NULL or Empty.
     * @param signature     The name of the person who send the email, may not be NULL or Empty.
     * @param subject       The subject or title of the email to send.
     * @param value         The content of the email to send.
     * @param replyName     The name of the person who receive the reply from the receiver, may not be NULL or Empty.
     *
     * @return true if email sent successfully, false otherwise
     */
    public boolean sendEmail(String apiKey, String emailTo, String emailFrom, String emailReply, String targetName, String signature, String subject, String value, String replyName) {
        JSONObject ret = manager.sendEmail(apiKey, emailTo, emailFrom, emailReply, targetName, signature, subject, "text/html", value, replyName);
        return ret != null;
    }

    /**
     * Retrieve data from the API with a given word input.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * If the response from the API is not NULL, it will update the entry in the database about the word given.<br>
     *
     * @param word The given word from user input.
     *
     * @return The data about the given word input in the form of String retrieved from the api.
     */
    public String getWordFromAPI(String word) {
        String ret = this.manager.getWord(word);
        if (ret != null) {
            this.db.updateEntity(word, ret);
        }
        return ret;
    }



}
