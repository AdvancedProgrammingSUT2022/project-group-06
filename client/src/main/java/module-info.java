module project.civilization {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;
    requires gson.extras;
    requires java.desktop;
    requires javafx.swing;


    exports  clientapp;
    exports  clientapp.views;

    opens clientapp to javafx.fxml, javafx.media, javafx.base ;
    opens clientapp.fxml to javafx.media, javafx.base ;
    opens clientapp.views to javafx.fxml;

}