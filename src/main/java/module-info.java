module com.example.tegneprogram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tegneprogram to javafx.fxml;
    exports com.example.tegneprogram;
    exports com.example.tegneprogram.JavaOblig;
    opens com.example.tegneprogram.JavaOblig to javafx.fxml;
}