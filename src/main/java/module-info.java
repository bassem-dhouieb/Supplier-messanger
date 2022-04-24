module com.example.suppliermessanger {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.suppliermessanger to javafx.fxml;
    exports com.example.suppliermessanger;
}