package enums;

public enum Color {
    BLACK("\033[0;30m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    ANSI_BLACK_BACKGROUND("\u001B[40m"),
    ANSI_RED_BACKGROUND("\u001B[41m"),
    ANSI_GREEN_BACKGROUND("\u001b[48;5;22m"),
    ANSI_YELLOW_BACKGROUND("\u001b[48;5;220m"),
    ANSI_RESET("\u001B[0m"),
    ANSI_BRAWN_BACKGROUND("\u001b[48;5;94m"),
    ANSI_BULE_BACKGROUND("\u001b[48;5;20m"),
    ANSI_Bright_Green_BACKGROUND("\u001b[42;1m"),
    ANSI_WHITE_BACKGROUND("\u001b[48;5;15m"),
    ANSI_Bright_BLUE_BACKGROUND("\u001b[48;5;51m");
    private final String character;

    Color(String color) {
        this.character = color;
    }

    public String getCharacter() {
        return character;
    }
}