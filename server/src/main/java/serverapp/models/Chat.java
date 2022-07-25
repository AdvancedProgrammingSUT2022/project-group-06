package serverapp.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Chat {

    private static Chat publicChat;
    private static ArrayList<Chat> rooms = new ArrayList<>();
    private static ArrayList<Chat> privateChats = new ArrayList<>();

    private ArrayList<String> participants;
    private HashMap<String, ArrayList<Message>> usersMessages;
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

    public static ArrayList<Chat> getPrivateChats() {
        return privateChats;
    }

    public static void setPrivateChats(ArrayList<Chat> privateChats) {
        Chat.privateChats = privateChats;
    }

    public static ArrayList<Chat> getRooms() {
        return rooms;
    }

    public static void setRooms(ArrayList<Chat> rooms) {
        Chat.rooms = rooms;
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

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }
}
