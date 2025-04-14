# 🎬 Movie Catalogue System

A Spring Boot web application that lets users explore trending movies using the TMDb API, view detailed movie information, and manage a list of favorite movies using an in-memory database.

---

## ✅ Implemented Features

### 🔹 Home Page
- Displays a list of popular movies from TMDb.
- Each movie shows:
  - Poster
  - Title
  - Overview
  - Link to details page
  - Button to add to favorites

### 🔹 Movie Detail View
- Displays:
  - Movie Title
  - Overview
  - Poster
  - Release Date
  - Rating
- Add to or remove from favorites
- Shows whether a movie is already favorited

### 🔹 Search Feature
- Users can search movies by title
- Results are shown on the same movie list view

### 🔹 Favorites Page
- Displays all movies marked as favorites
- Includes button to remove any movie from favorites

### 🔹 Favorite Management
- Favorites are stored in an H2 in-memory database
- Duplicate entries are prevented
- Favorites persist for the session while the app is running

### 🔹 Error Handling
- Graceful error redirection to a user-friendly error page
- Server-side exception logging
