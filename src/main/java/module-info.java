module com.example.javamassagaapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.slf4j;

    opens com.example.javamassagaapp.Models to javafx.base;
    opens com.example.javamassagaapp to javafx.fxml;
    exports com.example.javamassagaapp;
    exports com.example.javamassagaapp.Controllers;
    exports com.example.javamassagaapp.Views;
}