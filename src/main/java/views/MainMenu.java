package views;

import java.util.Scanner;
import java.util.regex.Matcher;

import controllers.InitializeGameInfo;
import controllers.MainController;
import controllers.UserController;
import enums.Commands;

public class MainMenu extends Menu {
    MainController mainController = new MainController();
    ProfileMenu profileMenu = new ProfileMenu();

    public boolean checkCommand(String command, Scanner scanner) {
        Matcher matcher;

        if ((matcher = Commands.getMatcher(command, Commands.EXIT)) != null) {
            return true;
        } else if ((matcher = Commands.getMatcher(command, Commands.SHOWCURRENTMENU)) != null) {
            System.out.println("Main Menu");
            return false;
        } else if ((matcher = Commands.getMatcher(command, Commands.CHANGEMENU)) != null) {
            if (matcher.group("menuname").equals("Profile Menu")) {
                profileMenu.run(scanner);
                return false;
            }
            if (matcher.group("menuname").equals("Play Game")) {
                GameMenu gameMenu = new GameMenu();
                gameMenu.run(scanner);
            }

            //System.out.println("you can't move to "+matcher.group("menuname")+" from Login Menu");
            System.out.println("menu navigation is not possible");
            return false;
        } else if ((matcher = Commands.getMatcher(command, Commands.LOGOUT)) != null) {
            UserController.loggedInUser = null;
            return true;
        } else if ((matcher = Commands.getMatcher(command, Commands.STARTGAME)) != null) {
            int check = mainController.startGame(command);
            if (check == 2) {
                GameMenu gameMenu = new GameMenu();
                gameMenu.run(scanner);
                return false;
            }
            if (check == 1) {
                System.out.println("user does not exist");
                return false;
            }
            if (check == 0) {

                System.out.println("invalid command!");
                return false;
            }
        }


        System.out.println("invalid command!");
        return false;
    }
}
