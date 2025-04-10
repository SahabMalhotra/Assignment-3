package com.example.movieapp.model;

import java.util.List;

public class SearchResponse {
    private List<Movie> Search;
    private String Response;

    public List<Movie> getSearch() {
        return Search;
    }

    public String getResponse() {
        return Response;
    }
}
