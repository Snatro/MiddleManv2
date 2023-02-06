module com.example.middle_man {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.middle_man to javafx.fxml;
    exports com.example.middle_man;
}