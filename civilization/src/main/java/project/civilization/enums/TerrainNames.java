package project.civilization.enums;

public enum TerrainNames {
    Coast("Coast"),
    Desert("Desert"),
    Grasslannd("Grasslands"),
    Hills("Hills"),
    Mountain("Mountain"),
    Ocean("Ocean"),
    Plains("Plain"),
    Snow("Snow"),
    Tundra("Tundra");

    private String character;

    TerrainNames(String terrainNames) {
        this.character = terrainNames;
    }

    public String getCharacter() {
        return this.character;
    }
}