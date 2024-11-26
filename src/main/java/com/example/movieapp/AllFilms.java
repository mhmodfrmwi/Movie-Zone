package com.example.movieapp;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class AllFilms {
    @FXML
    private TextField searchBar;

    @FXML
    private Button backBtn;

    private final OkHttpClient client = new OkHttpClient();

    @FXML
    private GridPane filmsGrid; // GridPane to arrange films in rows and columns.
    private static final Dotenv dotenv = Dotenv.configure().directory("src/main/resources").load();
    private static final String API_KEY = dotenv.get("API_KEY");
    public void initialize() {
        // Fetch and render films when the page is loaded.
        fetchAndRenderFilms();
    }
    @FXML
    public void handleSearch(ActionEvent actionEvent) {
        String query = searchBar.getText().trim();
        if (!query.isEmpty()) {
            searchMovies(query);
        }
    }

    public void searchMovies(String query) {
        // Clear the grid before rendering new results
        filmsGrid.getChildren().clear();

        String url = "https://api.themoviedb.org/3/search/movie?query=" + query + "&language=en-US";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                JSONObject jsonObject = new JSONObject(responseData);
                JSONArray results = jsonObject.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movie = results.getJSONObject(i);
                    String title = movie.getString("title");
                    String posterPath = movie.optString("poster_path", "");
                    String posterUrl = "https://image.tmdb.org/t/p/w500" + posterPath;

                    // Create a card for each movie
                    VBox card = createFilmCard(title, posterUrl,"");
                    filmsGrid.add(card, i % 2, i / 2); // 2 movies per row
                }
            } else {
                System.err.println("Failed to fetch search results: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchAndRenderFilms() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer "+API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                renderFilms(responseData);
            } else {
                System.err.println("Request failed with status: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderFilms(String responseData) {
        JSONObject jsonResponse = new JSONObject(responseData);
        JSONArray films = jsonResponse.getJSONArray("results");

        int row = 0;
        int col = 0;

        for (int i = 0; i < films.length(); i++) {
            JSONObject film = films.getJSONObject(i);

            // Extract film details
            String title = film.optString("title", film.optString("name", "Unknown Title"));
            String posterPath = film.optString("poster_path", "");
            String posterUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
            String filmOverview = film.optString("overview", "");
            // Create a card for the film
            VBox filmCard = createFilmCard(title, posterUrl, filmOverview);

            // Add the card to the GridPane
            filmsGrid.add(filmCard, col, row);

            col++;
            if (col == 2) { // Move to the next row after 2 cards
                col = 0;
                row++;
            }
        }
    }

    private VBox createFilmCard(String title, String posterUrl, String filmOverview) {
        VBox card = new VBox();
        card.setStyle("-fx-background-color: #333; -fx-padding: 10; -fx-spacing: 5; -fx-border-color: #c44018; -fx-border-width: 2;");
        card.setPrefWidth(300);

        // ImageView for the poster
        ImageView poster = new ImageView();
        poster.setFitWidth(150);
        poster.setFitHeight(225);
        try {
            Image image = new Image(posterUrl, true);
            poster.setImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load image: " + posterUrl);
        }

        // Text for the title
        Text titleText = new Text(title);
        titleText.setStyle("-fx-fill: white; -fx-font-size: 14; -fx-font-weight: bold;");
        Text overviewText = new Text(filmOverview);
        overviewText.setStyle("-fx-fill: white; -fx-font-size: 10; -fx-font-weight: bold;");
        // Checkbox for "Add to Favourite"
        CheckBox addToFavourite = new CheckBox("Add to Favourite");
        addToFavourite.setStyle("-fx-text-fill: white;");
        addToFavourite.setOnAction(event -> {
            if (addToFavourite.isSelected()) {
                DB.addFavourite(title, posterUrl, filmOverview); // Add to MongoDB
            } else {
                DB.removeFavourite(title); // Remove from MongoDB
            }
        });

        card.getChildren().addAll(poster, titleText, addToFavourite);
        card.setOnMouseClicked(event -> openFilmDetails(title, posterUrl, filmOverview));
        return card;
    }

    public void backController(ActionEvent actionEvent) throws IOException {
        // Load the FXML for the register page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        // Get the current stage using the button's scene
        Stage stage = (Stage) backBtn.getScene().getWindow();

        // Set the new scene to the current stage
        stage.setTitle("Home!");
        stage.setScene(scene);
    }

    private void openFilmDetails(String title, String posterUrl, String filmOverview) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("filmDetails.fxml"));
            Stage detailsStage = new Stage();
            detailsStage.setScene(new Scene(loader.load()));

            // Get the controller and set film details
            FilmDetailsController controller = loader.getController();
            controller.setFilmDetails(title, posterUrl, filmOverview);
            controller.setStage(detailsStage);

            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
