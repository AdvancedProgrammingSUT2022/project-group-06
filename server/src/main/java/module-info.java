module com.example.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;
    requires gson.extras;
    requires java.desktop;
    requires javafx.swing;
    requires org.json;

    exports serverapp;
    exports  serverapp.views;
    exports  serverapp.models.maprelated;
    exports  serverapp.models.units;
    exports  serverapp.models.gainable;
    exports  serverapp.models.twopartyactions;

    opens  serverapp to javafx.fxml, javafx.media, javafx.base ;
    opens  serverapp.views to javafx.fxml;
    opens  serverapp.models to com.google.gson;
    opens  serverapp.models.gainable to com.google.gson;
    opens  serverapp.models.maprelated to com.google.gson;
    opens  serverapp.models.units to com.google.gson;
    opens  serverapp.models.twopartyactions to com.google.gson;
}






