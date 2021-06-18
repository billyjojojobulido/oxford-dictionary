package controller;

import model.ModelFacade;
import view.RequestWindow;
import javax.swing.JOptionPane;

/**
 * CONTROLLER in MVC: the controller module that carry out the HTTP requests,
 * invokes the model methods and then update the GUI
 * */
public class RequestWindowController {

    private ModelFacade backEnd;
    private RequestWindow frontEnd;
    private String emailKey;
    private String emailFrom;
    private String emailTo;
    private String emailReply;
    private String senderName;
    private String targetName;
    private String replyName;

    /**
     * Constructor.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param model An instance of the ModelFacade class. May not be NULL.
     */
    public RequestWindowController(ModelFacade model) {
        this.backEnd = model;
        this.frontEnd = new RequestWindow(this);
    }

    /**
     * Assigning the data to the fields.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * data will be assigned to the fields of the instance.<br>
     *
     * @param emailKey      The email api key, may not be NULL or Empty.
     * @param emailFrom     The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailTo       The email address you want to use to send the email, may not be NULL or Empty.
     * @param emailReply    The email address you want to use to receive the reply form the receiver, may not be NULL or Empty.
     * @param senderName    The name of the person who send the email, may not be NULL or Empty.
     * @param targetName    The name of the person who receive the email, may not be NULL or Empty.
     * @param replyName     The name of the person who receive the reply from the receiver, may not be NULL or Empty.
     *
     */
    public void logIn(String emailKey, String emailFrom, String emailTo, String emailReply, String senderName, String targetName, String replyName) {
        this.emailKey = emailKey;
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.emailReply = emailReply;
        this.senderName = senderName;
        this.targetName = targetName;
        this.replyName = replyName;
    }

    public void thresholdRequest(){
        Object[] options = new Object[18];
        for (int i = 3; i<=20; i++){
            options[i-3] = i;
        }
        try {
            int threshold = (Integer) JOptionPane.showInputDialog(null,
                    "Please select a threshold character length:\n", "Set Threshold",
                    JOptionPane.QUESTION_MESSAGE, null, options, 3);
            this.backEnd.setThreshold(threshold);
        } catch (NullPointerException e){

        }
    }

    public void run() {
        this.frontEnd.setVisible(true);
    }

    /**
     * check if the given word has been stored in the database before <br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param word The given word from user input.
     *
     * @return true if the given word has been stored in cache before, false otherwise.
     */
    public boolean checkCache(String word) {
        return this.backEnd.hasCached(word);
    }

    /**
     * Retrieve the data describing the given word either from database or api.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param word The given word from user input.
     * @param fromCache ture: retrieve data from database; false retrieve data from api
     *
     * @return data about the given word in the form of String
     */
    public String retrieveData(String word, boolean fromCache) {
        if (fromCache) {
            return this.backEnd.getCachedEntry(word);
        } else {
            return this.backEnd.getWordFromAPI(word);
        }
    }

    /**
     * send the email with the given subject and content <br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * An Message box will be displayed on the GUI indicating the success of email sending.<br>
     *
     * @param subject   The subject or title of the email.
     * @param value     The content of the email
     */
    public void reportData(String subject, String value) {
        boolean succeed = this.backEnd.sendEmail(emailKey, emailTo, emailFrom, emailReply, targetName, senderName, subject, value, replyName);
        JOptionPane.showMessageDialog(this.frontEnd, "Email is sent! Please Check you inbox!");
    }


    /**
     * display a given text on the GUI text area.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None.<br>
     *
     * @param input The input data to display on the GUI text area
     */
    public void updateView(String input) {
        this.frontEnd.notify(input);
    }

    public void blink(String text){
        if (this.backEnd.timeToBlink(text)){
            this.frontEnd.blink();
        }
    }

}
