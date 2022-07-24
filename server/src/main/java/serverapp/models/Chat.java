package serverapp.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {

    private ArrayList<User> participants;
    private HashMap<User, ArrayList<Message>> usersMessages;
    private ArrayList<Message> allMessages;

    public Chat() {

    }

}
