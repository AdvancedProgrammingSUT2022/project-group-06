package serverapp.views;

import org.json.JSONObject;
import serverapp.controllers.NetWorkController;
import serverapp.enums.Actions;

import java.net.Socket;

public class Network {
    public static String run(String command, JSONObject jsonObject, Socket socket) {
        if(command.equals(Actions.INITIALIZEHEARINGSERVERSOCKET.getCharacter())){
            return NetWorkController.initializeNetworkSocket(jsonObject.getString("UUID"), socket);
        }
        return "bad request format";
    }
}
