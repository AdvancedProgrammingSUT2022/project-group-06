package serverapp.controllers;

import org.json.JSONObject;
import serverapp.models.User;

import javax.swing.tree.DefaultTreeCellEditor;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ContentHandler;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NetWorkController {
    private static HashMap<String,Socket> loggedInClientsSocket = new HashMap<>();
    public static HashMap<String, Socket> getLoggedInClientsSocket() {
        return loggedInClientsSocket;
    }

    public static String initializeNetworkSocket (String uuid, Socket socket){
        if(socket == null) System.out.println("socket is null");
        loggedInClientsSocket.put(uuid, socket);
        return " ";
    }
    public static void broadCast(User temp, String message){
        for(HashMap.Entry<String,User> m : UserController.getUserHashMap().entrySet()){
            if(m.getValue() == temp){
                String enemyUUID = m.getKey();
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(
                            loggedInClientsSocket.get(enemyUUID).getOutputStream());
                    dataOutputStream.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
