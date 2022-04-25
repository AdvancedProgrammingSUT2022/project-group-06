package models.maprelated;

import enums.Color;
import models.Player;

import java.util.*;

public class World {
    public static HashMap<String, ArrayList<String>> appropriateTerrain = new HashMap<String, ArrayList<String>>();
    public static HashMap<String, ArrayList<String>> appropriateFeature = new HashMap<String, ArrayList<String>>();
    public static HashMap<String, String> unitNeededResource=new HashMap<String,String>();
    public static HashMap<String, String> unitNeededTech=new HashMap<String,String>();
    public static final HashMap<String, String[]> terrainPossibleFeature = new HashMap<String, String[]>();//niki
    public static HashMap<String, Color> terrainColors = new HashMap<String, Color>();//niki

    public static ArrayList<String> terrainNames = new ArrayList<String>();
    public static ArrayList<String> resourceNames = new ArrayList<String>();
    private final Random random = new Random();

    private static final int worldWidth = 120;
    private static final int worldHeight = 65;
    private static String[][] string = new String[worldHeight][worldWidth];

    private static final int hexInWidth = 10;
    private static final int hexInHeight = 10;
    private static Hex[][] hex = new Hex[hexInHeight][hexInWidth];

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public String[][] getString() {
        return string;
    }

    public Hex[][] getHex() {
        return hex;
    }

    public World() {
        initializeHex();
        //todo: initializeRiver
        initializeResource(10);
        initializePlayerTiles();
        //initializeCivilizations(2);
        initializeString();
        //printResource();
    }

    private void initializePlayerTiles() {
        Player[] players = new Player[2];
        players[0] = new Player("A");
        players[1] = new Player("B");
        hex[0][0].setOwner(players[0]);
        hex[0][1].setOwner(players[1]);
    }

    private void printResource() {
        for (int i = 0; i < hexInHeight; i++) {
            for (int j = 0; j < hexInWidth; j++) {
                System.out.print(hex[i][j].getTerrain().getName());
                if (hex[i][j].getResource() != null) {
                    System.out.print(hex[i][j].getResource().getName());
                }
                if (hex[i][j].getFeature() != null) {
                    System.out.print(hex[i][j].getFeature().getName());
                }
            }
        }
    }


    private String randomPickAName(ArrayList<String> names) {
        return names.get(Math.abs(Math.abs(random.nextInt())) % names.size());
    }

    private String randomPickANameS(String[] names) {
        return names[Math.abs(random.nextInt()) % names.length];
    }

    private void initializeHex() {
        for (int i = 0; i < hexInHeight; i++) {
            for (int j = 0; j < hexInWidth; j++) {
                //random terrain
                String pickedTerrainName = randomPickAName(terrainNames);
                Terrain terrain = new Terrain(pickedTerrainName);
                //random feature of possible features of terrain
                Feature feature = null;
                if (terrainPossibleFeature.get(pickedTerrainName) != null) {
                    feature = new Feature(randomPickANameS(terrainPossibleFeature.get(pickedTerrainName)));
                }
                hex[i][j] = new Hex(i, j, terrain, feature);
            }
        }
    }


    private void initializeResource(int resourceNumbers) {
        while (resourceNumbers != 0) {
            //random resource
            String resourceName = randomPickAName(resourceNames);
            Resource resource = new Resource(resourceName);
            ArrayList<Hex> possibleHex = new ArrayList<Hex>();
            ArrayList<String> PossibleTerrain = appropriateTerrain.get(resourceName);
            ArrayList<String> PossibleFeature = appropriateFeature.get(resourceName);
            search:
            for (int i = 0; i < hexInHeight; i++) {
                for (int j = 0; j < hexInWidth; j++) {
                    if (isTerrainSuitable(PossibleTerrain, hex[i][j]) ||
                            isFeatureSuitable(PossibleFeature, hex[i][j])) {
                        //todo : random possibleHex
                        hex[i][j].setResource(resource);
                        break search;
                    }
                }
            }
            resourceNumbers--;
        }

    }

