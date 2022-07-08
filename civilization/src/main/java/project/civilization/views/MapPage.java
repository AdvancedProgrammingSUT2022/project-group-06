package project.civilization.views;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.World;

public class MapPage {
    public Pane pane;
    private static int[] mapBoundaries;

    public void initialize() {
        mapBoundaries = new int[]{0, 4, 0, 8};
        InitializeGameInfo.run();
        World world = InitializeGameInfo.getWorld();
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                Hex hex = world.getHex()[i][j];
                initializeTileView(i, j, hex);
                if(hex.getFeature() != null){
                    initializeFeatureView(i, j, hex);
                }
            }
        }
        initializePane();
        Platform.runLater(() -> {
            pane.requestFocus();
        });
        handleKeyEvent();
    }

    private void handleKeyEvent() {
        pane.setOnKeyPressed(e -> {
            int[] directions = new int[]{0, 0, 0, 0};
            switch (e.getCode()) {
                case RIGHT:
                    directions[2]++;
                    directions[3]++;
                    MoveMap(directions);
                    break;
                case LEFT:
                    directions[2]--;
                    directions[3]--;
                    MoveMap(directions);
                    break;
                case UP:
                    directions[0]--;
                    directions[1]--;
                    MoveMap(directions);
                    break;
                case DOWN:
                    directions[0]++;
                    directions[1]++;
                    MoveMap(directions);
                    break;
                default:
                    break;
            }
        });
    }

    private void MoveMap(int[] directions) {
        if (mapBoundaries[0] + directions[0] < 0 || mapBoundaries[1] + directions[1] > InitializeGameInfo.getWorld().getHexInHeight() ||
                mapBoundaries[2] + directions[2] < 0 || mapBoundaries[3] + directions[3] > InitializeGameInfo.getWorld().getHexInWidth()) {
            return;
        }
        pane.getChildren().removeAll(pane.getChildren());
        for (int i = 0; i < 4; i++) {
            mapBoundaries[i] += directions[i];
        }
        initializePane();
    }

    private void initializePane() {
        World world = InitializeGameInfo.getWorld();
        Hex[][] hexes = world.getHex();
        for (int i = mapBoundaries[0]; i < mapBoundaries[1]; i++) {
            for (int j = mapBoundaries[2]; j < mapBoundaries[3]; j++) {
                setTerrainViewCoordinates(i, j , hexes[i][j].getTerrain().getTerrainView());
                if(hexes[i][j].getFeature() != null) {
                    setFeatureViewCoordinates(i, j , hexes[i][j].getFeature().getFeatureView());
                }
            }
        }
    }

    private void setTerrainViewCoordinates(int i, int j, ImageView terrainView) {
        int align = (j % 2 == 0) ? 0 : 100;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        terrainView.setX (j * 150);
        terrainView.setY(align + i * 200);
        pane.getChildren().add(terrainView);
    }

    private void setFeatureViewCoordinates(int i, int j, ImageView featureView){
        int align = (j % 2 == 0) ? 100 : 200;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        featureView.setX(50 + (j * 150));
        featureView.setY(align + i * 200);
        pane.getChildren().add(featureView);
    }

    private void initializeFeatureView(int i, int j, Hex hex) {
        Image featureImage;
        String featureAddress = "pictures/featureTexture/" + hex.getFeature().getName() + ".png";
        featureImage = new Image(CivilizationApplication.class.getResource(featureAddress).toExternalForm());
        javafx.scene.image.ImageView featureView = new javafx.scene.image.ImageView(featureImage);
        hex.getFeature().setFeatureView(featureView);
    }

    private void initializeTileView(int i, int j, Hex hex) {
        Image image;
        String address = "pictures/terrainTexturs/" + hex.getTerrain().getName() + "200-200.png";
        image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        hex.getTerrain().setTerrainView(imageView);
    }
}
