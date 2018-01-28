package com.nnn.moviee.model.response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.nnn.moviee.model.Movie;

import java.util.List;

/**
 * Created by ridhaaaaazis on 27/01/18.
 */

public class ListMovieResponse{
    int page;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;
    JsonArray results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


    public List<Movie> getListResults(){
        Gson gson = new Gson();
        List<Movie> d = gson.fromJson(getResults(),new TypeToken<List<Movie>>(){}.getType());
        return d;
    }

    public JsonArray getResults() {
        return results;
    }

    public void setResults(JsonArray results) {
        this.results = results;
    }
}
