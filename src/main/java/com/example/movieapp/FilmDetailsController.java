package com.example.movieapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FilmDetailsController {

    @FXML
    private ImageView posterImage;
    @FXML
    private Label titleLabel;
    @FXML
    private TextArea descriptionText;

    private Stage stage;

    // Initialize film details
    public void setFilmDetails(String title, String posterUrl, String description) {
        titleLabel.setText(title);
        posterImage.setImage(new Image(posterUrl, true));
        descriptionText.setText(description != null ? description : "No description available.");
        descriptionText.setStyle("-fx-font-size: 14px;-fx-font-weight: bold;-fx-font-style: italic;-fx-background-color: #000;");
        descriptionText.setWrapText(true);
        descriptionText.setEditable(false);
        descriptionText.setFocusTraversable(false);
    }

    @FXML
    private void handleBack() {
        if (stage != null) {
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
