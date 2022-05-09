import controllers.InitializeGameInfo;
import enums.HexState;
import models.Player;
import models.maprelated.Hex;
import models.maprelated.Terrain;
import models.maprelated.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InitializeGameInfoTest {
    @BeforeAll
    public static void setUp(){
        new Player("A");
        new Player("B");
        new Player("C");
        InitializeGameInfo.initializeTerrainInfo();
        InitializeGameInfo.initializeFeatureInfo();
        InitializeGameInfo. initializeResourceInfo();
        InitializeGameInfo.initializeTechnologyInfo();
        InitializeGameInfo.initializeHashMap();
        InitializeGameInfo.initializeUnitInfo();
    }

/*    @Mock
    World world;*/

    @Test
    public void randomPickANameTest() {

    }

    @Test
    public void terrainsValidationCheck() {
        World world = new World();
        InitializeGameInfo.initializeHex(world.getHexInHeight(),world.getHexInWidth(),world.getHex());
        Hex[][] hexes = world.getHex();
        boolean result = true;
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                Terrain terrain = hexes[i][j].getTerrain();
                if(Objects.equals(terrain.getName(), "FoodPlains")){
                    if(!(terrain.getFood() == 2 && terrain.getProduction()== 0 && terrain.getGold()== 0 &&
                            terrain.getCombatModifiersPercentage()== -33 && terrain.getMovePoint() == 2)){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Forest")){
                    if(!(terrain.getFood() == 1 && terrain.getProduction()== 1 && terrain.getGold()== 0 &&
                            terrain.getCombatModifiersPercentage()== 25 && terrain.getMovePoint() == 2)){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Ice")){
                    if(!(terrain.getFood() == 0 && terrain.getProduction()== 0 && terrain.getGold()== 0 &&
                            terrain.getCombatModifiersPercentage()== 0 && terrain.getMovePoint() == 0)){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Jungle")){
                    if(!(terrain.getFood() == 1 && terrain.getProduction()== -1 && terrain.getGold()== 0 &&
                            terrain.getCombatModifiersPercentage()== 25 && terrain.getMovePoint() == 2)){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Marsh")){
                    if(!(terrain.getFood() == -1 && terrain.getProduction()== 0 && terrain.getGold()== 0 &&
                            terrain.getCombatModifiersPercentage()== -33 && terrain.getMovePoint() == 2)){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Oasis")){
                    if(!(terrain.getFood() == 3 && terrain.getProduction()== 0 && terrain.getGold()== 1 &&
                            terrain.getCombatModifiersPercentage()== -33 && terrain.getMovePoint() == 1)){
                        result = false;
                    }
                }
            }
        }
        Assertions.assertTrue(result);
    }

    @Test
    public void featureValidationCheck(){
        World world = new World();
        InitializeGameInfo.initializeHex(world.getHexInHeight(),world.getHexInWidth(),world.getHex());
        Hex[][] hexes = world.getHex();
        boolean result = true;
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                Terrain terrain = hexes[i][j].getTerrain();
                if(Objects.equals(terrain.getName(), "FoodPlains")){
                    if(hexes[i][j].getFeature() != null ||
                            !Objects.equals(hexes[i][j].getFeature().getName(), "Desert")){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Forest")){
                    if(hexes[i][j].getFeature() != null ||
                            !Objects.equals(hexes[i][j].getFeature().getName(), "Hills")||
                            !Objects.equals(hexes[i][j].getFeature().getName(), "Plain")){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Ice")){
                    if(hexes[i][j].getFeature() != null){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Jungle")){
                    if(hexes[i][j].getFeature() != null ||!hexes[i][j].getFeature().getName().matches("Grassland|Tundra|Plain|Hills")){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Marsh")){
                    if(hexes[i][j].getFeature() != null ||
                            !Objects.equals(hexes[i][j].getFeature().getName(), "Grassland")){
                        result = false;
                    }
                }else if(Objects.equals(terrain.getName(), "Oasis")){
                    if(hexes[i][j].getFeature() != null ||
                            !Objects.equals(hexes[i][j].getFeature().getName(), "Desert")){
                        result = false;
                    }
                }
            }
        }
        Assertions.assertTrue(result);
    }

    @Test
    public void checkResourceValidation(){
        World world = new World();
        InitializeGameInfo.initializeHex(world.getHexInHeight(),world.getHexInWidth(),world.getHex());
        Hex[][] hexes = world.getHex();
        boolean result = true;
        InitializeGameInfo.initializeResource(10, world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        for (int i = 0; i < world.getHexInHeight() ; i++) {
            for (int j = 0; j < world.getHexInWidth() ; j++) {
                if(hexes[i][j].getResource() != null){
                    ArrayList<String> possibleTerrain = InitializeGameInfo.getAppropriateTerrain().get(hexes[i][j].getResource().getName());
                    ArrayList<String> possibleFeature = InitializeGameInfo.getAppropriateFeature().get(hexes[i][j].getResource().getName());
                    if(!possibleTerrain.contains(hexes[i][j].getTerrain().getName()) && (hexes[i][j].getFeature() != null && !possibleFeature.contains(hexes[i][j].getFeature().getName()))){
                        result = false;
                    }
                }
            }
        }
        Assertions.assertTrue(result);
    }

    @Test
    public void checkAllFoodPlainsHaveRiver(){
        World world = new World();
        InitializeGameInfo.initializeHex(world.getHexInHeight(),world.getHexInWidth(),world.getHex());
        Hex[][] hexes = world.getHex();
        boolean result = true;
        InitializeGameInfo.initializeRiver(world.getHexInHeight(), world.getHexInWidth(), world.getHex());
        for (int i = 0; i < world.getHexInHeight() ; i++) {
            for (int j = 0; j < world.getHexInWidth() ; j++) {
                if (hexes[i][j].getFeature() != null && Objects.equals(hexes[i][j].getFeature().getName(), "FoodPlains")) {
                    result = false;
                    for (boolean hasRiver: hexes[i][j].getHasRiver()) {
                        if (hasRiver) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        Assertions.assertTrue(result);
    }

    @Test
    public void testNumberOfPlayerBeginningHexes(){
        InitializeGameInfo.initializeGameWorld();
        boolean result = true;
        World world = InitializeGameInfo.getWorld();
        Hex[][] hexes = world.getHex();
        int[] numberOfPlayerBeginningHexes = new int[InitializeGameInfo.getNumberOFPlayers()];
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                for (int k = 0; k < InitializeGameInfo.getNumberOFPlayers(); k++) {
                    if (hexes[i][j].getState(InitializeGameInfo.getPlayers().get(k))== HexState.Visible){
                        numberOfPlayerBeginningHexes[k]++;
                    }
                }
            }
        }
        for (int i = 0; i < InitializeGameInfo.getNumberOFPlayers(); i++) {
            if(numberOfPlayerBeginningHexes[i] != 10) result = false;
        }
        Assertions.assertTrue(result);
    }
}
