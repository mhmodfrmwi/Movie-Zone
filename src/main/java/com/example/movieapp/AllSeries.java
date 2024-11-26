package com.example.movieapp;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class AllSeries {
    @FXML
    public ScrollPane seriesScrollPane;
    @FXML
    private Button backBtn;

    @FXML
    private GridPane seriesGrid; // GridPane to arrange films in rows and columns.
    @FXML
    private TextField searchField;
    private static final Dotenv dotenv = Dotenv.configure().directory("src/main/resources").load();
    private static final String API_KEY = dotenv.get("API_KEY");
    @FXML
    private Button searchButton;
    public void initialize() {
        // Fetch and render films when the page is loaded.
        fetchAndRenderSeries();
    }
    @FXML
    public void searchSeries() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            fetchAndRenderSeries(); // Fetch popular series if search field is empty
        } else {
            searchAndRenderSeries(query);
        }
    }

    private void searchAndRenderSeries(String query) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/search/tv?query=" + query + "&language=en-US&page=1&include_adult=false")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseData = response.body().string();
                renderSeries(responseData);
            } else {
                System.err.println("Search request failed with status: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fetchAndRenderSeries() {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&language=en-US&page=1&sort_by=popularity.desc")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                renderSeries(responseData);
            } else {
                System.err.println("Request failed with status: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderSeries(String responseData) {
        JSONObject jsonResponse = new JSONObject(responseData);
        JSONArray series = jsonResponse.getJSONArray("results");

        int row = 0;
        int col = 0;

        for (int i = 0; i < series.length(); i++) {
            JSONObject film = series.getJSONObject(i);

            // Extract film details
            String title = film.optString("title", film.optString("name", "Unknown Title"));
            String posterPath = film.optString("poster_path", "");
            String posterUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
            String seriesOverview = film.optString("overview", "");
            // Create a card for the film
            VBox filmCard = createSeriesCard(title, posterUrl, seriesOverview);

            // Add the card to the GridPane
            seriesGrid.add(filmCard, col, row);

            col++;
            if (col == 2) { // Move to the next row after 2 cards
                col = 0;
                row++;
            }
        }
    }

    private VBox createSeriesCard(String title, String posterUrl, String seriesOverview) {
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
        Text overviewText = new Text(seriesOverview);
        overviewText.setStyle("-fx-fill: white; -fx-font-size: 10; -fx-font-weight: bold;");
        // Checkbox for "Add to Favourite"
        CheckBox addToFavourite = new CheckBox("Add to Favourite");
        addToFavourite.setStyle("-fx-text-fill: white;");
        addToFavourite.setOnAction(event -> {
            if (addToFavourite.isSelected()) {
                DB.addFavouriteSeries(title, posterUrl, seriesOverview); // Add to MongoDB
            } else {
                DB.removeFavouriteSeries(title); // Remove from MongoDB
            }
        });

        card.getChildren().addAll(poster, titleText, addToFavourite);
        card.setOnMouseClicked(event -> openSeriesDetails(title, posterUrl, seriesOverview));
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

    private void openSeriesDetails(String title, String posterUrl, String filmOverview) {
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
