package project.civilization.views;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import project.civilization.controllers.GameController;

import javafx.scene.shape.Line;
import java.util.ArrayList;

public class TechGraphMenu {

    @FXML
    private AnchorPane anchorPane;

    private ArrayList<HBox> techBoxes;
    private ArrayList<Line> lines;

    int screenWidth = 600;
    int screenHeight = 400;

    public void initialize() {
        initializeTree();
    }

    private void initializeLines() {
    }

    private void initializeLineBlock1() {
        Line line1 = new Line();
        line1.setStartX();
        line1.setStartY();
        line1.setEndX();
        line1.setEndY();
    }

    private void initializeTree() {
        AnchorPane demoPane = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        techBoxes = new ArrayList<>();

        initializeBox1(demoPane);
        initializeBox2(demoPane);
        initializeBox3(demoPane);
        initializeBox4(demoPane);
        initializeBox5(demoPane);
        initializeBox6(demoPane);
        initializeBox7(demoPane);
        initializeBox8(demoPane);
        initializeBox9(demoPane);
        initializeBox10(demoPane);
        initializeBox11(demoPane);
        initializeBox12(demoPane);
        initializeBox13(demoPane);
        initializeBox14(demoPane);
        initializeBox15(demoPane);
        setBorderForAllBoxes();
        scrollPane.setMaxHeight(screenHeight);
        scrollPane.setPrefWidth(screenWidth);
        scrollPane.setLayoutY(27);
        scrollPane.setContent(demoPane);
        scrollPane.setPannable(true);
        anchorPane.getChildren().add(scrollPane);
    }

    private void initializeBox1(AnchorPane demoPane) {
        VBox box1 = new VBox();
        HBox hBox = new HBox();
        Label label = new Label("Agriculture");
        box1.setLayoutX(10);
        box1.setLayoutY(180);
        box1.setPrefWidth(80);
        hBox.getChildren().add(label);
        hBox.setAlignment(Pos.CENTER);
        box1.getChildren().add(hBox);
        techBoxes.add(hBox);
        demoPane.getChildren().add(box1);
    }

    private void initializeBox2(AnchorPane demoPane) {
        VBox box2 = new VBox();
        box2.setSpacing(70);
        box2.setLayoutX(150);
        box2.setLayoutY(50);
        box2.setPrefWidth(80);
        HBox pottery = new HBox();
        pottery.getChildren().add(new Label("Pottery"));
        HBox animalHusbandry = new HBox(new Label("Animal Husbandry"));
        HBox archery = new HBox(new Label("Archery"));
        HBox mining = new HBox(new Label("Mining"));
        box2.getChildren().addAll(pottery, animalHusbandry, archery, mining);
        techBoxes.add(pottery);
        techBoxes.add(animalHusbandry);
        techBoxes.add(archery);
        techBoxes.add(mining);
        demoPane.getChildren().add(box2);
    }

    private void initializeBox3(AnchorPane demoPane) {
        VBox box3 = new VBox();
        box3.setSpacing(30);
        box3.setLayoutX(300);
        box3.setPrefWidth(80);
        box3.setLayoutY(40);
        HBox sailing = new HBox(new Label("Sailing"));
        HBox calender = new HBox(new Label("Calender"));
        HBox Writing = new HBox(new Label("Writing"));
        HBox trapping = new HBox(new Label("Trapping"));
        HBox theWheel = new HBox(new Label("The Wheel"));
        HBox masonry = new HBox(new Label("Masonry"));
        HBox bronzeWorking = new HBox(new Label("Bronze Working"));
        box3.getChildren().addAll(sailing, calender, Writing, trapping, theWheel, masonry, bronzeWorking);
        techBoxes.add(sailing);
        techBoxes.add(calender);
        techBoxes.add(Writing);
        techBoxes.add(trapping);
        techBoxes.add(theWheel);
        techBoxes.add(masonry);
        techBoxes.add(bronzeWorking);
        techBoxes.add(bronzeWorking);
        demoPane.getChildren().add(box3);
    }

    private void initializeBox4(AnchorPane demoPane) {
        VBox box4 = new VBox();
        box4.setSpacing(60);
        box4.setLayoutY(50);
        box4.setLayoutX(450);
        box4.setPrefWidth(80);
        HBox optics = new HBox(new Label("Optics"));
        HBox horsebackRiding = new HBox(new Label("HorseBack Riding"));
        HBox mathematics = new HBox(new Label("Mathematics"));
        HBox construction = new HBox(new Label("Construction"));
        techBoxes.add(optics);
        techBoxes.add(horsebackRiding);
        techBoxes.add(mathematics);
        techBoxes.add(construction);
        box4.getChildren().addAll(optics, horsebackRiding, mathematics, construction);
        demoPane.getChildren().add(box4);
    }

