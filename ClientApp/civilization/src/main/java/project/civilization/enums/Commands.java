package project.civilization.enums;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum Commands {
    //All
    CHANGEMENU("[ \\t]*menu[ \\t]+enter[ \\t]+(?<menuname>[a-zA-Z]+ [A-Za-z]+)[ \\t]*"),
    EXIT("[ \\t]*exit[ \\t]+menu[ \\t]*"),
    SHOWCURRENTMENU("[ \\t]*menu[ \\t]+show\\-current[ \\t]*"),
    //Login Menu
    LOGIN("[ \\t]*user[ \\t]+login[ \\t]+.*"),
    CREATEUSER("[ \\t]*user[ \\t]+create[ \\t]*.*"),
    //Main Menu
    LOGOUT("[ \\t]*user[ \\t]+logout[ \\t]*"),
    STARTGAME("[ \\t]*play[ \\t]+game[ \\t]*.*");

    private String regex;

    Commands(String regex) {
        this.regex = regex;
    }


    public static Matcher getMatcher(String input, Commands command) {

        Pattern pattern = Pattern.compile(command.regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
