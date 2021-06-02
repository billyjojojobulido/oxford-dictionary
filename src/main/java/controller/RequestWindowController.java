package controller;

import model.Model;
import view.RequestWindow;

import javax.swing.JOptionPane;

public class RequestWindowController {

    private Model backEnd;
    private RequestWindow frontEnd;
    private String emailKey;
    private String emailFrom;
    private String emailTo;
    private String emailReply;
    private String senderName;
    private String targetName;
    private String replyName;

    public RequestWindowController(Model model){
        this.backEnd = model;
        this.frontEnd = new RequestWindow(this);
    }

    public void logIn(String emailKey, String emailFrom, String emailTo, String emailReply, String senderName, String targetName, String replyName){
        this.emailKey = emailKey;
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.emailReply= emailReply;
        this.senderName = senderName;
        this.targetName = targetName;
        this.replyName = replyName;
    }

    public void run(){
        this.frontEnd.setVisible(true);
    }

    public boolean checkCache(String word){
        return this.backEnd.hasCached(word);
    }

    public String retrieveData(String word, boolean fromCache){
        if (fromCache){
            return this.backEnd.getCachedEntry(word);
        } else {
            return this.backEnd.getWordFromAPI(word);
        }
    }

    public void reportData(String subject, String value){
        boolean succeed = this.backEnd.sendEmail(emailKey, emailTo, emailFrom, emailReply, targetName, senderName, subject, value, replyName);
        JOptionPane.showMessageDialog(this.frontEnd, "Email is sent! Please Check you inbox!");
    }

    public void updateView(String input){
        this.frontEnd.notify(input);
    }



}
