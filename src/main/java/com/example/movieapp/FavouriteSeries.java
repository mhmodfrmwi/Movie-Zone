package com.example.movieapp;

import com.mongodb.client.FindIterable;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;

public class FavouriteSeries {

    @FXML
    private Button backBtn;

    @FXML
    private ScrollPane filmsScrollPane;

    @FXML
    private GridPane seriesGrid;

    @FXML
    public void initialize() {
        loadFavoriteFilms();
    }

    private void loadFavoriteFilms() {
        // Clear existing content
        seriesGrid.getChildren().clear();

        // Fetch favorite films from the database
        FindIterable<Document> favourites = DB.getFavouritesSeries();

        int row = 0;
        int column = 0;

        for (Document favourite : favourites) {
            String title = favourite.getString("title");
            String posterUrl = favourite.getString("posterUrl");

            // Create a film card with a checkbox
            VBox card = createFilmCard(title, posterUrl);

            // Add card to the grid
            seriesGrid.add(card, column, row);

            // Adjust column and row for the grid layout
            column++;
            if (column == 2) {
                column = 0;
                row++;
            }
        }
    }

    private VBox createFilmCard(String title, String posterUrl) {
        VBox card = new VBox();
        card.setStyle("-fx-background-color: #333; -fx-padding: 10; -fx-spacing: 5; -fx-border-color: #c44018; -fx-border-width: 2;");
        card.setPrefWidth(300);

        // Poster image
        javafx.scene.image.ImageView poster = new javafx.scene.image.ImageView();
        poster.setFitWidth(150);
        poster.setFitHeight(225);
        try {
            javafx.scene.image.Image image = new javafx.scene.image.Image(posterUrl, true);
            poster.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + posterUrl);
        }

        // Film title
        javafx.scene.text.Text titleText = new javafx.scene.text.Text(title);
        titleText.setStyle("-fx-fill: white; -fx-font-size: 14; -fx-font-weight: bold;");

        // Checkbox for "Add to Favorite"
        CheckBox favouriteCheckBox = new CheckBox("Favourite");
        favouriteCheckBox.setSelected(true); // Initially checked
        favouriteCheckBox.setStyle("-fx-text-fill: white;");
        favouriteCheckBox.setOnAction(event -> {
            if (!favouriteCheckBox.isSelected()) {
                DB.removeFavouriteSeries(title); // Remove from database
                loadFavoriteFilms(); // Reload the list
            }
        });

        // Add elements to the card
        card.getChildren().addAll(poster, titleText, favouriteCheckBox);

        return card;
    }

    @FXML
    private void backController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 720);
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
