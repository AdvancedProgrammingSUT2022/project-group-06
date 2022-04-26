package views;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {

    public void run(Scanner scanner) {
        String command = new String("");
        while (true) {
            command = scanner.nextLine();

            if (checkCommand(command, scanner)) {
                break;
            }


        }
    }

    protected static Matcher getMatcher(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find())
            return matcher;
        return null;
    }

    protected static boolean isCommandFound(Matcher matcher) {
        return matcher.find();
    }

    //TODO: age movafegh budan ke yeki she
    // movafegh nabudan:)
    protected static boolean findMatcher(String regex, String input) {
        return Pattern.compile(regex).matcher(input).find();
    }

    public boolean checkCommand(String command, Scanner scannner) {
        return false;
    }

}
