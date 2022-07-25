package project.civilization.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private static SimpleDateFormat formatter = new SimpleDateFormat("H:mm");

    private String text;
    private String dateString;
    private int state;//1 = sent, 2 = seen
    private String senderUUID;
    private String senderUsername;

    public Message(String text, String uuid) {
        this.text = text;
        this.senderUUID = uuid;
        Date date = new Date();
        this.dateString = formatter.format(date);
        this.state = 1;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSenderUUID() {
        return senderUUID;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
}
