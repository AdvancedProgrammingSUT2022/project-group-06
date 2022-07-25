package serverapp.models;

public class Request {
    private String senderUUID;
    private String receiverUUID;
    private String senderName;
    private String receiverName;

    public Request( String senderName, String receiverName) {
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getSenderUUID() {
        return senderUUID;
    }

    public String getReceiverUUID() {
        return receiverUUID;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

}
