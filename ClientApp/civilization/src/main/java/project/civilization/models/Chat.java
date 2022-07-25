package project.civilization.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Chat {

    private static Chat publicChat;
    private static ArrayList<Chat> rooms;
    private static ArrayList<Chat> privateChats;

    private ArrayList<User> participants;
    private HashMap<User, ArrayList<Message>> usersMessages;
    private ArrayList<Message> allMessages;
    private int chatID;

    public Chat() {
        participants = new ArrayList<>();
        allMessages = new ArrayList<>();
        usersMessages = new HashMap<>();
        chatID = new Random().nextInt();
    }

    public static Chat getPublicChat() {
        return publicChat;
    }

    public static void setPublicChat(Chat publicChat) {
        Chat.publicChat = publicChat;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public HashMap<User, ArrayList<Message>> getUsersMessages() {
        return usersMessages;
    }

    public ArrayList<Message> getAllMessages() {
        return allMessages;
    }

}
