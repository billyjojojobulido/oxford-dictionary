package controller;

import model.Model;
import view.RequestWindow;

import javax.swing.*;

public class RequestWindowController {

    private Model backEnd;
    private RequestWindow frontEnd;

    public RequestWindowController(Model model){
        this.backEnd = model;
        this.frontEnd = new RequestWindow(this);
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

    public void reportData(String apiKey, String emailTo, String emailFrom, String emailReply, String targetName, String signature, String subject, String value, String replyName){
        boolean succeed = this.backEnd.sendEmail(apiKey, emailTo, emailFrom, emailReply, targetName, signature, subject, value, replyName);
        if (!succeed){
            JOptionPane.showMessageDialog(this.frontEnd, "Failed To Send, Please Check!");
        } else {
            JOptionPane.showMessageDialog(this.frontEnd, "Email is sent! Please Check you inbox!");
        }
    }

    public void updateView(String input){
        this.frontEnd.notify(input);
    }



}
