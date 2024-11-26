package com.example.movieapp;

import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

public class DB {
    // Load environment variables from the .env file
    private static final Dotenv dotenv = Dotenv.configure().directory("src/main/resources").load();

    // Fetch Mongo URI from the .env file
    private static final String CONNECTION_STRING = dotenv.get("MONGO_URI");
    private static final String DATABASE_NAME = "MovieApp";
    private static final String COLLECTION_NAME = "favourites";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Ensure connection to MongoDB is created only once
    static {
        // Create a MongoClient using the URI from the .env file
        mongoClient = MongoClients.create(CONNECTION_STRING);
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

    // Fetch the collection
    public static MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    // Get the database connection
    public static MongoDatabase getDatabase(String databaseName) {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }
        System.out.println("Connected to database: " + databaseName);
        return mongoClient.getDatabase(databaseName);
    }

    // Add a favourite to the database
    public static void addFavourite(String title, String posterUrl, String filmOverview) {
        MongoCollection<Document> collection = getCollection(COLLECTION_NAME);
        Document document = new Document("title", title)
                .append("posterUrl", posterUrl)
                .append("filmOverview", filmOverview);

        collection.insertOne(document);
        System.out.println("Added to favourites: " + title);
    }

    // Add a favourite series to the database
    public static void addFavouriteSeries(String title, String posterUrl, String seriesOverview) {
        MongoCollection<Document> collection = getCollection("favourite-series");
        Document document = new Document("title", title)
                .append("posterUrl", posterUrl)
                .append("seriesOverview", seriesOverview);

        collection.insertOne(document);
        System.out.println("Added to favourites: " + title);
    }

    // Remove a favourite from the database
    public static void removeFavourite(String title) {
        MongoCollection<Document> collection = getCollection(COLLECTION_NAME);
        Document query = new Document("title", title);

        collection.deleteOne(query);
        System.out.println("Removed from favourites: " + title);
    }

    // Remove a favourite series from the database
    public static void removeFavouriteSeries(String title) {
        MongoCollection<Document> collection = getCollection("favourite-series");
        Document query = new Document("title", title);

        collection.deleteOne(query);
        System.out.println("Removed from favourites: " + title);
    }

    // Get all favourites from the database
    public static FindIterable<Document> getFavourites() {
        MongoCollection<Document> collection = getDatabase(DATABASE_NAME).getCollection("favourites");
        return collection.find();
    }

    // Get all favourite series from the database
    public static FindIterable<Document> getFavouritesSeries() {
        MongoCollection<Document> collection = getDatabase(DATABASE_NAME).getCollection("favourite-series");
        return collection.find();
    }
}
