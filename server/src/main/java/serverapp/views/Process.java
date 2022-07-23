package serverapp.views;

import org.json.JSONException;
import org.json.JSONObject;
import serverapp.controllers.NetWorkController;
import serverapp.enums.Color;
import serverapp.enums.MenuCategory;

import java.net.Socket;

public class Process {

    public static String run(String command, Socket socket) {
        try{
            JSONObject obj = new JSONObject(command);
            String menuName = obj.getString("menu");
            String action = obj.getString("action");
            if(menuName.equals(MenuCategory.LOGIN.getCharacter())){
                return LoginMenu.run(action, obj);
            }else if(menuName.equals(MenuCategory.MAIN.getCharacter())){
                return Main.run(action, obj);
            }else if(menuName.equals(MenuCategory.GAMEMenu.getCharacter())){
                return Game.run(action, obj);
            }else if(menuName.equals(MenuCategory.PROFILE.getCharacter())){
                return Profile.run(action, obj);
            }else if(menuName.equals(MenuCategory.NETWORK.getCharacter())){
                return Network.run(action, obj, socket);
            } else if(menuName.equals(MenuCategory.CHEAT.getCharacter())){
                return Cheat.run(action, obj);
            } else return "no menu matched";
        }catch (JSONException e) {
            e.printStackTrace();
            return "sth is wrong with json";
        }

    }
}
