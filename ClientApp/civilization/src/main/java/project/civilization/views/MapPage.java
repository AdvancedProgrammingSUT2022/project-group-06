package project.civilization.views;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import org.json.JSONObject;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.*;
import project.civilization.models.Player;
import project.civilization.models.gainable.Construction;
import project.civilization.models.gainable.Improvement;
import project.civilization.models.maprelated.City;
import project.civilization.models.units.Unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapPage {
    public Pane pane;
    private static int[] mapBoundaries;
    private boolean wantToMove = false;
    private boolean wantToAttack = false;
    private boolean cityWantToattack = false;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button notification;
    @FXML
    private Button technologyMenu;
    int FOGRemover = 0;
    public static boolean technologyPass = false;

    public static void cityCombatMenu(String cityName, String playerName) {
        ButtonType delete = new ButtonType("delete");
        ButtonType add = new ButtonType("add");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "city is death select a number: \n 1.delete it \n 2.add it to your territory", delete, add);
        alert.showAndWait();
        if (alert.getResult() == delete) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION,City.deleteCity(cityName));
            alert2.showAndWait();
        } else {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION,CombatController.addCityToTerritory(cityName, playerName));
            alert2.showAndWait();
        }
    }


    public void activateRuin(int i,JSONObject jsonObject) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch (i) {
            case 1:
                alert.setContentText("Congratulations!! your population increased 1 person.");
                break;
            case 2:
                alert.setContentText("Congratulations!! you found a box of gold with " + jsonObject.getInt("amount2") + " coins :)");
                break;
            case 3:
                alert.setContentText("Congratulations!! you just got a free Settler Unit :)");
                break;
            case 4:
                alert.setContentText("Congratulations!! you now have the abilitiy to remove fog of war from 3 desired tiles.\nA new button will appear on the top of your screen.\nchoose a tile, click the button, and enjoy");
                createFogOfWarRemoverButton();
                FOGRemover = 3;
                break;
            case 5:
                alert.setContentText("congratulations!! you can unlock a technology now!! :)");
                technologyPass = true;
                //free technology
                break;

        }
        alert.showAndWait();
    }


    public void createFogOfWarRemoverButton() {
        Button remover = new Button();
        remover.setText("FOG Remover");
        anchorPane.getChildren().add(remover);
        remover.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String res = GameController.handelFogOfWarRemoverButton();
                //todo:
                if(res.equals("choose a tile first")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,res );
                    alert.showAndWait();
                    return;
                }
                if (res.equals("it's not wise to waste this token :)")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                    return;
                }
                FOGRemover--;
                if (FOGRemover == 0) {
                    anchorPane.getChildren().add(remover);
                }
                initializePane();
            }

        });

        remover.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                remover.setStyle("-fx-background-color:white; -fx-text-fill: black");

            }

        });
        remover.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                remover.setStyle("-fx-background-color:white; -fx-text-fill: black");

            }

        });

    }

    ImageView[][] tilesImageViews;
    int hexInHeight;
    int hexInWidth;
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
        Platform.runLater(() -> {
            pane.requestFocus();
        });
        handleKeyEvent();
    }

    private Node openPanel;
    public void loadPanel(String name) {
        FXMLLoader loader = new FXMLLoader(CivilizationApplication.class.getResource("fxml/panels/" + name + ".fxml"));
        try {
            // notificationHistory=;
            if (openPanel != null) {
                return;
            }
            openPanel = (Node) (loader.load());
            openPanel.setLayoutY(80);
            openPanel.setLayoutX(80);
            pane.getChildren().add(openPanel);


            Button closeButton = new Button();
            closeButton.setLayoutX(80);
            closeButton.setLayoutY(80);
            closeButton.setPrefSize(60, 15);
            closeButton.setText("Close");
            closeButton.setStyle("-fx-background-color:black; -fx-text-fill: goldenrod");

            pane.getChildren().add(closeButton);
            closeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    closeButton.setStyle("-fx-background-color:#3a3a3a; -fx-text-fill: goldenrod");

                }

            });
            closeButton.setOnMouseExited(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    closeButton.setStyle("-fx-background-color:black; -fx-text-fill: goldenrod");

                }

            });
            closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent arg0) {
                    pane.getChildren().remove(openPanel);
                    pane.getChildren().remove(closeButton);
                    openPanel = null;
                    Platform.runLater(() -> {
                        pane.requestFocus();
                    });
                    resetPane();
                }

            });
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


    public void economic(MouseEvent mouseEvent) {
        loadPanel("economic-page");
    }

    public void units(MouseEvent mouseEvent) {
        loadPanel("units-panel");
    }

    public void notification(MouseEvent mouseEvent) {
        loadPanel("notification-history");
    }

    public void demographic(MouseEvent mouseEvent) {
        loadPanel("demographic");
    }

    public void military(MouseEvent mouseEvent) {
        loadPanel("military-panel");
    }

    public void citiesPanel(MouseEvent mouseEvent) {
        loadPanel("city-panel");
    }

    public void technology(MouseEvent mouseEvent) {
        loadPanel("technology-menu-page");
    }

    Boolean ctrl = false;

    private void handleKeyEvent() {
        pane.setOnKeyPressed(e -> {
            int[] directions = new int[]{0, 0, 0, 0};
            switch (e.getCode()) {
                case RIGHT:
                    ctrl = false;
                    directions[2] = 1;
                    directions[3] = 1;
                    MoveMap(directions);
                    break;
                case LEFT:
                    ctrl = false;
                    directions[2] = -1;
                    directions[3] = -1;
                    MoveMap(directions);
                    break;
                case UP:
                    ctrl = false;
                    directions[0] = -1;
                    directions[1] = -1;
                    MoveMap(directions);
                    break;
                case DOWN:
                    ctrl = false;
                    directions[0] = 1;
                    directions[1] = 1;
                    MoveMap(directions);
                    break;
                case CONTROL:
                    ctrl = true;
                    break;
                case C:
                    if (ctrl) {
                        cheatMenu();
                    }
                    break;
                default:
                    ctrl = false;
                    ctrl = false;
                    break;
            }
        });
    }

    private void cheatMenu() {
        VBox hBox = new VBox();
        HBox vBox = new HBox();
        TextField cheat = new TextField();
        cheat.setStyle("-fx-background-color: black; -fx-text-fill: white");
        cheat.setPrefWidth(300);
        Button button = new Button();
        Button close = new Button();
        button.setStyle("-fx-font-weight: bold; -fx-background-color: white;");
        close.setStyle("-fx-font-weight: bold; -fx-background-color: white;");
        button.setText("Ok");
        close.setText("close");
        vBox.getChildren().add(close);
        vBox.getChildren().add(button);
        hBox.getChildren().add(cheat);
        hBox.getChildren().add(vBox);
        Label error = new Label();
        error.setStyle("-fx-text-fill:red");
        hBox.getChildren().add(error);
        pane.getChildren().add(hBox);
        close.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                pane.getChildren().remove(hBox);
                Platform.runLater(() -> {
                    pane.requestFocus();
                });
                resetPane();
            }

        });

        button.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                Matcher matcher;
                String command = cheat.getText();
                if ((matcher = getMatcher("increase (--gold|-g) (?<amount>\\d+)", command)) != null) {
                    error.setText(GameController.cheatGold(Integer.parseInt(matcher.group("amount"))));
                } else if ((matcher = getMatcher("increase (--cityproduction|-cp) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null) {
                    error.setText(GameController.cheatCityProduction(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
                } else if ((matcher = getMatcher("increase (--movepoint|-mp) (?<amount>\\d+) (?<x>\\d+) (?<y>\\d+) (?<type>[a-zA-Z]+)", command)) != null) {
                    error.setText(GameController.cheatMP((Integer.parseInt(matcher.group("amount"))), Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")), matcher.group("type")));
                } else if ((matcher = getMatcher("increase (--happiness|-h) (?<amount>\\d+)", command)) != null) {
                    error.setText(GameController.cheatHappiness((Integer.parseInt(matcher.group("amount")))));
                } else if ((matcher = getMatcher("increase (--population|-p) (?<amount>\\d+)", command)) != null) {
                    error.setText(GameController.cheatPopulation((Integer.parseInt(matcher.group("amount")))));
                } else if ((matcher = getMatcher("increase (--score|-s) (?<amount>\\d+)", command)) != null) {
                    error.setText(GameController.cheatScore((Integer.parseInt(matcher.group("amount")))));
                } else if ((matcher = getMatcher("increase (--production|-pr) (?<amount>\\d+)", command)) != null) {
                    error.setText(GameController.cheatProduction((Integer.parseInt(matcher.group("amount")))));
                } else if ((matcher = getMatcher("increase (--cityMeleeCombatStrength|-cmcs) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null) {
                    error.setText(GameController.cheatMeleeCombatStrength(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
                } else if ((matcher = getMatcher("increase (--cityRangedCombatStrength|-crcs) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null) {
                    error.setText(GameController.cheatRangedCombatStrength(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
                } else if ((matcher = getMatcher("increase (--cityhitpoint|-chp) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null) {
                    error.setText(GameController.cheatCityHitPoint(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
                } else if ((matcher = getMatcher("increase (--cityfood|-cf) (?<amount>\\d+) (?<cityname>[a-zA-Z_ ]+)", command)) != null) {
                    error.setText(GameController.cheatCityFood(Integer.parseInt(matcher.group("amount")), matcher.group("cityname")));
                } else if ((matcher = getMatcher("increase (--trophy|-t) (?<amount>\\d+)", command)) != null) {
                    error.setText(GameController.cheatTrophy(Integer.parseInt(matcher.group("amount"))));
                }
            }
        });

    }

    private static Matcher getMatcher(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.find()) return matcher;
        return null;
    }


    private void MoveMap(int[] directions) {
        if (mapBoundaries[0] + directions[0] < 0 || mapBoundaries[1] + directions[1] > hexInHeight ||
                mapBoundaries[2] + directions[2] < 0 || mapBoundaries[3] + directions[3] > hexInWidth) {
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

    private void initializeRuins(int i, int j) {
        ImageView ruins = new ImageView(new Image(CivilizationApplication.class.getResource("pictures/others/ruins.png").toExternalForm()));
        ruins.setFitWidth(50);
        ruins.setFitHeight(70);
        ruins.setX(tilesImageViews[i][j].getX() + 60);
        ruins.setY(tilesImageViews[i][j].getY() + 50);
        pane.getChildren().add(ruins);

    }


    private void initializeWorkingWorkers(int i, int j ,ImageView unitView) {
        JSONObject jsonObject = new JSONObject(GameController.getImprovementNameOfWoorker(i,j));
        if(jsonObject.has("impName")){
            String impName = jsonObject.getString("impName");
            double progress = jsonObject.getDouble("progress");
            Text text = new Text("Building " + impName);
            text.setX(unitView.getX());
            text.setY(unitView.getY() - 20);
            text.setStyle("    -fx-font-size: 15;\n" +
                    "    -fx-text-fill: black;");
            ProgressBar makingProgress = new ProgressBar();
            makingProgress.setLayoutX(unitView.getX() + 20);
            makingProgress.setLayoutY(unitView.getY());
            makingProgress.setPrefWidth(100);
            makingProgress.setPrefHeight(20);
            unitView.setOnMouseEntered(event -> {
                makingProgress.setProgress(progress);
                pane.getChildren().add(makingProgress);
            });
            unitView.setOnMouseExited(event -> {
                pane.getChildren().remove(makingProgress);
            });
            pane.getChildren().add(text);
        }
    }


    private ImageView makeImageView(String address, double x, double y) {
        Image image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        ImageView view = new ImageView(image);
        view.setX(x);
        view.setY(y);
        pane.getChildren().add(view);
        return view;
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
        cityView.setOnMouseClicked(event1 -> {
            GameController.setSelectedCityByName(name);
            VBox vBox = new VBox();
            ImageView delete = createImageView("pictures/city/rangeAttack.png");
            vBox.getChildren().add(delete);
            delete.setOnMouseClicked(event2 -> {
                cityWantToattack = true;
            });
            vBox.setLayoutY(100);
            pane.getChildren().add(vBox);
        });
        cityView.setX(tilesImageViews[i][j].getX() + 50);
        cityView.setY(tilesImageViews[i][j].getY() + 20);
        pane.getChildren().add(cityView);
        pane.getChildren().add(text);
        //TODO
    }

    private void initializeButtons() {
        pane.getChildren().add(anchorPane);
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

    private void initializeMilitaryUnitView(int i, int j,String name , boolean isOwner,
                                            String state,int alignX, int alignY) {
        ImageView unitView = makeView(i,j,name, state, alignX, alignY);
        unitView.setOnMouseClicked(event -> {
            selectMilitaryUnit(i, j, isOwner);
        });
    }

    private void initializeCivilianView(int i, int j,String name ,boolean isOwner,
                                        String state, int alignX, int alignY) {
        ImageView unitView = makeView(i,j,name, state, alignX, alignY);
        initializeWorkingWorkers(i, j, unitView);
        unitView.setOnMouseClicked(event -> {
            selectCivilianUnit(i, j, isOwner);
            // TODO: 7/15/2022 : check is ordered
            if (isOwner&&Objects.equals(name, "Worker") /*&& !unit.isOrdered()*/) {
                loadPanel("worker-action-panel");
            }
        });
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

    private void selectCivilianUnit(int i, int j, boolean isOwner) {
        if (isOwner) {
            UnitController.setSelectedUnit( "civilian", i,  j);
            showInformationOfUnit( i, j,"civilian");
            VBox vBox = new VBox();
            ImageView delete = createImageView("pictures/unitActionsIcon/delete.png");
            vBox.getChildren().add(delete);
            delete.setOnMouseClicked(event -> {
                UnitController.deleteUnitAction();
                resetPane();
            });
            ImageView foundCity = createImageView("pictures/unitActionsIcon/foundCity.png");
            vBox.getChildren().add(foundCity);
            foundCity.setOnMouseClicked(event -> {
                String res = CityController.buildCity();
                if (!res.equals("new city created successfully")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                } else resetPane();
            });
            ImageView moveView = createImageView("pictures/unitActionsIcon/move.png");
            vBox.getChildren().add(moveView);
            moveView.setOnMouseClicked(event -> {
                wantToMove = true;
            });
            vBox.setLayoutY(100);
            pane.getChildren().add(vBox);
        }
    }

    private void selectMilitaryUnit(int i, int j,boolean isOwner) {
        if (isOwner) {
            UnitController.setSelectedUnit("military",i, j);
            showInformationOfUnit(i, j, "military");
            VBox vBox = new VBox();
            ImageView delete = createImageView("pictures/unitActionsIcon/delete.png");
            vBox.getChildren().add(delete);
            delete.setOnMouseClicked(event -> {
                UnitController.deleteUnitAction();
                resetPane();
            });
            ImageView pillage = createImageView("pictures/unitActionsIcon/pillage.png");
            vBox.getChildren().add(pillage);
            pillage.setOnMouseClicked(event -> {
                String res = UnitController.pillage();
                if(!res.equals("pillaged successfully")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                }else resetPane();
            });
            ImageView alertView = createImageView("pictures/unitActionsIcon/alert.png");
            vBox.getChildren().add(alertView);
            alertView.setOnMouseClicked(event -> {
                UnitController.alert();
                resetPane();
            });
            ImageView sleepView = createImageView("pictures/unitActionsIcon/sleep.png");
            vBox.getChildren().add(sleepView);
            sleepView.setOnMouseClicked(event -> {
                UnitController.sleepUnit();
                resetPane();
            });
            ImageView fortifyView = createImageView("pictures/unitActionsIcon/fortify.png");
            vBox.getChildren().add(fortifyView);
            fortifyView.setOnMouseClicked(event -> {
                String res = (UnitController.fortify());
                if(!res.equals("fortified successfully")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                }else resetPane();
            });
            ImageView wake = createImageView("pictures/unitActionsIcon/wake.png");
            vBox.getChildren().add(wake);
            wake.setOnMouseClicked(event -> {
                UnitController.wakeUpUnit();
                resetPane();
            });
            ImageView moveView = createImageView("pictures/unitActionsIcon/move.png");
            vBox.getChildren().add(moveView);
            moveView.setOnMouseClicked(event -> {
                wantToMove = true;
            });

            ImageView garrison = createImageView("pictures/unitActionsIcon/garrison.png");
            vBox.getChildren().add(garrison);
            garrison.setOnMouseClicked(event -> {
                System.out.println(UnitController.garrison());
                resetPane();
            });

            ImageView attack = createImageView("pictures/unitActionsIcon/attack.png");
            vBox.getChildren().add(attack);
            attack.setOnMouseClicked(event -> {
                wantToAttack = true;
            });

            ImageView rangedAttackView = createImageView("pictures/unitActionsIcon/rangeAttack.png");
            vBox.getChildren().add(rangedAttackView);
            rangedAttackView.setOnMouseClicked(event -> {
                wantToAttack = true;
            });
            ImageView setUpRangeAttackView = createImageView("pictures/unitActionsIcon/setUpForRangeAttack.png");
            vBox.getChildren().add(setUpRangeAttackView);
            setUpRangeAttackView.setOnMouseClicked(event -> {
                String res = UnitController.setUpSiegeForRangeAttack();
                if (!res.equals("siege is ready for the next turn")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                } else resetPane();
            });
            ImageView fortifyUntilHeal = createImageView("pictures/unitActionsIcon/fortifyUntilHeal.png");
            vBox.getChildren().add(fortifyUntilHeal);
            fortifyUntilHeal.setOnMouseClicked(event -> {
                UnitController.fortifyUtilHeal();
                resetPane();
            });
/*             ImageView cancel = createImageView("pictures/unitActionsIcon/cancel.png");
             vBox.getChildren().add(cancel);
             cancel.setOnMouseClicked(event -> {
                 resetPane();
             });*/
            vBox.setLayoutY(100);
            pane.getChildren().add(vBox);
        }
    }

    private void showInformationOfUnit(int i, int j, String type) {
        VBox vBox = new VBox();
        Popup popup = new Popup();
        JSONObject jsonObject = new JSONObject(GameController.getUnitInformation(i ,j, type));
        Label label = new Label("name: " + jsonObject.getString("name"));
        addVboxToPopup(label, vBox);
            Label label1 = new Label("state: " + jsonObject.getString("state"));
        addVboxToPopup(label1, vBox);
        Label label2 = new Label("MP: " + jsonObject.getString("mp"));
        addVboxToPopup(label2, vBox);
        Label label3 = new Label("CS: " +jsonObject.getString("cp"));
        addVboxToPopup(label3, vBox);
        popup.getContent().add(vBox);
        popup.setY(10);
        popup.setAutoHide(true);
        if (!popup.isShowing())
            popup.show(CivilizationApplication.stages);
    }

    private Button makeUnitActionButton(VBox vBox, String text) {
        Button button = new Button(text);
        button.setLayoutX(0);
        vBox.getChildren().add(button);
        return button;
    }

    private ImageView createImageView(String address) {
        Image image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        return new ImageView(image);
    }


    private void initializeRevealedView(int i, int j) {
        String revealedAddress = "pictures/terrainTexturs/revealed.png";
        Image revealedImage = new Image(CivilizationApplication.class.getResource(revealedAddress).toExternalForm());
        javafx.scene.image.ImageView revealedView = new javafx.scene.image.ImageView(revealedImage);
        revealedView.setOpacity(0.7);
        setTerrainViewCoordinates(i, j, revealedView);
        handelSelectHexEvent(revealedView, i, j);
    }

    private void initializeFogOfWarView(int i, int j) {
        String fogOfWarAddress = "pictures/terrainTexturs/fogofwar.png";
        Image fogOfWarImage = new Image(CivilizationApplication.class.getResource(fogOfWarAddress).toExternalForm());
        javafx.scene.image.ImageView fogOfWarView = new javafx.scene.image.ImageView(fogOfWarImage);
        setTerrainViewCoordinates(i, j, fogOfWarView);
        handelSelectHexEvent(fogOfWarView, i, j);
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

    int bar = 50;

    private void setTerrainViewCoordinates(int i, int j, ImageView terrainView) {
        int align = (j % 2 == 0) ? 0 + bar : 100 + bar;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        terrainView.setX(j * 150);
        terrainView.setY(align + i * 200);
        pane.getChildren().add(terrainView);
    }

    private void setHexDetailsViewCoordinates(int i, int j, ImageView featureView, int alignY, int alignX) {
        int align = (j % 2 == 0) ? 100 + bar : 200 + bar;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        featureView.setX(alignX + (j * 150));
        featureView.setY(align + i * 200 + alignY);
        pane.getChildren().add(featureView);
    }

    private void initializeFeatureView(int i, int j, String name) {
        Image featureImage;
        String featureAddress = "pictures/featureTexture/" + name+ ".png";
        featureImage = new Image(CivilizationApplication.class.getResource(featureAddress).toExternalForm());
        javafx.scene.image.ImageView featureView = new javafx.scene.image.ImageView(featureImage);
        setHexDetailsViewCoordinates(i, j, featureView,0, 50);
    }

    private void initializeTerrainView(String terrainName, int i, int j) {
        Image image;
        String address;
        address = "pictures/terrainTexturs/" + terrainName + "200-200.png";
        image = new Image(CivilizationApplication.class.getResource(address).toExternalForm());
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        tilesImageViews[i][j] = imageView;
        handelSelectHexEvent(imageView, i, j);
    }

    private void handelSelectHexEvent( ImageView imageView, int i, int j) {
        imageView.setOnMouseClicked(event -> {
            if (wantToMove) {
                String res = UnitController.startMovement(i, j);
                System.out.println(res);
                if (!res.equals("unit is on its way")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                }else resetPane();
                wantToMove = false;
            } else if (wantToAttack) {
                JSONObject res =  new JSONObject(CombatController.attackUnit(i, j));
                String alertresult =(res.has("result")) ?res.getString("result"): res.toString();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, alertresult);
                alert.showAndWait();
                if(res.has("combatType"))attackResultView(res);
                resetPane();
                wantToAttack = false;
            } else if (cityWantToattack) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, CombatController.attackCity(i, j));
                alert.showAndWait();
                resetPane();
                cityWantToattack = false;
            } else {
                showHexDetails(i, j);
                GameController.setSelectedHex(i, j);
                createUnit();
            }
        });
    }
    private void createUnit()
    {
        Button button=new Button();
        button.setText("Create Unit");
        button.setLayoutY(600);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                pane.getChildren().remove(button);
                showBuyUnitOption();
            }
            
        });
        pane.getChildren().add(button);
        
    }
    private void showBuyUnitOption()
    {
        Button button=new Button();
        button.setText("Buy Unit");
        button.setLayoutY(550);
        TextField textField=new TextField();
        textField.setPromptText("enter unit name");
        textField.setLayoutY(450);
        Button exit=new Button();
        exit.setText("exit");
        exit.setLayoutY(600);
        Button makeUnit=new Button();
        makeUnit.setText("Make Unit");
        makeUnit.setLayoutY(500);


        makeUnit.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                
                Alert alert;
                if(textField.getText().equals(""))
                {
                    alert=new Alert(AlertType.INFORMATION,"Enter Unit's Name");
                    alert.showAndWait();
                    return;
                }
                alert=new Alert(AlertType.INFORMATION,GameController.unitMake(textField.getText()));
                alert.showAndWait();
                resetPane();
            }
            
        });
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Platform.runLater(() -> {
                    pane.requestFocus();
                });
                resetPane(); 
            }
            
        });
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Alert alert;
                if(textField.getText().equals(""))
                {
                    alert=new Alert(AlertType.INFORMATION,"Enter Unit's Name");
                    alert.showAndWait();
                    return;
                }
                alert=new Alert(AlertType.INFORMATION,GameController.unitBuy(textField.getText()));
                alert.showAndWait();
                resetPane();

            }
            
        });
        pane.getChildren().add(button);
        pane.getChildren().add(textField);
        pane.getChildren().add(exit);
    }

    private void attackResultView(JSONObject res) {
        if(res.getString("combatType").equals("meleeToCity")){
            if(res.getString("result").equals("in melee to city combat city is death")){
                cityCombatMenu( res.getString("cityName"), res.getString("playerName"));
            }else if(res.getString("result").equals("in melee to city combat unit and city are death")){
                cityCombatMenu( res.getString("cityName"), res.getString("playerName"));
            }
        } else if(res.getString("combatType").equals("meleeToCity")){
        }
    }

    private void showHexDetails(int i, int j) {
        JSONObject obj = new JSONObject(GameController.getHexDetails(i ,j));
        String terrainDetails = obj.getString("terrainDetails");
        String featureDetails = obj.getString("featureDetails");
        String improvementDetails = obj.getString("improvementDetails");
        String resourceDetails = obj.getString("resourceDetails");
        VBox vBox = new VBox();
        Label label = new Label(terrainDetails);
        Popup popup = new Popup();
        addVboxToPopup(label, vBox);
        if (!Objects.equals(resourceDetails, "")) {
            Label label1 = new Label( resourceDetails);
            Image image = new Image(CivilizationApplication.class.getResource("pictures/resources/" + obj.getString("resourceName") + ".png").toExternalForm());
            ImageView imageView = new ImageView(image);
            label1.setGraphic(imageView);
            addVboxToPopup(label1, vBox);
        }
        if (!Objects.equals(featureDetails, "")) {
            Label label1 = new Label(featureDetails);
            addVboxToPopup(label1, vBox);
        }
        if (!Objects.equals(improvementDetails, "")) {
            Label label1 = new Label(improvementDetails);
            addVboxToPopup(label1, vBox);
        }
        if(obj.has("railroad")){
            Label label1 = new Label("has railRoad");
            addVboxToPopup(label1, vBox);
        }
        if(obj.has("road")){
            Label label1 = new Label("has road");
            addVboxToPopup(label1, vBox);
        }
        popup.getContent().add(vBox);
        popup.setY(10);
        popup.setAutoHide(true);
        if (!popup.isShowing())
            popup.show(CivilizationApplication.stages);
    }

    private void addVboxToPopup(Label label, VBox VBox) {
        label.setStyle(" -fx-background-color: #6e663f;" +
                "    -fx-font-size: 12;\n" +
                "    -fx-text-fill: black;");
        VBox.getChildren().add(label);
    }

    public void nextTurn(MouseEvent mouseEvent) {
        String outPut = GameController.changeTurn();
        if (outPut.startsWith("Turn changed successfully")) {
            Platform.runLater(() -> {
                pane.requestFocus();
            });
            resetPane();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, outPut);
            alert.showAndWait();
        }
    }

    public void pause(MouseEvent mouseEvent) {
        loadPanel("pause-panel");
    }

    public void changeTurnForOthers() {
        resetPane();
    }
}