    private void initializeBox5(AnchorPane demoPane) {
        VBox box5 = new VBox();
        box5.setSpacing(50);
        box5.setLayoutY(40);
        box5.setLayoutX(600);
        box5.setPrefWidth(80);
        HBox philosophy = new HBox(new Label("Philosophy"));
        HBox dramaAndPoetry = new HBox(new Label("Drama and Poetry"));
        HBox currency = new HBox(new Label("Currency"));
        HBox engineering = new HBox(new Label("Engineering"));
        HBox ironWorking = new HBox(new Label("Iron working"));
        techBoxes.add(philosophy);
        techBoxes.add(dramaAndPoetry);
        techBoxes.add(currency);
        techBoxes.add(engineering);
        techBoxes.add(ironWorking);
        box5.getChildren().addAll(philosophy, dramaAndPoetry, currency, engineering, ironWorking);
        demoPane.getChildren().add(box5);
    }

    private void initializeBox6(AnchorPane demoPane) {
        VBox box6 = new VBox();
        box6.setSpacing(60);
        box6.setLayoutY(50);
        box6.setLayoutX(750);
        box6.setPrefWidth(80);
        HBox theology = new HBox(new Label("Theology"));
        HBox civilService = new HBox(new Label("Civil service"));
        HBox guilds = new HBox(new Label("Guilds"));
        HBox metalCasting = new HBox(new Label("Metal Casting"));
        techBoxes.add(theology);
        techBoxes.add(civilService);
        techBoxes.add(guilds);
        techBoxes.add(metalCasting);
        box6.getChildren().addAll(theology, civilService, guilds, metalCasting);
        demoPane.getChildren().add(box6);
    }

    private void initializeBox7(AnchorPane demoPane) {
        VBox box7 = new VBox();
        box7.setSpacing(40);
        box7.setLayoutY(40);
        box7.setLayoutX(900);
        box7.setPrefWidth(80);
        HBox compass = new HBox(new Label("Compass"));
        HBox education = new HBox(new Label("Education"));
        HBox chivalry = new HBox(new Label("Chivalry"));
        HBox machinery = new HBox(new Label("Machinery"));
        HBox physics = new HBox(new Label("Physics"));
        HBox steel = new HBox(new Label("Steel"));
        techBoxes.add(compass);
        techBoxes.add(education);
        techBoxes.add(chivalry);
        techBoxes.add(machinery);
        techBoxes.add(physics);
        techBoxes.add(steel);
        box7.getChildren().addAll(compass, education, chivalry, machinery, physics, steel);
        demoPane.getChildren().add(box7);
    }

    private void initializeBox8(AnchorPane demoPane) {
        VBox box8 = new VBox();
        box8.setSpacing(50);
        box8.setLayoutY(50);
        box8.setLayoutX(1050);
        box8.setPrefWidth(80);
        HBox astronomy = new HBox(new Label("Astronomy"));
        HBox acoustics = new HBox(new Label("Acoustics"));
        HBox banking = new HBox(new Label("Banking"));
        HBox printingPress = new HBox(new Label("Printing Press"));
        HBox gunpowder = new HBox(new Label("Gunpowder"));
        techBoxes.add(astronomy);
        techBoxes.add(acoustics);
        techBoxes.add(banking);
        techBoxes.add(printingPress);
        techBoxes.add(gunpowder);
        box8.getChildren().addAll(astronomy, acoustics, banking, printingPress, gunpowder);
        demoPane.getChildren().add(box8);
    }

    private void initializeBox9(AnchorPane demoPane) {
        VBox box9 = new VBox();
        box9.setSpacing(50);
        box9.setLayoutY(50);
        box9.setLayoutX(1200);
        box9.setPrefWidth(80);
        HBox navigation = new HBox(new Label("Navigation"));
        HBox architecture = new HBox(new Label("Architecture"));
        HBox economics = new HBox(new Label("Economics"));
        HBox metallurgy = new HBox(new Label("metallurgy"));
        HBox chemistry = new HBox(new Label("Chemistry"));
        techBoxes.add(navigation);
        techBoxes.add(architecture);
        techBoxes.add(economics);
        techBoxes.add(metallurgy);
        techBoxes.add(chemistry);
        box9.getChildren().addAll(navigation, architecture, economics, metallurgy, chemistry);
        demoPane.getChildren().add(box9);
    }

    private void initializeBox10(AnchorPane demoPane) {
        VBox box10 = new VBox();
        box10.setSpacing(40);
        box10.setLayoutY(40);
        box10.setLayoutX(1350);
        box10.setPrefWidth(80);
        HBox archeology = new HBox(new Label("Archeology"));
        HBox scientificTheory = new HBox(new Label("Scientific Theory"));
        HBox industrialization = new HBox(new Label("Industrialization"));
        HBox riffling = new HBox(new Label("Riffling"));
        HBox militaryScience = new HBox(new Label("militaryScience"));
        HBox fertilizer = new HBox(new Label("Fertilizer"));
        techBoxes.add(archeology);
        techBoxes.add(scientificTheory);
        techBoxes.add(industrialization);
        techBoxes.add(riffling);
        techBoxes.add(militaryScience);
        techBoxes.add(fertilizer);
        box10.getChildren().addAll(archeology, scientificTheory, industrialization, riffling, militaryScience, fertilizer);
        demoPane.getChildren().add(box10);
    }

