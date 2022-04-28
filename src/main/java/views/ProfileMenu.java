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
                this.changeNicknameView(matcher.group("nickname"));
            else if ((matcher = getMatcher("profile change (--password|-p) (--current|-c) (?<currentPassword>\\S+) (--new|-n) (?<newPassword>\\S+)", command)) != null)
                this.changePasswordView(matcher);
            else if ((matcher = getMatcher("profile change (--password|-p) (--new|-n) (?<newPassword>\\S+) (--current|-c) (?<currentPassword>\\S+)", command)) != null)
                this.changePasswordView(matcher);
            else if (command.equals("exit menu"))
                break;
            command = scanner.nextLine();
        }
    }

    private void changeNicknameView(String nickname) {
        if (UserController.isNicknameUsed(nickname))
            System.out.print("user with nickname" + nickname + "already exists");
        else {
            UserController.loggedInUser.setNickName(nickname);
            System.out.print("nickname changed successfully!");
        }
    }

    private void changePasswordView(Matcher matcher) {
        if (!UserController.isPasswordValid(matcher.group("currentPassword")))
            System.out.print("current password is invalid");
        else if (matcher.group("currentPassword").equals(matcher.group("newPassword")))
            System.out.print("please enter a new password");
        else {
            UserController.loggedInUser.setPassword(matcher.group("newPassword"));
            System.out.print("password changed successfully!");
        }
    }
}