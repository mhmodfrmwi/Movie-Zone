package com.example.movieapp;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;

public class loginController {

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Label errorLabel;
    @FXML
    public void registerController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        // Get the current stage using the button's scene
        Stage stage = (Stage) registerBtn.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Register");
        stage.setScene(scene);
    }


    public void loginController(ActionEvent actionEvent) throws IOException {
        String email = emailTxt.getText();
        String password = passwordTxt.getText();

        // Get the MongoDB collection
        MongoDatabase database = DB.getDatabase("MovieApp");
        MongoCollection<Document> usersCollection = database.getCollection("users");

        // Query the user
        Document query = new Document("email", email).append("password", password);
        Document user = usersCollection.find(query).first();
        if (user != null) {
            errorLabel.setText("Login successful!");
            // Proceed to next scene or functionality
        } else {
            errorLabel.setText("email or password incorrect.");
            return;
        }
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        // Get the current stage using the button's scene
        Stage stage = (Stage) loginBtn.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Home!");
        stage.setScene(scene);
    }
}