package views;


import java.util.Scanner;
import java.util.regex.Matcher;

import controllers.LoginController;
import controllers.UserController;
import enums.Commands;

public class LoginMenu extends Menu
{ 
    LoginController loginController= new LoginController();
    MainMenu mainMenu= new MainMenu();

    public boolean checkCommand(String command,Scanner scanner)
    {
        Matcher matcher;
        if((matcher=Commands.getMatcher(command, Commands.LOGIN))!=null) {
            System.out.println(loginController.login(command));
            return false;
        }else if((matcher=Commands.getMatcher(command, Commands.SHOWCURRENTMENU))!=null) {
            System.out.println("Login Menu");
            return false;
        }else if((matcher=Commands.getMatcher(command, Commands.CREATEUSER))!=null) {
            System.out.println(loginController.createUser(command));
            return false;
        }else if((matcher=Commands.getMatcher(command, Commands.EXIT))!=null) {
            UserController.saveUsers();
            return true;
        }else if((matcher=Commands.getMatcher(command, Commands.LOGOUT))!=null) {
            System.out.println(UserController.logout());
            return false;
        }else if((matcher=Commands.getMatcher(command, Commands.CHANGEMENU))!=null) {
            if(UserController.loggedInUser==null) {
                System.out.println("please login first");
                return false;
            }
            if(loginController.changeMenu(matcher)) {
                mainMenu.run(scanner);
                return false;
            }
            //System.out.println("you can't move to "+matcher.group("menuname")+" from Login Menu");
            System.out.println("menu navigation is not possible");
            return false;
        }
        System.out.println("invalid command!");
        return false;
    }
}