package serverapp.views;

import com.google.gson.Gson;
import org.json.JSONObject;
import serverapp.controllers.GameController;
import serverapp.controllers.InvitationController;
import serverapp.controllers.UnitController;
import serverapp.controllers.UserController;
import serverapp.enums.Actions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Game {
    public static String run(String command, JSONObject jsonObject) {
        if(command.equals(Actions.SENDINITATION.getCharacter())){
            return InvitationController.CreateInvitation(jsonObject.getString("UUID"),
                    jsonObject.getString("usernames"),jsonObject.getString("hexInHeight"),jsonObject.getString("hexInWidth"));
        }if(command.equals(Actions.ACCEPTINVITATION.getCharacter())){
            return InvitationController.acceptInvitation(jsonObject.getString("UUID"),jsonObject.getString("GameUUID"));
        }if(command.equals(Actions.REJECTINVITATION.getCharacter())){
            return InvitationController.rejectInvitation(jsonObject.getString("GameUUID"));
        }if(command.equals(Actions.GETHexInHeight.getCharacter())){
            return GameController.getHexInHeight();
        }if(command.equals(Actions.SELECTUNIT.getCharacter())){
            return UnitController.selectUnit(jsonObject.getInt("i"), jsonObject.getInt("j"),jsonObject.getString("type"));
        }if(command.equals(Actions.GETHexInWidth.getCharacter())){
            return GameController.getHexInWidth();
        }if(command.equals(Actions.GetTerrainNames.getCharacter())){
            return GameController.getTerrainNames();
        }if(command.equals(Actions.GetHexDetails.getCharacter())){
           // return GameController.getHexDetails();
        }if(command.equals(Actions.GETUNITINFORMATION.getCharacter())){
            return GameController.getUnitInformation();
        }if(command.equals(Actions.SELECTILE.getCharacter())){
           // return GameController.selectHex();
        }if(command.equals(Actions.GETPANEDETAILS.getCharacter())){
            return GameController.GetPaneDetails(jsonObject);
        }
        return "bad request format";
    }
}
