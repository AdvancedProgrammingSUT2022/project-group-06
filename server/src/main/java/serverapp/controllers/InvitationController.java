package serverapp.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import serverapp.enums.Actions;
import serverapp.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class InvitationController {
    private static ArrayList<Invitation> allInvitations = new ArrayList<>();
    public static String CreateInvitation(String uuid, String usernames, String hexInHeight, String hexInWidth) {
        ArrayList<String> inventedUsers = new Gson().fromJson(usernames, new TypeToken<ArrayList<String>>() {
        }.getType());
        HashMap<User,Boolean> temp = new HashMap<>();
        for (int i = 0; i < inventedUsers.size(); i++) {
            temp.put(UserController.getUserByUserName(inventedUsers.get(i)), false);
        }
        Invitation invitation =
                new Invitation(UserController.getUserHashMap().get(uuid),temp,
                        UUID.randomUUID().toString(),Integer.parseInt(hexInHeight),
                        Integer.parseInt(hexInWidth));
        allInvitations.add(invitation);
        for (int i = 0; i < inventedUsers.size(); i++) {
            sendInvitation(uuid, UserController.getUserByUserName(inventedUsers.get(i)), invitation.getGameUuid());
        }
        return " ";
    }
    public static String sendInvitation(String uuid, User user, String gameUuid) {
        JSONObject json = new JSONObject();
        try {
            json.put("action", Actions.INVITETOGAME.getCharacter());
            json.put("username", UserController.getUserHashMap().get(uuid).getUsername());
            json.put("gameUuid", gameUuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetWorkController.broadCast(user, json.toString());
        return "reqest sent seuccesfully";
    }

    public static String acceptInvitation(String userUuid, String gameUuid) {
        UserController.getUserHashMap().get(userUuid);
        for (int i = 0; i < allInvitations.size(); i++) {
            Invitation invitation = allInvitations.get(i);
            if(invitation.getGameUuid().equals(gameUuid)){
                invitation.getStateOfInventedPersons().replace(
                        UserController.getUserHashMap().get(userUuid),true);
                if(invitation.isAllDone() ){
                    GameNetworkController.startGame(gameUuid, invitation);
                    for (User user: invitation.getAllUsers()) {
                        JSONObject json = new JSONObject();
                        try {
                            json.put("action", Actions.STARTGAME.getCharacter());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NetWorkController.broadCast(user,json.toString());
                    }
                    allInvitations.remove(invitation);
                }
            }
        }
        return " ";
    }
    public static String rejectInvitation(String gameUuid) {
        for (int i = 0; i < allInvitations.size(); i++) {
            Invitation invitation = allInvitations.get(i);
            if(invitation.getGameUuid().equals(gameUuid)){
                allInvitations.remove(invitation);
                sendRejectionBroadCast(gameUuid, invitation.getAllUsers());
            }
        }
        return " ";
    }
    private static void sendRejectionBroadCast(String gameUuid, ArrayList<User> invitationUsers){
        JSONObject json = new JSONObject();
        try {
            json.put("action", Actions.SENDREJECTION.getCharacter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (User u:invitationUsers){
            NetWorkController.broadCast(u,json.toString());
        }
    }
}
