package serverapp.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {

    private static Chat publicChat;
    private static ArrayList<Chat> rooms;
    private static ArrayList<Chat> privateChats;

    private ArrayList<String> participants;
    private HashMap<String, ArrayList<Message>> usersMessages;
    private ArrayList<Message> allMessages;

    public Chat() {
        participants = new ArrayList<>();
        allMessages = new ArrayList<>();
        usersMessages = new HashMap<>();
    }

    public static Chat getPublicChat() {
        return publicChat;
    }

    public static void setPublicChat(Chat publicChat) {
        Chat.publicChat = publicChat;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public HashMap<String, ArrayList<Message>> getUsersMessages() {
        return usersMessages;
    }

    public ArrayList<Message> getAllMessages() {
        return allMessages;
    }
}
