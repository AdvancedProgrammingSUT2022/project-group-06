package project.civilization.views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;
import project.civilization.controllers.InitializeGameInfo;
import project.civilization.enums.HexState;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.World;
import project.civilization.models.units.Unit;

public class MapPage {
    public Pane pane;
    private static int[] mapBoundaries;
    public static boolean isANewGame = true;
    @FXML
    private Button saveGameButton;
    @FXML
    private Button nextTurnButton;

    public void initialize() {
        mapBoundaries = new int[]{0, 4, 0, 8};
        if (isANewGame) {
            InitializeGameInfo.run();
            GameController.initializeGameController();
        } else {
            loadGme();
        }
        World world = InitializeGameInfo.getWorld();
/*        world.getHex()[0][0].setOwner(GameController.getCurrentPlayer());
        world.getHex()[0][0].setState(HexState.Visible, GameController.getCurrentPlayer());
        Military military = new Military("Archer", world.getHex()[0][0], GameController.getCurrentPlayer());
        world.getHex()[0][0].setMilitaryUnit(military);
        Civilian civilian = new Civilian("Worker", world.getHex()[0][0], GameController.getCurrentPlayer());
        world.getHex()[0][0].setCivilianUnit(civilian);*/
        for (int i = 0; i < world.getHexInHeight(); i++) {
            for (int j = 0; j < world.getHexInWidth(); j++) {
                Hex hex = world.getHex()[i][j];
                initializeTerrainView(hex);
                if (hex.getFeature() != null) {
                    initializeFeatureView(hex);
                }
            }
        }
        GameController.startGame();
        initializePane();
        Platform.runLater(() -> {
            pane.requestFocus();
        });
        handleKeyEvent();
    }

    private void loadGme() {
        InitializeGameInfo.runAsLoadGame();
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
        for (int i = 0; i < 4; i++) {
            mapBoundaries[i] += directions[i];
        }
        resetPane();
    }

    private void resetPane() {
        pane.getChildren().removeAll(pane.getChildren());
        initializeButtons();
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
                        setHexDetailsViewCoordinates(i, j, hexes[i][j].getFeature().getFeatureView(),0, 50);
                    }
                    initializeRiverView(hexes[i][j]);
                    if (hexes[i][j].getMilitaryUnit() != null) {
                        initializeUnitView(hexes[i][j], hexes[i][j].getMilitaryUnit(), 30, 0);
                    }
                    if( hexes[i][j].getCivilianUnit() != null){
                        initializeUnitView(hexes[i][j], hexes[i][j].getCivilianUnit(), 100, 0);
                    }
                    if (hexes[i][j].getOwner() != null) {
                        initializeOwnerView(hexes[i][j]);
                    }
                }
            }
        }
    }

    private void initializeButtons() {
        pane.getChildren().add(saveGameButton);
        pane.getChildren().add(nextTurnButton);
    }

    private void initializeOwnerView(Hex hex) {
        Text text = new Text(hex.getOwner().getName());
        text.setX(hex.getTerrain().getTerrainView().getX() + 100);
        text.setY(hex.getTerrain().getTerrainView().getY() + 100);
        text.setStyle("    -fx-font-size: 25;\n" +
                "    -fx-text-fill: black;");
        text.minWidth(100);
        text.minHeight(100);
        pane.getChildren().add(text);
    }

    private void initializeUnitView(Hex hex, Unit unit, int alignX, int alignY) {
        String unitAddress = "pictures/units/" + unit.getName() + ".png";
        Image UnitImage = new Image(CivilizationApplication.class.getResource(unitAddress).toExternalForm());
        javafx.scene.image.ImageView unitView = new javafx.scene.image.ImageView(UnitImage);
        unitView.setScaleX(2);
        unitView.setScaleY(2);
        setHexDetailsViewCoordinates(hex.getX(), hex.getY(), unitView ,alignY , alignX);
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

    private void setHexDetailsViewCoordinates(int i, int j, ImageView featureView, int alignY,int alignX) {
        int align = (j % 2 == 0) ? 100 : 200;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        featureView.setX(alignX + (j * 150));
        featureView.setY(align + i * 200 + alignY);
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
        imageView.setOnMouseClicked(event -> {
            showHexDetails(hex);
        });
    }

    private void showHexDetails(Hex hex) {
        VBox vBox = new VBox();
        Label label = new Label(hex.getTerrain().getName());
        Popup popup = new Popup();
        addLabelToPopup(label, vBox);
        if (hex.getResource() != null) {
            Label label1 = new Label(hex.getResource().getName() +
                    " ,food:" + hex.getResource().getFood() +
                    " ,product:" + hex.getResource().getProduction() +
                    " ,gold:" + hex.getResource().getGold());
            Image image = new Image(CivilizationApplication.class.getResource("pictures/resources/" + hex.getResource().getName() + ".png").toExternalForm());
            ImageView imageView = new ImageView(image);
            label1.setGraphic(imageView);
            addLabelToPopup(label1, vBox);
        }
        if (hex.getFeature() != null) {
            Label label1 = new Label(hex.getFeature().getName() +
                    " ,food:" + hex.getFeature().getFood() +
                    " ,product:" + hex.getFeature().getProduction() +
                    " ,gold:" + hex.getFeature().getGold() +
                    " ,CMP:" + hex.getFeature().getCombatModifiersPercentage() +
                    " ,MP:" + hex.getFeature().getMovePoint());
            addLabelToPopup(label1, vBox);
        }
        popup.getContent().add(vBox);
        popup.setY(10);
        popup.setAutoHide(true);
        if (!popup.isShowing())
            popup.show(CivilizationApplication.stages);
    }

    private void addLabelToPopup(Label label, VBox VBox) {
        label.setStyle(" -fx-background-color: #6e663f;" +
                "    -fx-font-size: 12;\n" +
                "    -fx-text-fill: black;");
        VBox.getChildren().add(label);
    }

    public void saveGame(MouseEvent mouseEvent) {
        GameController.saveGame("A");
    }

    public void nextTurn(MouseEvent mouseEvent) {
        String outPut = GameController.changeTurn();
        if (outPut.startsWith("Turn changed successfully")) {
            GameController.checkTimeVariantProcesses();
            if (GameController.getTurn() == 1) GameController.startGame();
            Platform.runLater(() -> {
                pane.requestFocus();
            });
            resetPane();
        }else{
            System.out.println(outPut);
        }
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