    private boolean isTerrainSuitable(ArrayList<String> PossibleTerrain, Hex tile) {
        if (PossibleTerrain.isEmpty()) return false;
        for (String s : PossibleTerrain) {
            if (Objects.equals(s, tile.getTerrain().getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isFeatureSuitable(ArrayList<String> PossibleFeature, Hex tile) {
        if (PossibleFeature.isEmpty() || tile.getFeature() == null) return false;
        for (String s : PossibleFeature) {
            if (Objects.equals(s, tile.getFeature().getName())) {
                return true;
            }
        }
        return false;
    }

    private static void drawHexDetails(int align, int minI, int minJ, String[][] string, Hex hex, String color) {
        //string[minI + 1 + align][minJ + 6] = color + "\033[0;33m" + "A" + Color.ANSI_RESET.getCharacter();
        if (hex.getOwner() != null)
            string[minI + 1 + align][minJ + 6] = color + "\033[0;33m" + hex.getOwner().getName() + Color.ANSI_RESET.getCharacter();
        string[minI + 3 + align][minJ + 4] = color + "S" + Color.ANSI_RESET.getCharacter();
        string[minI + 3 + align][minJ + 8] = color + "B" + Color.ANSI_RESET.getCharacter();
        string[minI + 2 + align][minJ + 4] = color + hex.getX() % 10 + Color.ANSI_RESET.getCharacter();
        string[minI + 2 + align][minJ + 8] = color + hex.getY() % 10 + Color.ANSI_RESET.getCharacter();
        if (hex.getFeature() != null) {
            if (Objects.equals(hex.getFeature().getName(), "FoodPlains")) {
                string[minI + 4 + align][minJ + 6] = color + "F" + Color.ANSI_RESET.getCharacter();
                string[minI + 4 + align][minJ + 7] = color + "P" + Color.ANSI_RESET.getCharacter();
            } else
                string[minI + 4 + align][minJ + 6] = color + hex.getFeature().getName().charAt(0) + Color.ANSI_RESET.getCharacter();
        }
    }

    public static void drawHex(Hex hex) {
        String color = terrainColors.get(hex.getTerrain().getName()).getCharacter();
        int hexHeight = 6, hexWidth = 12;
        int x = hex.getX(), y = hex.getY();
        int align = y % 2 == 1 ? 3 : 0;
        for (int i = hexHeight * x; i < hexHeight * (x + 1); i++) {
            int k = i % hexHeight < 3 ? Math.abs((i % hexHeight) - 2) : Math.abs((i % hexHeight) - 3);
            for (int j = (hexWidth - 2) * y + k; j < (hexWidth - 2) * y + hexWidth - k + 1; j++) {
                if ((j == (hexWidth - 2) * y + k && i % hexHeight < 3) || (j == (hexWidth - 2) * y + hexWidth - k && i % 6 >= 3)) {
                    string[i + align][j] = "/";
                } else if ((j == (hexWidth - 2) * y + k && i % 6 >= 3) || (j == (hexWidth - 2) * y + hexWidth - k & i % 6 < 3)) {
                    string[i + align][j] = "\\";
                } else if (i == hexHeight * (x + 1) - 1) {
                    string[i + align][j] = color + "_" + Color.ANSI_RESET.getCharacter();
                } else string[i + align][j] = color + " " + Color.ANSI_RESET.getCharacter();
            }
        }
        drawHexDetails(align, hexHeight * x, (hexWidth - 2) * y, string, hex, color);
    }

    private static void initializeString() {
        for (int i = 0; i < worldHeight; i++) {
            for (int j = 0; j < worldWidth; j++) {
                string[i][j] = " ";
            }
        }
        for (int n = 0; n < hexInHeight; n++) {
            for (int m = 0; m < hexInWidth; m++) {
                drawHex(hex[n][m]);
            }
        }
    }

    private void initializeCivilizations(int numberOfPlayer) {
        Player[] players = new Player[2];
        players[0] = new Player("A");
        players[1] = new Player("B");
        for (int i = 0; i < numberOfPlayer; i++) {
            int numberOfPlayerHex = 6;
            ArrayList<Hex> playerHex = new ArrayList<>();
            while (playerHex.size() != numberOfPlayerHex) {
                playerHex.clear();
                initializePlayerHex(numberOfPlayerHex, 0, playerHex, Math.abs(random.nextInt()) % hexInHeight, Math.abs(random.nextInt()) % hexInWidth);
            }
            System.out.println(playerHex.size());
            setPlayerHex(playerHex, players[i]);
        }
    }

    private void setPlayerHex(ArrayList<Hex> playerHex, Player player) {
        for (Hex hex : playerHex) {
            hex.setOwner(player);
        }
    }


    private int initializePlayerHex(int numberOfPlayerHex, int depth, ArrayList<Hex> playerHex, int i, int j) {
        if (depth == numberOfPlayerHex || i >= hexInHeight || j >= hexInWidth || i < 0 || j < 0 || hex[i][j].getOwner() != null) {
            return depth;
        }
        playerHex.add(hex[i][j]);
        depth++;

        List<int[]> directions = new java.util.ArrayList<>(List.of(new int[]{-1, 0},
                new int[]{0, -1}, new int[]{1, -1}, new int[]{1, 0}, new int[]{1, 1}, new int[]{0, 1}));
        while (directions.size() != 0) {
            int index = Math.abs(random.nextInt()) % directions.size();
            int[] selectedDirection = directions.get(index);
            int temp = initializePlayerHex(numberOfPlayerHex, depth, playerHex, i + selectedDirection[0], j + selectedDirection[1]);
            if (temp >= numberOfPlayerHex) {
                break;
            }
            //playerHex.remove(playerHex.size()-1);
            directions.remove(index);
        }
        return depth;
    }
}