    private void initializeBox11(AnchorPane demoPane) {
        VBox box11 = new VBox();
        box11.setSpacing(60);
        box11.setLayoutY(50);
        box11.setLayoutX(1500);
        box11.setPrefWidth(80);
        HBox biology = new HBox(new Label("Biology"));
        HBox electricity = new HBox(new Label("Electricity"));
        HBox streamPower = new HBox(new Label("Stream Power"));
        HBox dynamite = new HBox(new Label("Dynamite"));
        techBoxes.add(biology);
        techBoxes.add(electricity);
        techBoxes.add(streamPower);
        techBoxes.add(dynamite);
        box11.getChildren().addAll(biology, electricity, streamPower, dynamite);
        demoPane.getChildren().add(box11);
    }

    private void initializeBox12(AnchorPane demoPane) {
        VBox box12 = new VBox();
        box12.setSpacing(50);
        box12.setLayoutY(50);
        box12.setLayoutX(1650);
        box12.setPrefWidth(80);
        HBox refrigeration = new HBox(new Label("Refrigeration"));
        HBox radio = new HBox(new Label("Radio"));
        HBox replaceableParts = new HBox(new Label("Replaceable parts"));
        HBox flight = new HBox(new Label("Flight"));
        HBox railroad = new HBox(new Label("Railroad"));
        techBoxes.add(refrigeration);
        techBoxes.add(radio);
        techBoxes.add(replaceableParts);
        techBoxes.add(flight);
        techBoxes.add(railroad);
        box12.getChildren().addAll(refrigeration, radio, replaceableParts, flight, railroad);
        demoPane.getChildren().add(box12);
    }

    private void initializeBox13(AnchorPane demoPane) {
        VBox box13 = new VBox();
        box13.setSpacing(60);
        box13.setLayoutY(60);
        box13.setLayoutX(1800);
        box13.setPrefWidth(80);
        HBox plastics = new HBox(new Label("Plastics"));
        HBox electronics = new HBox(new Label("Electronics"));
        HBox ballistics = new HBox(new Label("Ballistics"));
        HBox combustion = new HBox(new Label("Combustion"));
        techBoxes.add(plastics);
        techBoxes.add(electronics);
        techBoxes.add(ballistics);
        techBoxes.add(combustion);
        box13.getChildren().addAll(plastics, electronics, ballistics, combustion);
        demoPane.getChildren().add(box13);
    }

    private void initializeBox14(AnchorPane demoPane) {
        VBox box14 = new VBox();
        box14.setSpacing(60);
        box14.setLayoutY(60);
        box14.setLayoutX(1950);
        box14.setPrefWidth(80);
        HBox penicillin = new HBox(new Label("Penicillin"));
        HBox atomicTheory = new HBox(new Label("Atomic Theory"));
        HBox radar = new HBox(new Label("Radar"));
        HBox combinedArms = new HBox(new Label("Combined Arms"));
        techBoxes.add(penicillin);
        techBoxes.add(atomicTheory);
        techBoxes.add(radar);
        techBoxes.add(combinedArms);
        box14.getChildren().addAll(penicillin, atomicTheory, radar, combinedArms);
        demoPane.getChildren().add(box14);
    }

    private void initializeBox15(AnchorPane demoPane) {
        VBox box15 = new VBox();
        box15.setSpacing(60);
        box15.setLayoutY(60);
        box15.setLayoutX(2100);
        box15.setPrefWidth(80);
        HBox ecology = new HBox(new Label("Ecology"));
        HBox nuclearFission = new HBox(new Label("Nuclear Fission"));
        HBox rocketry = new HBox(new Label("Rocketry"));
        HBox computers = new HBox(new Label("Computers"));
        techBoxes.add(ecology);
        techBoxes.add(nuclearFission);
        techBoxes.add(rocketry);
        techBoxes.add(computers);
        box15.getChildren().addAll(ecology, nuclearFission, rocketry, computers);
        demoPane.getChildren().add(box15);
    }

    private void setBorderForAllBoxes() {
        for (HBox hBox : techBoxes) {
            setBorderColor(hBox);
        }
    }

    private void setBorderColor(HBox hBox) {
        String name = hBox.getChildren().get(0).toString();
        if (GameController.isAchieved(name) == null) {
            hBox.setStyle("-fx-border-color:#424242; -fx-border-width:2px");
        } else if (Boolean.TRUE.equals(GameController.isAchieved(name))) {
            hBox.setStyle("-fx-border-color:#111111; -fx-border-width:2px");
        }
        hBox.setStyle("-fx-border-color:#555555; -fx-border-width:2px");
    }
}
