package project.civilization.controllers;


import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.civilization.CivilizationApplication;
import project.civilization.enums.Color;
import project.civilization.enums.HexState;
import project.civilization.models.Player;
import project.civilization.models.gainable.Building;
import project.civilization.models.gainable.Technology;
import project.civilization.models.maprelated.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class InitializeGameInfo {
    public static HashMap<String, String> unitNeededResource = new HashMap<String, String>();
    public static HashMap<String, String> unitNeededTech = new HashMap<String, String>();
    private static final HashMap<String, String> terrainInfo = new HashMap<>();
    private static final HashMap<String, String> featureInfo = new HashMap<>();
    private static final HashMap<String, String> resourceInfo = new HashMap<>();
    private static final ArrayList<Technology> allTechnologies = new ArrayList<>();
    private static final HashMap<String, Technology> technologyByName = new HashMap<>();
    private static final HashMap<String, String> technologyInfo = new HashMap<>();
    public static HashMap<String, String> unitInfo = new HashMap<>();
    private static final ArrayList<String> terrainNames = new ArrayList<String>();
    private static final ArrayList<String> resourceNames = new ArrayList<String>();
    private static final HashMap<String, ArrayList<String>> appropriateTerrain = new HashMap<String, ArrayList<String>>();
    private static final HashMap<String, ArrayList<String>> appropriateFeature = new HashMap<String, ArrayList<String>>();
    private static final HashMap<String, String[]> terrainPossibleFeature = new HashMap<String, String[]>();
    public static HashMap<String, Color> terrainColors = new HashMap<String, Color>();
    private static final HashMap<String, Color> playerColor = new HashMap<String, Color>();
    private static ArrayList<Player> players = new ArrayList<Player>();
    private static int numberOFPlayers;
    private static ArrayList<Building> allBuildings = new ArrayList<>();
    private static final HashMap<String, Building> buildingsInfo = new HashMap<>();

    private static final Random random = new Random();
    private static World world;

    public static HashMap<String, ArrayList<String>> getAppropriateTerrain() {
        return appropriateTerrain;
    }

    public static HashMap<String, ArrayList<String>> getAppropriateFeature() {
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

    public static HashMap<String, Technology> getTechnologyByName() {
        return technologyByName;
    }

    public static HashMap<String, Color> getPlayerColor() {
        return playerColor;
    }

    public static World getWorld() {
        return world;
    }

    public static ArrayList<Building> getAllBuildings() {
        return allBuildings;
    }

    public static ArrayList<Technology> getAllTechnologies() {
        return allTechnologies;
    }

    public static HashMap<String, Building> getBuildingsInfo() {
        return buildingsInfo;
    }

    public static void initializeUnitInfo() {
        try {
            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/UnitInfo.txt")).toExternalForm());
            String readUnitInfo = new String(Files.readAllBytes(Paths.get(address.toURI())));
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
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeTerrainInfo() {
        try {
            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/TerrainInfo.txt")).toExternalForm());
            String readTerrainInfo = new String(Files.readAllBytes(Paths.get(address.toURI())));

            String[] readInfo = readTerrainInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                terrainNames.add(name);
                terrainInfo.put(name, info);
            }

        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeFeatureInfo() {
        try {
            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/FeatureInfo.txt")).toExternalForm());
            String readFeatureInfo = new String(Files.readAllBytes(Paths.get(address.toURI())));
            String[] readInfo = readFeatureInfo.split("\n");
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];


                featureInfo.put(name, info);
            }
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeTechnologyInfo() {
        try {
            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/TechnologyInfo.txt")).toExternalForm());
            String readTechnologyInfo = new String(Files.readAllBytes(Paths.get(address.toURI())));
            String[] readInfo = readTechnologyInfo.split("\n");
            ArrayList<String> setArray = new ArrayList<String>();
            for (String temp : readInfo) {
                String[] read = temp.split("#");
                String name = read[0];
                String info = read[1];

                technologyInfo.put(name, info);
                Technology technology = new Technology(name, null);
                technologyByName.put(name, technology);
                allTechnologies.add(technology);
                setArray.add(name);
                for (Player player : players) {

                    player.setTechnologyForPlayers();

                }
            }

        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void initializeResourceInfo() {
        try {
            URL address = new URL(Objects.requireNonNull(CivilizationApplication.class.getResource("files/ResourceInfo.txt")).toExternalForm());
            String readResourceInfo = new String(Files.readAllBytes(Paths.get(address.toURI())));
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
        } catch (IOException | URISyntaxException e) {
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
/*        numberOFPlayers = 3;
        Player a = new Player("A");
        Player b = new Player("B");
        Player c = new Player("C");*/
        initializeTerrainInfo();
        initializeFeatureInfo();
        initializeResourceInfo();
        initializeTechnologyInfo();
        initializeHashMap();
        initializeUnitInfo();
        initializeGameWorld();
        initializeBuildings();
    }

    public static void initializeGameWorld() {

        initializeHex(world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        initializeResource(20, world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        initializeRiver(world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        initializeCivilizations(numberOFPlayers, world.getHexInHeight(), world.getHexInWidth());
    }

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
        for (Hex hex : playerHex) {
            hex.setState(HexState.Visible, player);
        }
    }


    public static void setMapSize(String mapSize) {
        String[] size = mapSize.split("\\*");
        int hexInHeight = Integer.parseInt(size[0]);
        int hexInWidth = Integer.parseInt(size[1]);
        world = new World(hexInHeight, hexInWidth);
    }

    public static void runAsLoadGame(World worldd, ArrayList<Player> players1) {
        players = players1;
        world = worldd;
        initializeTerrainInfo();
        initializeFeatureInfo();
        initializeResourceInfo();
        initializeTechnologyInfo();
        initializeHashMap();
        initializeUnitInfo();
    }

    public static void initializeBuildings() {
        addBuildingsToList();

        fillBuildingsInfo();
        setPrerequisiteForBuildings();
//
//        try {
//            saveToFile("buildings.json", new Gson().toJson(allBuildings));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private static void addBuildingsToList() {
        Building barracks = new Building("Barracks", 80, 1, 2, "Bronze working");
        allBuildings.add(barracks);

        Building granary = new Building("Granary", 100, 1, 2, "Pottery");
        allBuildings.add(granary);

        Building library = new Building("Library", 80, 1, 3, "Writing");
        allBuildings.add(library);

        Building monument = new Building("Monument", 60, 1, 1, null);
        allBuildings.add(monument);

        Building walls = new Building("Walls", 100, 1, 2, "Masonry");
        allBuildings.add(walls);

        Building waterMill = new Building("Watermill", 120, 2, 2, "The Wheel");
        allBuildings.add(waterMill);

        Building armory = new Building("Armory", 130, 3, 2, "Iron working");
        allBuildings.add(armory);

        Building burialTomb = new Building("Burial Tomb", 120, 0, 2, "Philosophy");
        allBuildings.add(burialTomb);

        Building circus = new Building("Circus", 150, 3, 2, "Horseback Riding");
        allBuildings.add(circus);

        Building colosseum = new Building("Colosseum", 150, 3, 2, "Construction");
        allBuildings.add(colosseum);

        Building courtHouse = new Building("Court House", 200, 5, 2, "Mathematics");
        allBuildings.add(courtHouse);

        Building stable = new Building("Stable", 100, 1, 2, "Horseback Riding");
        allBuildings.add(stable);

        Building temple = new Building("Temple", 120, 2, 2, "Philosophy");
        allBuildings.add(temple);

        Building castle = new Building("Castle", 200, 3, 2, "Chivalry");
        allBuildings.add(castle);

        Building forge = new Building("Forge", 150, 2, 2, "metalCasting");
        allBuildings.add(forge);

        Building garden = new Building("Garden", 120, 2, 2, "Theology");
        allBuildings.add(garden);

        Building market = new Building("Market", 120, 0, 2, "Currency");
        allBuildings.add(market);

        Building mint = new Building("Mint", 120, 0, 2, "Currency");
        allBuildings.add(mint);

        Building monastery = new Building("Monastery", 120, 2, 2, "Theology");
        allBuildings.add(monastery);

        Building university = new Building("University", 200, 3, 2, "Education");
        allBuildings.add(university);

        Building workshop = new Building("Workshop", 100, 2, 2, "Metal casting");
        allBuildings.add(workshop);

        Building bank = new Building("Bank", 220, 0, 2, "Banking");
        allBuildings.add(bank);

        Building militaryAcademy = new Building("Military Academy", 350, 3, 2, "Military Science");
        allBuildings.add(militaryAcademy);

        Building museum = new Building("Museum", 350, 3, 2, "Archeology");
        allBuildings.add(museum);

        Building operaHouse = new Building("Opera House", 220, 3, 2, "Acoustics");
        allBuildings.add(operaHouse);

        Building publicSchool = new Building("Public School", 350, 3, 2, "Scientific Theory");
        allBuildings.add(publicSchool);

        Building satrapCourt = new Building("Satrap's Court", 220, 0, 2, "Banking");
        allBuildings.add(satrapCourt);

        Building theater = new Building("Theater", 300, 5, 2, "Printing Press");
        allBuildings.add(theater);

        Building windmill = new Building("Windmill", 180, 2, 2, "Economics");
        allBuildings.add(windmill);

        Building arsenal = new Building("Arsenal", 350, 3, 2, "Railroad");
        allBuildings.add(arsenal);

        Building broadcastTower = new Building("Broadcast Tower", 600, 3, 2, "Radio");
        allBuildings.add(broadcastTower);

        Building factory = new Building("Factory", 300, 3, 2, "Steam Power");
        allBuildings.add(factory);

        Building hospital = new Building("Hospital", 400, 2, 2, "Biology");
        allBuildings.add(hospital);

        Building militaryBase = new Building("Military Base", 450, 4, 2, "Telegraph");
        allBuildings.add(militaryBase);

        Building stockExchange = new Building("Stock Exchange", 650, 0, 2, "Electricity");
        allBuildings.add(stockExchange);

        Building palace = new Building("Palace", 1, 0, 0, null);
        allBuildings.add(palace);
    }

    private static void setPrerequisiteForBuildings() {
        buildingsInfo.get("Armory").setPrerequisite(buildingsInfo.get("Barracks"));
        buildingsInfo.get("Temple").setPrerequisite(buildingsInfo.get("Monument"));
        buildingsInfo.get("Castle").setPrerequisite(buildingsInfo.get("Walls"));
        buildingsInfo.get("University").setPrerequisite(buildingsInfo.get("Library"));
        buildingsInfo.get("Museum").setPrerequisite(buildingsInfo.get("Opera House"));
        buildingsInfo.get("Opera House").setPrerequisite(buildingsInfo.get("Temple"));
        buildingsInfo.get("Public School").setPrerequisite(buildingsInfo.get("University"));
        buildingsInfo.get("Satrap's Court").setPrerequisite(buildingsInfo.get("Market"));
        buildingsInfo.get("Theater").setPrerequisite(buildingsInfo.get("Colosseum"));
        buildingsInfo.get("Arsenal").setPrerequisite(buildingsInfo.get("Military Academy"));
        buildingsInfo.get("Broadcast Tower").setPrerequisite(buildingsInfo.get("Museum"));
        buildingsInfo.get("Military Base").setPrerequisite(buildingsInfo.get("Bank"));
    }

    private static void fillBuildingsInfo() {
        for (Building building : allBuildings) {
            buildingsInfo.put(building.getName(), building);
        }

    }

    private static void saveToFile(String fileName, String text) throws FileNotFoundException {
        File file = new File(fileName);
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(text);
        printWriter.close();
    }

    private static String loadFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream inputStream = new FileInputStream(file);
        String text = new String(inputStream.readAllBytes());
        inputStream.close();
        return text;
    }
}
