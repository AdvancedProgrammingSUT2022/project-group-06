package serverapp.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String text;
    private static SimpleDateFormat formatter = new SimpleDateFormat("H:mm");
    private String dateString;
    private int state;//1 = sent, 2 = seen
    private String senderUUID;

    public Message(String text, String uuid) {
        this.text = text;
        this.senderUUID = uuid;
        Date date = new Date();
        this.dateString = formatter.format(date);
        this.state = 1;
    }

    public String getSenderUUID() {
        return senderUUID;
    }

    public void setSenderUUID(String senderUUID) {
        this.senderUUID = senderUUID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
