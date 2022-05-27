module project.civilization {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;

    opens  project.civilization to javafx.fxml, javafx.media, javafx.base ;
    opens  project.civilization.fxml to javafx.media, javafx.base ;
    exports  project.civilization;
    exports  project.civilization.views;
    opens project.civilization.views to javafx.fxml;
    opens  project.civilization.models to com.google.gson;
}