package com.example.movieapp.model;

public class Movie {
    private String id;
    private String Title;
    private String Year;
    private String imdbID;
    private String Type;
    private String Poster;
    private String Plot;

    // 🔥 Newly added fields:
    private String imdbRating;
    private String Runtime;
    private String Genre;

    public Movie() {

    }


    public String getId() { return id; }
    public String getTitle() { return Title; }
    public String getYear() { return Year; }
    public String getImdbID() { return imdbID; }
    public String getType() { return Type; }
    public String getPoster() { return Poster; }
    public String getPlot() { return Plot; }
    public String getImdbRating() { return imdbRating; }
    public String getRuntime() { return Runtime; }
    public String getGenre() { return Genre; }


    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.Title = title; }
    public void setYear(String year) { this.Year = year; }
    public void setImdbID(String imdbID) { this.imdbID = imdbID; }
    public void setType(String type) { this.Type = type; }
    public void setPoster(String poster) { this.Poster = poster; }
    public void setPlot(String plot) { this.Plot = plot; }
    public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }
    public void setRuntime(String runtime) { this.Runtime = runtime; }
    public void setGenre(String genre) { this.Genre = genre; }
}
