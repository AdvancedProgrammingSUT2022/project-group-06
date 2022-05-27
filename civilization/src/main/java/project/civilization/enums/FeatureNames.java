package project.civilization.enums;

public enum FeatureNames {
    Fallout("Fallout"),
    Floodplains("Floodplains"),
    Forest("Forest"),
    Ice("Ice"),
    Jungle("Jungle"),
    Marsh("Marsh"),
    Oasis("Oasis");

    private String character;

    FeatureNames(String featureNames) {
        this.character = featureNames;
    }

    public String getCharacter() {
        return this.character;
    }
}