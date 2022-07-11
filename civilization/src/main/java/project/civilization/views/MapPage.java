package project.civilization.views;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.enums.HexState;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.Resource;
import project.civilization.models.maprelated.World;

public class MapPage {
    public Pane pane;
    private static int[] mapBoundaries;

    public void initialize() {
        mapBoundaries = new int[]{0, 4, 0, 8};
        InitializeGameInfo.run();
        GameController.initializeGameController();
        World world = InitializeGameInfo.getWorld();
        world.getHex()[0][0].setState(HexState.Visible, GameController.getCurrentPlayer());
        world.getHex()[0][0].setResource(new Resource("Sugar"));
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                Hex hex = world.getHex()[i][j];
                initializeTerrainView(hex);
                if (hex.getFeature() != null) {
                    initializeFeatureView(hex);
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
                if (hexes[i][j].getState(GameController.getCurrentPlayer()) == HexState.FogOfWar) {
                    initializeFogOfWarView(hexes[i][j]);
                } else if (hexes[i][j].getState(GameController.getCurrentPlayer()) == HexState.Revealed) {
                    setTerrainViewCoordinates(i, j, hexes[i][j].getTerrain().getTerrainView());
                    initializeRevealedView(hexes[i][j]);
                } else {
                    setTerrainViewCoordinates(i, j, hexes[i][j].getTerrain().getTerrainView());
                    if (hexes[i][j].getFeature() != null) {
                        setFeatureViewCoordinates(i, j, hexes[i][j].getFeature().getFeatureView());
                    }
                    initializeRiverView(hexes[i][j]);
                }
            }
        }
    }

    private void initializeRevealedView(Hex hex) {
        String riverAddress = "pictures/terrainTexturs/revealed.png";
        Image riverImage = new Image(CivilizationApplication.class.getResource(riverAddress).toExternalForm());
        javafx.scene.image.ImageView riverView = new javafx.scene.image.ImageView(riverImage);
        riverView.setOpacity(0.7);
        setTerrainViewCoordinates(hex.getX(), hex.getY(), riverView);
    }

    private void initializeFogOfWarView(Hex hex) {
        String riverAddress = "pictures/terrainTexturs/fogofwar.png";
        Image riverImage = new Image(CivilizationApplication.class.getResource(riverAddress).toExternalForm());
        javafx.scene.image.ImageView riverView = new javafx.scene.image.ImageView(riverImage);
        setTerrainViewCoordinates(hex.getX(), hex.getY(), riverView);
    }

    private void initializeRiverView(Hex hex) {
        for (int i = 0; i < 4; i++) {
            if (hex.isRiver(i)) {
                String riverAddress = "pictures/river/" + i + ".png";
                Image riverImage = new Image(CivilizationApplication.class.getResource(riverAddress).toExternalForm());
                javafx.scene.image.ImageView riverView = new javafx.scene.image.ImageView(riverImage);
                setTerrainViewCoordinates(hex.getX(), hex.getY(), riverView);
            }
        }
    }

    private void setTerrainViewCoordinates(int i, int j, ImageView terrainView) {
        int align = (j % 2 == 0) ? 0 : 100;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        terrainView.setX(j * 150);
        terrainView.setY(align + i * 200);
        pane.getChildren().add(terrainView);
    }

    private void setFeatureViewCoordinates(int i, int j, ImageView featureView) {
        int align = (j % 2 == 0) ? 100 : 200;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        featureView.setX(50 + (j * 150));
        featureView.setY(align + i * 200);
        pane.getChildren().add(featureView);
    }

    private void initializeFeatureView(Hex hex) {
        Image featureImage;
        String featureAddress = "pictures/featureTexture/" + hex.getFeature().getName() + ".png";
        featureImage = new Image(CivilizationApplication.class.getResource(featureAddress).toExternalForm());
        javafx.scene.image.ImageView featureView = new javafx.scene.image.ImageView(featureImage);
        hex.getFeature().setFeatureView(featureView);
    }

    private void initializeTerrainView(Hex hex) {
        Image image;
        String address;
        address = "pictures/terrainTexturs/" + hex.getTerrain().getName() + "200-200.png";
        image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        hex.getTerrain().setTerrainView(imageView);
        imageView.setOnMouseClicked(event ->  {
            showHexDetails(hex);
        });
    }

    private void showHexDetails(Hex hex) {
        VBox vBox = new VBox();
        Label label = new Label(hex.getTerrain().getName());
        Popup popup = new Popup();
        addLabelToPopup(label, vBox);
        if(hex.getResource() != null){
            Label label1 = new Label(hex.getResource().getName()+
                    " ,food:" + hex.getResource().getFood()+
                    " ,product:" +hex.getResource().getProduction()+
                    " ,gold:"+ hex.getResource().getGold());
            Image image = new Image(CivilizationApplication.class.getResource("pictures/resources/"+hex.getResource().getName()+".png").toExternalForm());
            ImageView imageView  = new ImageView(image);
            label1.setGraphic(imageView);
            addLabelToPopup(label1, vBox);
        }
        if(hex.getFeature() != null){
            Label label1 = new Label(hex.getFeature().getName() +
                    " ,food:" + hex.getFeature().getFood()+
                    " ,product:"+hex.getFeature().getProduction()+
                    " ,gold:"+ hex.getFeature().getGold()+
                    " ,CMP:"+ hex.getFeature().getCombatModifiersPercentage()+
                    " ,MP:"+hex.getFeature().getMovePoint());
            addLabelToPopup(label1, vBox);
        }
        popup.getContent().add(vBox);
        popup.setY(10);
        popup.setAutoHide(true);
        if (!popup.isShowing())
            popup.show(CivilizationApplication.stages);
    }

    private void addLabelToPopup(Label label,VBox VBox) {
        label.setStyle(" -fx-background-color: #6e663f;" +
                "    -fx-font-size: 12;\n" +
                "    -fx-text-fill: black;");
        VBox.getChildren().add(label);
    }
}
    /* GameController.setSelectedHex(hex);
        VBox vBox = new VBox();
        Text text = new Text(GameController.showHexDetails(hex.getX(),hex.getY()));
        text.setStyle("-fx-font-size: 10");
        Button button = new Button("OK");
        button.setOnMouseClicked(event ->  {
            pane.getChildren().remove(vBox);
        });
        vBox.setLayoutX(400);
        vBox.setLayoutY(400);
        vBox.getChildren().add(text);
        vBox.getChildren().add(button);
        pane.getChildren().add(vBox);
        vBox.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));*/
