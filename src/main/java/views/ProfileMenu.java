package views;

import controllers.UserController;
import models.Player;
import models.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu {

    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
        while (true) {
            if ((matcher = getMatcher("profile change (--nickname|-n) (?<nickname>\\S+)", command)) != null)
                System.out.println(UserController.changeNickname(matcher.group("nickname")));
            else if ((matcher = getMatcher("profile change (--password|-p) (--current|-c) (?<currentPassword>\\S+) (--new|-n) (?<newPassword>\\S+)", command)) != null)
                System.out.println(UserController.changePassword(matcher.group("currentPassword"), matcher.group("newPassword")));
            else if ((matcher = getMatcher("profile change (--password|-p) (--new|-n) (?<newPassword>\\S+) (--current|-c) (?<currentPassword>\\S+)", command)) != null)
                System.out.println(UserController.changePassword(matcher.group("currentPassword"), matcher.group("newPassword")));
            else if (command.equals("show current menu")) System.out.println("Profile Menu");
            else if (command.equals("exit menu")) break;
            else System.out.println("invalid command!");
            command = scanner.nextLine();
        }
    }
}
