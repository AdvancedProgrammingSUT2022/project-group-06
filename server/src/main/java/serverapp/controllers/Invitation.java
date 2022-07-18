package serverapp.controllers;
import serverapp.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Invitation {
    private User inviter;
    private HashMap<User, Boolean> stateOfInventedPersons = new HashMap<>();

    public User getInviter() {
        return inviter;
    }

    public HashMap<User, Boolean> getStateOfInventedPersons() {
        return stateOfInventedPersons;
    }

    public String getGameUuid() {
        return gameUuid;
    }

    private String gameUuid;
    public Invitation(User inviter, HashMap<User, Boolean> stateOfInventedPersons, String gameUuid ){
        this.stateOfInventedPersons = stateOfInventedPersons;
        this.inviter = inviter;
        this.gameUuid = gameUuid;

    }
    public boolean isAllDone(){
        for(HashMap.Entry<User, Boolean> entry:stateOfInventedPersons.entrySet()){
            if(entry.getValue().equals(false)) return false;
        }
        return true;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> all = new ArrayList<>();
        all.add(inviter);
        for(HashMap.Entry<User, Boolean> entry:stateOfInventedPersons.entrySet()){
            all.add(entry.getKey());
        }
        return all;
    }
}
