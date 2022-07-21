module project.civilization {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;
    requires gson.extras;
    requires java.desktop;
    requires javafx.swing;
    requires commons.beanutils;

    exports  project.civilization;
    exports  project.civilization.views;
    exports  project.civilization.models.maprelated;
    exports  project.civilization.models.units;
    exports  project.civilization.models.gainable;
    exports  project.civilization.models.twopartyactions;
    exports  project.civilization.enums;

    opens  project.civilization to javafx.fxml, javafx.media, javafx.base;
    opens  project.civilization.fxml to javafx.media, javafx.base ;
    opens  project.civilization.views to javafx.fxml;
    opens  project.civilization.models to com.google.gson;
    opens  project.civilization.models.gainable to com.google.gson;
    opens  project.civilization.models.maprelated to com.google.gson;
    opens  project.civilization.models.units to com.google.gson, commons.beanutils;
    opens  project.civilization.models.twopartyactions to com.google.gson;

}