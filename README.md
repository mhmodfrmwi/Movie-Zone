
# MovieApp 🎬  
A JavaFX-based desktop application that allows users to browse, search, and manage their favorite movies using The Movie Database (TMDb) API and MongoDB for data storage.

---

## Features ✨
- **Browse Trending Movies**: Displays trending movies fetched dynamically from TMDb API.
- **Search Movies**: Allows users to search for movies by title.
- **Favorite Management**: Add or remove movies from a favorites list stored in MongoDB.
- **Detailed View**: Displays movie details, including title, poster, and description.

---

## Technologies Used 🛠️
- **JavaFX**: For the user interface.
- **OkHttp**: To make API requests.
- **MongoDB**: For storing favorite movies.
- **Dotenv**: To manage environment variables securely.
- **TMDb API**: To fetch movie data.

---

## Prerequisites ✅
Ensure you have the following installed:
- [JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html)  
- [MongoDB Atlas Account](https://www.mongodb.com/cloud/atlas) (or a local MongoDB instance)  
- Maven (if you are using a project build system)  

---

## Setup Guide 🚀

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/MovieApp.git
cd MovieApp
```

### Step 2: Configure Environment Variables
1. Create a `.env` file in `src/main/resources`.
2. Add the following variables:
   ```env
   MONGO_URI=mongodb+srv://<username>:<password>@cluster0.mongodb.net/MovieApp?retryWrites=true&w=majority&appName=Cluster0
   DB_NAME=MovieApp
   API_KEY=your_tmdb_api_key
   ```
   Replace `<username>`, `<password>`, and `your_tmdb_api_key` with your credentials.

### Step 3: Install Dependencies
If you are using Maven:
```bash
mvn clean install
```

### Step 4: Run the Application
Use your IDE (like IntelliJ IDEA) or the command line:
```bash
mvn javafx:run
```

---


---

## API Usage 🌐
This app integrates with the TMDb API to fetch movie data.  
**Endpoints used**:
- `https://api.themoviedb.org/3/trending/all/day`: Fetch trending movies.
- `https://api.themoviedb.org/3/search/movie`: Search for movies by title.

---

## Key Functionality 💡
1. **Fetching and Rendering Movies**  
   - Uses `OkHttpClient` to make requests to TMDb API.
   - Data is dynamically displayed in a JavaFX `GridPane`.

2. **Managing Favorites**  
   - Favorite movies are stored in a MongoDB database via the `DB` utility class.
   - Adding and removing favorites is handled with `CheckBox` events.

3. **Details View**  
   - Clicking on a movie opens a new window with detailed information.

---

---

## Troubleshooting 🛠️
- **Network Errors**:  
  Check your `.env` file to ensure the API key is correct and the `.env` file path is properly configured.

- **MongoDB Connection Issues**:  
  Verify that your MongoDB URI is correct and accessible from your network.

- **Image Loading Errors**:  
  Ensure valid URLs are returned from the TMDb API. Fallback images can be set in the code.

---

---

## Acknowledgements 🙏
- [TMDb API](https://www.themoviedb.org/documentation/api) for providing movie data.
- [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) for database services.
