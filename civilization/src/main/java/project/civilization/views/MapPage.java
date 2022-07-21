package project.civilization.views;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import project.civilization.CivilizationApplication;
import project.civilization.controllers.*;
import project.civilization.enums.HexState;
import project.civilization.enums.Menus;
import project.civilization.enums.UnitState;
import project.civilization.models.Player;
import project.civilization.models.gainable.Building;
import project.civilization.models.gainable.Construction;
import project.civilization.models.gainable.Improvement;
import project.civilization.models.maprelated.City;
import project.civilization.models.maprelated.Hex;
import project.civilization.models.maprelated.Terrain;
import project.civilization.models.maprelated.World;
import project.civilization.models.units.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

public class MapPage {
    public  Pane pane;
    private static int[] mapBoundaries;
    private boolean wantToMove = false;
    private boolean wantToAttack = false;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button notification;
    @FXML
    private Button technologyMenu;

    public static void cityCombatMenu(City city, Player player) {
        ButtonType delete = new ButtonType("delete");
        ButtonType add = new ButtonType("add");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "city is death select a number: \n 1.delete it \n 2.add it to your territory", delete, add);
        alert.showAndWait();
        if (alert.getResult() == delete) {
            System.out.println(City.deleteCity(city));
        }else{
            System.out.println(CombatController.addCityToTerritory(city, player));
        }
    }

    public void initialize() {
        mapBoundaries = new int[]{0, 4, 0, 8};
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
        initializePane();
        Platform.runLater(() -> {
            pane.requestFocus();
        });
        handleKeyEvent();
    }


    private Node openPanel;

    // public static void removeNotificationHistory()
    // {
    //     pane.getChildren().remove(notificationHistory);
    // }
    // private static Node notificationHistory;
    // public static void closePanel(String name)
    // {
    //     FXMLLoader loader = new FXMLLoader(CivilizationApplication.class.getResource("fxml/panels/"+name+".fxml"));
    //         try {
    //             // notificationHistory=;
    //             pane.getChildren().remove((Node) loader.load());
    //         } catch (IOException e) {

    //             e.printStackTrace();
    //         }
    // }
    public  void loadPanel(String name)
    {
        FXMLLoader loader = new FXMLLoader(CivilizationApplication.class.getResource("fxml/panels/"+name+".fxml"));
        try {
            // notificationHistory=;
            if(openPanel!=null)
            {
                return;
            }
            openPanel= (Node)(loader.load());
            openPanel.setLayoutY(80);
            openPanel.setLayoutX(80);
            pane.getChildren().add(openPanel);


            Button closeButton=new Button();
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
                    openPanel=null;
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

    public void units(MouseEvent mouseEvent)
    {
        loadPanel("units-panel");
    }
    public void notification(MouseEvent mouseEvent)
    {
        loadPanel("notification-history");
    }
    public void demographic(MouseEvent mouseEvent)
    {
        loadPanel("demographic");
    }
    public void military(MouseEvent mouseEvent)
    {
        loadPanel("military-panel");
    }
    public void citiesPanel(MouseEvent mouseEvent)
    {
        loadPanel("city-panel");
    }
    public void technology(MouseEvent mouseEvent) {
        loadPanel("technology-menu-page");
    }

    Boolean ctrl=false;
    private void handleKeyEvent() {
        pane.setOnKeyPressed(e -> {
            int[] directions = new int[]{0, 0, 0, 0};
            switch (e.getCode()) {
                case RIGHT:
                    ctrl=false;
                    directions[2] = 1;
                    directions[3] = 1;
                    MoveMap(directions);
                    break;
                case LEFT:
                    ctrl=false;
                    directions[2] = -1;
                    directions[3] = -1;
                    MoveMap(directions);
                    break;
                case UP:
                    ctrl=false;
                    directions[0] = -1;
                    directions[1] = -1;
                    MoveMap(directions);
                    break;
                case DOWN:
                    ctrl=false;
                    directions[0] = 1;
                    directions[1] = 1;
                    MoveMap(directions);
                    break;
                case CONTROL:
                    ctrl=true;
                    break;
                case C:
                    if(ctrl)
                    {
                        cheatMenu();
                    }
                    break;
                default:
                    ctrl=false;
                    ctrl=false;
                    break;
            }
        });
    }

    private void cheatMenu()
    {
        VBox hBox=new VBox();
        HBox vBox=new HBox();
        TextField cheat=new TextField();
        cheat.setStyle("-fx-background-color: black; -fx-text-fill: white");
        cheat.setPrefWidth(300);
        Button button=new Button();
        Button close=new Button();
        button.setStyle("-fx-font-weight: bold; -fx-background-color: white;");
        close.setStyle("-fx-font-weight: bold; -fx-background-color: white;");
        button.setText("Ok");
        close.setText("close");
        vBox.getChildren().add(close);
        vBox.getChildren().add(button);
        hBox.getChildren().add(cheat);
        hBox.getChildren().add(vBox);
        Label error=new Label();
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
            public void handle(MouseEvent arg0)
            {
                Matcher matcher;
                String command=cheat.getText();
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
                    if (hexes[i][j].isPillaged()) {
                        makeImageView("pictures/others/pillage.png",hexes[i][j].getTerrain().getTerrainView().getX()+50,
                                hexes[i][j].getTerrain().getTerrainView().getY()+50);
                    }
                    initializeRiverView(hexes[i][j]);
                    if (hexes[i][j].getMilitaryUnit() != null) {
                        initializeMilitaryUnitView(hexes[i][j], hexes[i][j].getMilitaryUnit(), 30, 20);
                    }
                    if( hexes[i][j].getCivilianUnit() != null){
                        initializeCivilianView(hexes[i][j], hexes[i][j].getCivilianUnit(), 100, 20);

                    }
                    if (hexes[i][j].getCity() != null) {
                        initializeCity(hexes[i][j]);
                        if (hexes[i][j].getCity().getBuiltBuildings().size() != 0) {
                         initializeBuildings(hexes[i][j].getCity(), hexes[i][j]);
                        }
                    }
/*                    if (!hexes[i][j].getImprovement().isEmpty()) {
                        initializeImprovementsView(hexes[i][j]);
                    }*/
                    if (hexes[i][j].getOwner() != null) {
                        initializeOwnerView(hexes[i][j]);
                    }
                }
            }
        }
    }

    private void initializeBuildings(City city, Hex hex) {
        for (int i = 1; i <= city.getBuiltBuildings().size(); i++) {
            Building building = city.getBuiltBuildings().get(i - 1);
            ImageView buildingView = building.getBuildingView();
            buildingView.setX(hex.getTerrain().getTerrainView().getX() + 25);
            buildingView.setY(hex.getTerrain().getTerrainView().getY() + 20 + (i * 7));
            pane.getChildren().add(buildingView);
        }
    }

    private void initializeWorkingWorkers(Unit unit, ImageView unitView) {
        for (Construction imp: GameController.getCurrentPlayer().getUnfinishedProjects()) {
            if(imp instanceof Improvement && imp.getWorker().equals(unit)){
                Text text = new Text("Building "+imp.getName());
                text.setX(unitView.getX());
                text.setY(unitView.getY()-20);
                text.setStyle("    -fx-font-size: 15;\n" +
                        "    -fx-text-fill: black;");
                ProgressBar makingProgress = new ProgressBar();
                makingProgress.setLayoutX(unitView.getX()+20);
                makingProgress.setLayoutY(unitView.getY());
                makingProgress.setPrefWidth(100);
                makingProgress.setPrefHeight(20);
                unitView.setOnMouseEntered(event -> {
                    makingProgress.setProgress(imp.getLeftTurns()*1.0/((Improvement)imp).getMaxTurn());
                    pane.getChildren().add(makingProgress);
                });
                unitView.setOnMouseExited(event -> {
                    pane.getChildren().remove(makingProgress);
                });
                pane.getChildren().add(text);
            }
        }
    }

    private void initializeImprovementsView(Hex hex) {
        for (Improvement imp :hex.getImprovement()) {
            ImageView imageView = makeImageView("pictures/improvements/"+imp.getName()+".png",
                    hex.getTerrain().getTerrainView().getX()+50,
                    hex.getTerrain().getTerrainView().getX()+50);
           /* imageView.setOnMouseClicked(event -> {

            });*/
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

    private void initializeCity(Hex hex) {
        Text text = new Text(hex.getCity().getName());
        text.setX(hex.getTerrain().getTerrainView().getX() + 80);
        text.setY(hex.getTerrain().getTerrainView().getY()+20);
        text.setStyle("    -fx-font-size: 15;\n" +
                "    -fx-text-fill: yellow;");
        text.minWidth(100);
        text.minHeight(100);
        String unitAddress = "pictures/city/city.png";
        Image cityImage = new Image(CivilizationApplication.class.getResource(unitAddress).toExternalForm());
        ImageView cityView = new ImageView(cityImage);
        cityView.setX(hex.getTerrain().getTerrainView().getX() +50);
        cityView.setY(hex.getTerrain().getTerrainView().getY()+20);
        pane.getChildren().add(cityView);
        pane.getChildren().add(text);

        //TODO

    }

    private void initializeButtons() {
        pane.getChildren().add(anchorPane);
    }

    private void initializeOwnerView(Hex hex) {
        Text text = new Text(hex.getOwner().getName());
        text.setX(hex.getTerrain().getTerrainView().getX() + 120);
        text.setY(hex.getTerrain().getTerrainView().getY() + 30);
        text.setStyle("    -fx-font-size: 25;\n" +
                "    -fx-text-fill: black;");
        text.minWidth(100);
        text.minHeight(100);
        pane.getChildren().add(text);
    }

    private void initializeMilitaryUnitView(Hex hex, Unit unit, int alignX, int alignY) {
        ImageView unitView =  makeView(hex, unit, alignX, alignY);
        unitView.setOnMouseClicked(event -> {
            selectMilitaryUnit(hex);
        });
    }

    private void initializeCivilianView(Hex hex, Unit unit, int alignX, int alignY) {
        ImageView unitView =  makeView(hex, unit, alignX, alignY);
        initializeWorkingWorkers(unit, unitView);
        unitView.setOnMouseClicked(event -> {
            selectCivilianUnit(hex);
            // TODO: 7/15/2022 : check is ordered
            if(unit instanceof Worker /*&& !unit.isOrdered()*/){
                loadPanel("worker-action-panel");
            }
        });
    }

    private ImageView makeView(Hex hex, Unit unit, int alignX, int alignY) {
        String unitAddress = "pictures/units/" + unit.getName() + ".png";
        Image UnitImage = new Image(CivilizationApplication.class.getResource(unitAddress).toExternalForm());
        ImageView unitView = new ImageView(UnitImage);
        unitView.setScaleX(2);
        unitView.setScaleY(2);
        setHexDetailsViewCoordinates(hex.getX(), hex.getY(), unitView ,alignY , alignX);
        Text text = new Text(unit.getState().getCharacter());
        text.setX(unitView.getX());
        text.setY(unitView.getY()-10);
        text.setStyle("    -fx-font-size: 15;\n" +
                "    -fx-text-fill: black;");
        pane.getChildren().add(text);
        return unitView;
    }
    private void selectCivilianUnit(Hex hex) {
        if (hex.getCivilianUnit().getOwner() == GameController.getCurrentPlayer()){
            showInformationOfUnit(hex.getCivilianUnit());
            UnitController.setSelectedUnit(hex.getCivilianUnit());
            VBox vBox = new VBox();
            ImageView delete = createImageView("pictures/unitActionsIcon/delete.png");
            vBox.getChildren().add(delete);
            delete.setOnMouseClicked(event -> {
                UnitController.deleteUnitAction(UnitController.getSelectedUnit());
                resetPane();
            });
            ImageView foundCity = createImageView("pictures/unitActionsIcon/foundCity.png");
            vBox.getChildren().add(foundCity);
            foundCity.setOnMouseClicked(event -> {
                String res = CityController.buildCity(
                        UnitController.getSelectedUnit().getOwner().getName()+UnitController.getSelectedUnit().getOwner().getCities().size());
                if(!res.equals("new city created successfully")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,res);
                    alert.showAndWait();
                    resetPane();
                }

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
    private void selectMilitaryUnit(Hex hex) {
        if (hex.getMilitaryUnit().getOwner() == GameController.getCurrentPlayer()){
            showInformationOfUnit(hex.getMilitaryUnit());
            UnitController.setSelectedUnit(hex.getMilitaryUnit());
            VBox vBox = new VBox();
            ImageView delete = createImageView("pictures/unitActionsIcon/delete.png");
            vBox.getChildren().add(delete);
            delete.setOnMouseClicked(event -> {
                UnitController.deleteUnitAction(UnitController.getSelectedUnit());
                resetPane();
            });
            ImageView pillage = createImageView("pictures/unitActionsIcon/pillage.png");
            vBox.getChildren().add(pillage);
            pillage.setOnMouseClicked(event -> {
                UnitController.pillage();
                resetPane();
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
                System.out.println(UnitController.fortify());
                resetPane();
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
                UnitController.garrison();
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
                if(!res.equals("siege is ready for the next turn")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                }else resetPane();
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

    private void showInformationOfUnit(Unit unit) {
        VBox vBox = new VBox();
        Popup popup = new Popup();
        Label label = new Label("name: " + unit.getName());
        addVboxToPopup(label, vBox);
        Label label1 = new Label("state: " + unit.getState().getCharacter());
        addVboxToPopup(label1, vBox);
        Label label2 = new Label("MP: " + String.valueOf(unit.getMP()));
        addVboxToPopup(label2, vBox);
        Label label3 = new Label("CS: " + String.valueOf(unit.getCombatStrength()));
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



    private void initializeRevealedView(Hex hex) {
        String revealedAddress = "pictures/terrainTexturs/revealed.png";
        Image revealedImage = new Image(CivilizationApplication.class.getResource(revealedAddress).toExternalForm());
        javafx.scene.image.ImageView revealedView = new javafx.scene.image.ImageView(revealedImage);
        revealedView.setOpacity(0.7);
        setTerrainViewCoordinates(hex.getX(), hex.getY(), revealedView);
        handelSelectHexEvent(hex, revealedView);
    }

    private void initializeFogOfWarView(Hex hex) {
        String fogOfWarAddress = "pictures/terrainTexturs/fogofwar.png";
        Image fogOfWarImage = new Image(CivilizationApplication.class.getResource(fogOfWarAddress).toExternalForm());
        javafx.scene.image.ImageView fogOfWarView = new javafx.scene.image.ImageView(fogOfWarImage);
        setTerrainViewCoordinates(hex.getX(), hex.getY(), fogOfWarView);
        handelSelectHexEvent(hex, fogOfWarView);
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
    int bar=50;
    private void setTerrainViewCoordinates(int i, int j, ImageView terrainView) {
        int align = (j % 2 == 0) ? 0+bar : 100+bar;
        j -= mapBoundaries[2];
        i -= mapBoundaries[0];
        terrainView.setX(j * 150);
        terrainView.setY(align + i * 200);
        pane.getChildren().add(terrainView);
    }

    private void setHexDetailsViewCoordinates(int i, int j, ImageView featureView, int alignY,int alignX) {
        int align = (j % 2 == 0) ? 100+bar : 200+bar;
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
        handelSelectHexEvent(hex, imageView);
    }

    private void handelSelectHexEvent(Hex hex, ImageView imageView) {
        imageView.setOnMouseClicked(event -> {
            if(wantToMove){
                String res = UnitController.startMovement(hex.getX(),hex.getY());
                if(!res.equals("unit is on its way")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, res);
                    alert.showAndWait();
                }
                wantToMove = false;
            }else if(wantToAttack){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, CombatController.attackUnit(hex.getX(),hex.getY()));
                alert.showAndWait();
                resetPane();
                wantToAttack = false;
            } else {
                showHexDetails(hex);
            }
        });
    }

    private void showHexDetails(Hex hex) {
        VBox vBox = new VBox();
        Label label = new Label(hex.getTerrain().getName()+
                " ,food:" + hex.getTerrain().getFood()+
                " ,product:" + hex.getTerrain().getProduction()+
                " ,gold:" +  hex.getTerrain().getGold()+
                " ,CMP:" + hex.getTerrain().getCombatModifiersPercentage()+
                " ,MP:" +hex.getTerrain().getMovePoint());
        Popup popup = new Popup();
        addVboxToPopup(label, vBox);
        if (hex.getResource() != null) {
            Label label1 = new Label(hex.getResource().getName() +
                    " ,food:" + hex.getResource().getFood() +
                    " ,product:" + hex.getResource().getProduction() +
                    " ,gold:" + hex.getResource().getGold());
            Image image = new Image(CivilizationApplication.class.getResource("pictures/resources/" + hex.getResource().getName() + ".png").toExternalForm());
            ImageView imageView = new ImageView(image);
            label1.setGraphic(imageView);
            addVboxToPopup(label1, vBox);
        }
        if (hex.getFeature() != null) {
            Label label1 = new Label(hex.getFeature().getName() +
                    " ,food:" + hex.getFeature().getFood() +
                    " ,product:" + hex.getFeature().getProduction() +
                    " ,gold:" + hex.getFeature().getGold() +
                    " ,CMP:" + hex.getFeature().getCombatModifiersPercentage() +
                    " ,MP:" + hex.getFeature().getMovePoint());
            addVboxToPopup(label1, vBox);
        }
        if (hex.getImprovement() != null) {
            StringBuilder improvementsName= new StringBuilder();
            for (Improvement improvement: hex.getImprovement()) {
                improvementsName.append(improvement.getName()+" ");
            }
            Label label1 = new Label(improvementsName.toString());
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
            GameController.checkTimeVariantProcesses();
            //GameController.getAvailableWorkOfActiveWorkers
            if (GameController.getTurn() == 1) GameController.startGame();
            Platform.runLater(() -> {
                pane.requestFocus();
            });
            resetPane();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, outPut);
            alert.showAndWait();
        }
    }

    public void pause(MouseEvent mouseEvent) {
        loadPanel("pause-panel");
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