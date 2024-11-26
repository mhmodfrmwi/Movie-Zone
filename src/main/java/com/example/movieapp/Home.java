package com.example.movieapp;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
public class Home {
    @FXML
    private Button logoutBtn;
    @FXML
    private Button allFilms;
    @FXML
    private Button favourite;
    @FXML
    private Button trending;
    @FXML
    private Button mostRated;
    @FXML
    private Button allSeries;
    @FXML
    private Button favouriteSeries;
    @FXML
    private Button trendingSeries;
    @FXML
    private Button mostRatedSeries;
    public void AllFilmsController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("allFilms.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) allFilms.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("All Films!");
        stage.setScene(scene);
    }

    public void favouriteController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("favourite.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) favourite.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Favourite Movies!");
        stage.setScene(scene);
    }

    public void trendingController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trending.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) trending.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Trending!");
        stage.setScene(scene);
    }

    public void mostRatedController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mostRated.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) mostRated.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Most Rated Movies!");
        stage.setScene(scene);

    }
    public void AllSeriesController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("allSeries.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) allSeries.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("All Series!");
        stage.setScene(scene);
    }

    public void favouriteSeriesController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("favouriteSeries.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) favouriteSeries.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Favourite Series!");
        stage.setScene(scene);
    }

    public void trendingSeriesController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trendingSeries.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) trendingSeries.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Trending Series!");
        stage.setScene(scene);
    }

    public void mostRatedSeriesController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mostRatedSeries.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 740);
        // Get the current stage using the button's scene
        Stage stage = (Stage) mostRatedSeries.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Most Rated Series!");
        stage.setScene(scene);
    }
    public void logoutController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        // Get the current stage using the button's scene
        Stage stage = (Stage) logoutBtn.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Home!");
        stage.setScene(scene);
    }


}
