package project.civilization.views;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.GameController;
import project.civilization.enums.Menus;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Television {
    public Pane pane;
    private static int[] mapBoundaries;
    ImageView[][] tilesImageViews;
    int hexInHeight;
    int hexInWidth;

    private Timer timer;

    public void initialize() {
        mapBoundaries = new int[]{0, 4, 0, 8};
        hexInHeight = GameController.getHexInHeight();
        hexInWidth = GameController.getHexInWidth();
        tilesImageViews = new ImageView[hexInHeight][hexInWidth];
        String[] terrainNames = GameController.getTerrainNames().split(" ");
        for (int i = 0; i < hexInHeight; i++) {
            for (int j = 0; j < hexInWidth; j++) {
                initializeTerrainView(terrainNames[(i*hexInWidth + j)], i, j);
            }
        }
        initializePane();
        handleKeyEvent();
        Platform.runLater(() -> {
            pane.requestFocus();
        });
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    resetPane();
                });

            }
        },0, 2000);
    }
    private void resetPane(){
        pane.getChildren().removeAll(pane.getChildren());
        initializePane();
    }
    private void handleKeyEvent() {
        pane.setOnKeyPressed(e -> {
            int[] directions = new int[]{0, 0, 0, 0};
            switch (e.getCode()) {
                case RIGHT:
                    directions[2] = 1;
                    directions[3] = 1;
                    MoveMap(directions);
                    break;
                case LEFT:
                    directions[2] = -1;
                    directions[3] = -1;
                    MoveMap(directions);
                    break;
                case UP:
                    directions[0] = -1;
                    directions[1] = -1;
                    MoveMap(directions);
                    break;
                case DOWN:
                    directions[0] = 1;
                    directions[1] = 1;
                    MoveMap(directions);
                    break;
            }
        });
    }

    private void MoveMap(int[] directions) {
        if (mapBoundaries[0] + directions[0] < 0 || mapBoundaries[1] + directions[1] > hexInHeight ||
                mapBoundaries[2] + directions[2] < 0 || mapBoundaries[3] + directions[3] > hexInWidth) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            mapBoundaries[i] += directions[i];
        }
        initializePane();
    }

    private void initializeTerrainView(String terrainName, int i, int j) {
        Image image;
        String address;
        address = "pictures/terrainTexturs/" + terrainName + "200-200.png";
        image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        tilesImageViews[i][j] = imageView;
    }
    private void initializePane() {
        String paneDetails = GameController.getPaneDetails( mapBoundaries[0], mapBoundaries[1], mapBoundaries[2], mapBoundaries[3]);
        JSONObject pane = new JSONObject(paneDetails);
        for (int i = mapBoundaries[0]; i < mapBoundaries[1]; i++) {
            for (int j = mapBoundaries[2]; j < mapBoundaries[3]; j++) {
                JSONObject details = pane.getJSONObject("hex"+i+","+j);
                if (details.getString("state").equals("FogOfWar")) {
                    initializeFogOfWarView(i ,j);
                } else if (details.getString("state").equals("Revealed")) {
                    setTerrainViewCoordinates(i, j, tilesImageViews[i][j]);
                    initializeRevealedView(i ,j);
                } else {
                    setTerrainViewCoordinates(i, j, tilesImageViews[i][j]);
                    initializeRiverView(i ,j,details.getString("riverSides"));
                    if (details.has("featureName")) {
                        initializeFeatureView(i, j,details.getString("featureName"));
                    }
                    if (details.has("isPillaged")) {
                        makeImageView("pictures/others/pillage.png", tilesImageViews[i][j].getX() + 50,
                                tilesImageViews[i][j].getY() + 50);
                    }
                    if (details.has("military")) {
                        JSONObject unitDetails = details.getJSONObject("military");
                        initializeMilitaryUnitView(i, j,unitDetails.getString("name"),
                                unitDetails.getBoolean("isOwner"),unitDetails.getString("unitState"), 30, 20);
                    }
                    if (details.has("civilian")) {
                        JSONObject unitDetails = details.getJSONObject("civilian");
                        initializeCivilianView(i , j,unitDetails.getString("name"),
                                unitDetails.getBoolean("isOwner"),unitDetails.getString("unitState"), 100, 20);
                    }
                    if (details.has("city")) {
                        initializeCity(i, j, details.getString("city"));
                        if (details.has("cityDetails")) {
                            JSONObject cityDetails = details.getJSONObject("cityDetails");

                        }
                        if (details.has("building")) {
                            initializeBuildings(details.getString("building"),i,j);
                        }
                    }
                    if (details.has("ruins")) {
                        initializeRuins(i, j);
                    }
                    if (details.has("owner")) {
                        initializeOwnerView(i,j,details.getString("owner"));
                    }
                }
            }
        }
    }
    private void initializeOwnerView(int i, int j, String name) {
        Text text = new Text(name);
        text.setX(tilesImageViews[i][j].getX() + 120);
        text.setY(tilesImageViews[i][j].getY() + 30);
        text.setStyle("    -fx-font-size: 25;\n" +
                "    -fx-text-fill: black;");
        text.minWidth(100);
        text.minHeight(100);
        pane.getChildren().add(text);
    }
    private void initializeRuins(int i, int j) {
        ImageView ruins = new ImageView(new Image(CivilizationApplication.class.getResource("pictures/others/ruins.png").toExternalForm()));
        ruins.setFitWidth(50);
        ruins.setFitHeight(70);
        ruins.setX(tilesImageViews[i][j].getX() + 60);
        ruins.setY(tilesImageViews[i][j].getY() + 50);
        pane.getChildren().add(ruins);

    }
    private void initializeBuildings(String buildingsNames, int i, int j) {
        ArrayList<String> names = new Gson().fromJson(buildingsNames, new TypeToken<ArrayList<String>>() {}.getType());
        for (int k = 0; k < names.size(); k++) {
            String name = names.get(k);
            ImageView buildingView = getBuildingView(name);
            buildingView.setX(tilesImageViews[i][j].getX() + 25);
            buildingView.setY(tilesImageViews[i][j].getY() + 20 + (k * 50));
            pane.getChildren().add(buildingView);
        }
    }
    private static ImageView getBuildingView(String name) {
        Image image;
        String address;
        address = "pictures/building/" + name.toLowerCase() + ".png";
        image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        ImageView imageView = new ImageView(image);
        return imageView;

    }
    private void initializeCity(int i, int j, String name) {
        Text text = new Text(name);
        text.setX(tilesImageViews[i][j].getX() + 80);
        text.setY(tilesImageViews[i][j].getY() + 20);
        text.setStyle("    -fx-font-size: 15;\n" +
                "    -fx-text-fill: yellow;");
        text.minWidth(100);
        text.minHeight(100);
        String address = "pictures/city/city.png";
        Image cityImage = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        ImageView cityView = new ImageView(cityImage);
        cityView.setX(tilesImageViews[i][j].getX() + 50);
        cityView.setY(tilesImageViews[i][j].getY() + 20);
        pane.getChildren().add(cityView);
        pane.getChildren().add(text);
        //TODO
    }

    private void initializeCivilianView(int i, int j,String name ,boolean isOwner,
                                        String state, int alignX, int alignY) {
        makeView(i,j,name, state, alignX, alignY);
    }

    private void initializeFogOfWarView(int i, int j) {
        String fogOfWarAddress = "pictures/terrainTexturs/fogofwar.png";
        Image fogOfWarImage = new Image(CivilizationApplication.class.getResource(fogOfWarAddress).toExternalForm());
        javafx.scene.image.ImageView fogOfWarView = new javafx.scene.image.ImageView(fogOfWarImage);
        setTerrainViewCoordinates(i, j, fogOfWarView);
    }

    private void initializeMilitaryUnitView(int i, int j,String name , boolean isOwner,
                                            String state,int alignX, int alignY) {
        ImageView unitView = makeView(i,j,name, state, alignX, alignY);
    }

    private ImageView makeView(int i, int j,String name ,String state, int alignX, int alignY) {
        String unitAddress = "pictures/units/" +name + ".png";
        Image UnitImage = new Image(CivilizationApplication.class.getResource(unitAddress).toExternalForm());
        ImageView unitView = new ImageView(UnitImage);
        unitView.setScaleX(2);
        unitView.setScaleY(2);
        setHexDetailsViewCoordinates(i, j, unitView, alignY, alignX);
        Text text = new Text(state);
        text.setX(unitView.getX());
        text.setY(unitView.getY() - 10);
        text.setStyle("    -fx-font-size: 15;\n" +
                "    -fx-text-fill: black;");
        pane.getChildren().add(text);
        return unitView;
    }

    int bar = 50;
    private void setTerrainViewCoordinates(int i, int j, ImageView terrainView) {
        int align = (j % 2 == 0) ? 0 + bar : 100 + bar;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        terrainView.setX(j * 150);
        terrainView.setY(align + i * 200);
        pane.getChildren().add(terrainView);
    }

    private void initializeRevealedView(int i, int j) {
        String revealedAddress = "pictures/terrainTexturs/revealed.png";
        Image revealedImage = new Image(CivilizationApplication.class.getResource(revealedAddress).toExternalForm());
        javafx.scene.image.ImageView revealedView = new javafx.scene.image.ImageView(revealedImage);
        revealedView.setOpacity(0.7);
        setTerrainViewCoordinates(i, j, revealedView);
    }
    private void initializeRiverView(int x, int y,String s) {
        String[] riversSides = s.split(" ");
        for (int i = 0; i < 4; i++) {
            if (riversSides[i].equals("true")) {
                String riverAddress = "pictures/river/" + i + ".png";
                Image riverImage = new Image(CivilizationApplication.class.getResource(riverAddress).toExternalForm());
                javafx.scene.image.ImageView riverView = new javafx.scene.image.ImageView(riverImage);
                setTerrainViewCoordinates(x, y, riverView);
            }
        }
    }
    private void initializeFeatureView(int i, int j, String name) {
        Image featureImage;
        String featureAddress = "pictures/featureTexture/" + name+ ".png";
        featureImage = new Image(CivilizationApplication.class.getResource(featureAddress).toExternalForm());
        javafx.scene.image.ImageView featureView = new javafx.scene.image.ImageView(featureImage);
        setHexDetailsViewCoordinates(i, j, featureView,0, 50);
    }
    private void setHexDetailsViewCoordinates(int i, int j, ImageView featureView, int alignY, int alignX) {
        int align = (j % 2 == 0) ? 100 + bar : 200 + bar;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        featureView.setX(alignX + (j * 150));
        featureView.setY(align + i * 200 + alignY);
        pane.getChildren().add(featureView);
    }

    private ImageView makeImageView(String address, double x, double y) {
        Image image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        ImageView view = new ImageView(image);
        view.setX(x);
        view.setY(y);
        pane.getChildren().add(view);
        return view;
    }

    public void back(MouseEvent mouseEvent) {
        CivilizationApplication.changeMenu(Menus.GAMEMenu);
    }

/*    @Override
    protected void interpolate(double v) {
        initializePane();

    }*/
}
