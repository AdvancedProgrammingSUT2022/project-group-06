package controllers;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import enums.Color;
import enums.HexState;
import models.Player;
import models.maprelated.*;

public class InitializeGameInfo {
    public static HashMap<String, String> unitNeededResource = new HashMap<String, String>();
    public static HashMap<String, String> unitNeededTech = new HashMap<String, String>();
    private static final HashMap<String, String> terrainInfo = new HashMap<>();
    private static final HashMap<String, String> featureInfo = new HashMap<>();
    private static final HashMap<String, String> resourceInfo = new HashMap<>();
    private static final HashMap<String, String> technologyInfo = new HashMap<>();
    public static HashMap<String, String> unitInfo = new HashMap<>();
    private static final ArrayList<String> terrainNames = new ArrayList<String>();
    private static final ArrayList<String> resourceNames = new ArrayList<String>();
    private static final HashMap<String, ArrayList<String>> appropriateTerrain = new HashMap<String, ArrayList<String>>();
    private static final HashMap<String, ArrayList<String>> appropriateFeature = new HashMap<String, ArrayList<String>>();
    private static final HashMap<String, String[]> terrainPossibleFeature = new HashMap<String, String[]>();
    public static HashMap<String, Color> terrainColors = new HashMap<String, Color>();
    private static final HashMap<String, Color> playerColor = new HashMap<String, Color>();
    private static final ArrayList<Player>  players = new ArrayList<Player>();
    private static int numberOFPlayers;

    private static final Random random = new Random();
    private static final World world = new World();;


    public static HashMap<String, ArrayList<String>> getAppropriateTerrain() {
        return appropriateTerrain;
    }
    public static HashMap<String, ArrayList<String>> getAppropriateFeature(){
        return appropriateFeature;
    }
    public static ArrayList<Player> getPlayers() {
        return players;
    }


    public static int getNumberOFPlayers() {
        return numberOFPlayers;
    }

    public static void setNumberOFPlayers(int numberOFPlayers) {
        InitializeGameInfo.numberOFPlayers = numberOFPlayers;
    }

    public static HashMap<String, String> getTerrainInfo() {
        return terrainInfo;
    }

    public static HashMap<String, String> getFeatureInfo() {
        return featureInfo;
    }

    public static HashMap<String, String> getResourceInfo() {
        return resourceInfo;
    }

    public static HashMap<String, String> getTechnologyInfo() {
        return technologyInfo;
    }

    public static HashMap<String, Color> getPlayerColor() {
        return playerColor;
    }

    public static World getWorld() {
        return world;
    }

