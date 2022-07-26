package serverapp.models;

public class Request {
    private String senderName;
    private String receiverName;

    public Request( String senderName, String receiverName) {
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

}
