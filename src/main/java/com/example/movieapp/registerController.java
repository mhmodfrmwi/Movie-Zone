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

public class registerController {
    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Label errorLabel;

    public void registerController(ActionEvent actionEvent) throws IOException {
        // Get the MongoDB collection
        MongoDatabase database = DB.getDatabase("MovieApp");
        MongoCollection<Document> usersCollection = database.getCollection("users");

        String username = usernameTxt.getText();
        String email = emailTxt.getText();
        String password = passwordTxt.getText();
        if (username=="")
        {
            errorLabel.setText("Please enter a valid username");
            return;
        }
        else if (email=="")
        {
            errorLabel.setText("Please enter a valid email");
            return;
        }
        else if (password=="")
        {
            errorLabel.setText("Please enter a valid password");
            return;
        }
        Document query = new Document("username", username);
        Document user = usersCollection.find(query).first();
        if (user!=null){
            errorLabel.setText("Username already exists");
            return;
        }
        query=new Document("email", email);
        user = usersCollection.find(query).first();
        if (user!=null){
            errorLabel.setText("Email already exists");
            return;
        }
        // Insert the user data into the MongoDB collection
        Document newUser = new Document("username", username)
                .append("password", password).append("email", email);
        usersCollection.insertOne(newUser);
        System.out.println("User registered successfully!");

        // Load register.fxml (optional - keep logic for changing the scene)
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        Stage stage = (Stage) registerBtn.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
    }

    public void loginController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);

        // Get the current stage using the button's scene
        Stage stage = (Stage) loginBtn.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Login!");
        stage.setScene(scene);
    }
}