    public static void initializeUnitInfo() {
        try {
            String resourceName = "files/UnitInfo.txt";
            ClassLoader classLoader = InitializeGameInfo.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
            String readUnitInfo = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            String[] readInfo = readUnitInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                String tech = info.split(" ")[6];
                String resource = info.split(" ")[5];

                if (tech.equals("NA")) {
                    unitNeededTech.put(name, null);
                } else {
                    unitNeededTech.put(name, tech);
                }
                if (resource.equals("NA")) {
                    unitNeededResource.put(name, null);
                } else {
                    unitNeededResource.put(name, resource);
                }

                unitInfo.put(name, info);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeTerrainInfo() {
        try {
            String resourceName = "files/TerrainInfo.txt";
            ClassLoader classLoader = InitializeGameInfo.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
            String readTerrainInfo = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

            String[] readInfo = readTerrainInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                terrainNames.add(name);
                terrainInfo.put(name, info);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeFeatureInfo() {
        try {
            String resourceName = "files/FeatureInfo.txt";
            ClassLoader classLoader = InitializeGameInfo.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
            String readFeatureInfo = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            String[] readInfo = readFeatureInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];


                featureInfo.put(name, info);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeTechnologyInfo() {
        try {
            String resourceName = "files/TechnologyInfo.txt";
            ClassLoader classLoader = InitializeGameInfo.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
            String readTechnologyInfo = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            String[] readInfo = readTechnologyInfo.split("\n");
            ArrayList<String> setArray=new ArrayList<String>();
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                technologyInfo.put(name, info);
                setArray.add(name);

            }
            Player.setTechnologies(setArray);
            for (Player player: players) {
                player.setTechnologyForPlayers();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeResourceInfo() {
        try {
            String resourceName = "files/ResourceInfo.txt";
            ClassLoader classLoader = InitializeGameInfo.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
            String readResourceInfo = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            String[] readInfo = readResourceInfo.split("\n");
            for (String temp : readInfo) {

                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];


                ArrayList<String> terrains = new ArrayList<String>();
                ArrayList<String> features = new ArrayList<String>();

                String[] terrainOrFeature = read[1].split(" ")[3].split(",");
                for (String temp1 : terrainOrFeature) {
                    if (InitializeGameInfo.featureInfo.containsKey(temp1)) {
                        features.add(temp1);
                    } else {
                        terrains.add(temp1);
                    }

                }
                appropriateFeature.put(name, features);
                appropriateTerrain.put(name, terrains);

                resourceNames.add(name);

                resourceInfo.put(name, info);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void initializeHashMap() {
        terrainPossibleFeature.put("Desert", new String[]{"Oasis", "FoodPlains"});
        terrainPossibleFeature.put("Grassland", new String[]{"Jungle", "Marsh"});
        terrainPossibleFeature.put("Hills", new String[]{"Forest", "Jungle"});
        terrainPossibleFeature.put("Plain", new String[]{"Forest", "Jungle"});
        terrainPossibleFeature.put("Tundra", new String[]{"Jungle"});

        terrainColors.put("Desert", Color.ANSI_YELLOW_BACKGROUND);
        terrainColors.put("Grassland", Color.ANSI_GREEN_BACKGROUND);
        terrainColors.put("Hills", Color.ANSI_RED_BACKGROUND);
        terrainColors.put("Mountain", Color.ANSI_BRAWN_BACKGROUND);
        terrainColors.put("Ocean", Color.ANSI_BULE_BACKGROUND);
        terrainColors.put("Plain", Color.ANSI_Bright_Green_BACKGROUND);
        terrainColors.put("Snow", Color.ANSI_WHITE_BACKGROUND);
        terrainColors.put("Tundra", Color.ANSI_BLACK_BACKGROUND);

        ArrayList<Color> playerColors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK));
        for (int i = 0; i < players.size(); i++) {
            playerColor.put(players.get(i).getName(), playerColors.get(i));
        }
    }

    public static void run() {
/*        new Player("A");
        new Player("B");
        new Player("C");*/
        initializeTerrainInfo();
        initializeFeatureInfo();
        initializeResourceInfo();
        initializeTechnologyInfo();
        initializeHashMap();
        initializeUnitInfo();
        initializeGameWorld();

    }

    public static void initializeGameWorld() {

        initializeHex(world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        initializeResource(10, world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        initializeRiver(world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        initializeCivilizations(numberOFPlayers, world.getHexInHeight(), world.getHexInWidth());
        //printResource();
        //players.get(0).addToRevealedHexes(world.getHex()[0][0]);
    }
/*    private void initializePlayerTiles() {
        Player[] players = new Player[2];
        players[0] = new Player("A");
        players[1] = new Player("B");
        world.getHex()[0][0].setOwner(players[0]);
        world.getHex()[0][1].setOwner(players[1]);
    }*/

    /*    private static void printResource() {
            Hex[][] hex = world.getHex();
            for (int i = 0; i < world.getHexInHeight(); i++) {
                for (int j = 0; j < world.getHexInWidth(); j++) {
                    System.out.print("terrain: "+hex[i][j].getTerrain().getName());
                    if (hex[i][j].getResource() != null) {
                        System.out.print(" resource: " + hex[i][j].getResource().getName());
                    }
                    if (hex[i][j].getFeature() != null) {
                        System.out.print(" feature: " + hex[i][j].getFeature().getName());
                    }
                    if (hex[i][j].getImprovement() != null) {
                        System.out.print(" improvement: " + hex[i][j].getImprovement());
                    }
                    System.out.println(" x,y:"+i + " "+ j);

                }
            }
        }*/
    private static String randomPickAName(ArrayList<String> names) {
        return names.get(Math.abs(Math.abs(random.nextInt())) % names.size());
    }

    private static String randomPickANameS(String[] names) {
        return names[Math.abs(random.nextInt()) % names.length];
    }

    public static void initializeHex(int hexInHeight, int hexInWidth, Hex[][] hex) {
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

    private static boolean isTerrainSuitable(ArrayList<String> PossibleTerrain, Hex tile) {
        if (PossibleTerrain.isEmpty()) return false;
        for (String s : PossibleTerrain) {
            if (Objects.equals(s, tile.getTerrain().getName())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFeatureSuitable(ArrayList<String> PossibleFeature, Hex tile) {
        if (PossibleFeature.isEmpty() || tile.getFeature() == null) return false;
        for (String s : PossibleFeature) {
            if (Objects.equals(s, tile.getFeature().getName())) {
                return true;
            }
        }
        return false;
    }

    public static void initializeResource(int numberOfTry, int hexInHeight, int hexInWidth, Hex[][] hex) {
        while (numberOfTry != 0) {
            //random resource
            String resourceName = randomPickAName(resourceNames);
            ArrayList<Hex> possibleHex = new ArrayList<Hex>();
            ArrayList<String> PossibleTerrain = appropriateTerrain.get(resourceName);
            ArrayList<String> PossibleFeature = appropriateFeature.get(resourceName);
            search:
            for (int i = 0; i < hexInHeight; i++) {
                for (int j = 0; j < hexInWidth; j++) {
                    if ((isTerrainSuitable(PossibleTerrain, hex[i][j]) || isFeatureSuitable(PossibleFeature, hex[i][j])) &&
                            (hex[i][j].getResource() == null || !Objects.equals(hex[i][j].getResource().getName(), resourceName))) {
                        //todo : random possibleHex
                        Resource resource = new Resource(resourceName);
                        hex[i][j].setResource(resource);
                        break search;
                    }
                }
            }
            numberOfTry--;
        }

    }

    public static void initializeRiver(int hexInHeight, int hexInWidth, Hex[][] hex) {
        int[][] oddDirection = new int[][]{{0, -1}, {1, -1}, {0, 1}, {1, 1}};
        int[][] evenDirection = new int[][]{{-1, -1}, {0, -1}, {-1, 1}, {0, 1}};
        for (int k = 0; k < 10; ) {
            int riverSide = Math.abs(random.nextInt()) % 4;
            int i = Math.abs(random.nextInt()) % (hexInHeight - 2) + 1;
            int j = Math.abs(random.nextInt()) % (hexInWidth - 2) + 1;
            if (!hex[i][j].isRiver(riverSide)) {
                hex[i][j].setRiver(riverSide);
                int[][] direction = j % 2 == 1 ? oddDirection : evenDirection;
                hex[i + direction[riverSide][0]][j + direction[riverSide][1]].setRiver(Math.abs(3 - riverSide));
                k++;
            }
        }
        for (int i = 0; i < hexInHeight; i++) {
            for (int j = 0; j < hexInWidth; j++) {
                if (hex[i][j].getFeature() != null && Objects.equals(hex[i][j].getFeature().getName(), "FoodPlains")) {
                    int[][] direction = j % 2 == 1 ? oddDirection : evenDirection;
                    int riverSide = Math.abs(random.nextInt()) % 4;
                    ;
                    while (i + direction[riverSide][0] >= hexInHeight || i + direction[riverSide][0] < 0 ||
                            j + direction[riverSide][1] >= hexInWidth || j + direction[riverSide][1] < 0) {
                        riverSide = Math.abs(random.nextInt()) % 4;
                    }
                    if (!hex[i][j].isRiver(riverSide)) {
                        hex[i][j].setRiver(riverSide);
                        hex[i + direction[riverSide][0]][j + direction[riverSide][1]].setRiver(Math.abs(3 - riverSide));
                    }
                }
            }
        }
    }

    public static void initializeCivilizations(int numberOfPlayers, int hexInHeight, int hexInWidth) {
        for (int i = 0; i < numberOfPlayers; i++) {
            int numberOfPlayerHex = 10;
            ArrayList<Hex> playerHex = new ArrayList<>();
            while (playerHex.size() != numberOfPlayerHex) {
                clear(playerHex);
                initializePlayerHex(numberOfPlayerHex, playerHex, players.get(i), Math.abs(random.nextInt()) % hexInHeight, Math.abs(random.nextInt()) % hexInWidth);
            }
            setPlayerHex(playerHex, players.get(i));
        }
    }

    private static void clear(ArrayList<Hex> playerHex) {
        for (Hex hex : playerHex) {
            hex.setOwner(null);
        }
        playerHex.clear();
    }

    private static int initializePlayerHex(int numberOfPlayerHex, ArrayList<Hex> playerHex, Player player, int i, int j) {
        int hexInHeight = world.getHexInHeight();
        int hexInWidth = world.getHexInWidth();
        Hex[][] hex = world.getHex();
        if (playerHex.size() == numberOfPlayerHex || i >= hexInHeight || j >= hexInWidth || i < 0 || j < 0) {
            return playerHex.size();
        }
        if (hex[i][j].getOwner() != null) {
            return playerHex.size();
        }
        playerHex.add(hex[i][j]);
        hex[i][j].setOwner(player);
        List<int[]> directions;
        if (j % 2 == 1) {
            directions = new java.util.ArrayList<>(List.of(new int[]{-1, 0},
                    new int[]{0, -1}, new int[]{1, -1}, new int[]{1, 0}, new int[]{1, 1}, new int[]{0, 1}));
        } else {
            directions = new java.util.ArrayList<>(List.of(new int[]{-1, 0},
                    new int[]{-1, -1}, new int[]{0, -1}, new int[]{1, 0}, new int[]{0, 1}, new int[]{-1, 1}));
        }
        while (directions.size() != 0) {
            int index = Math.abs(random.nextInt()) % directions.size();
            index = Math.abs(index) % 6;
            int[] selectedDirection = directions.get(index);
            int temp = initializePlayerHex(numberOfPlayerHex, playerHex, player, i + selectedDirection[0], j + selectedDirection[1]);
            if (temp >= numberOfPlayerHex) {
                break;
            }
            directions.remove(index);
        }
        if (playerHex.size() < numberOfPlayerHex) {
            playerHex.get(playerHex.size() - 1).setOwner(null);
            playerHex.remove(playerHex.size() - 1);
        }
        return playerHex.size();
    }

    private static void setPlayerHex(ArrayList<Hex> playerHex, Player player) {
/*        for (int i = 0; i < playerHex.size(); i++) {
            System.out.println(playerHex.get(i).getX()+ " " + playerHex.get(i).getY());
        }
        System.out.println(playerHex.size());
        System.out.println("===================");*/
        for (Hex hex : playerHex) {
            //hex.setOwner(player);
            hex.setState(HexState.Visible,player);
        }

    }


}
