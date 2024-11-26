module com.example.movieapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires jdk.httpserver;
    requires okhttp3;
    requires org.json;
    requires java.dotenv;


    opens com.example.movieapp to javafx.fxml;
    exports com.example.movieapp;
}