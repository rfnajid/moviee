package com.nnn.moviee.model;

import com.google.gson.annotations.SerializedName;
import com.nnn.moviee.model.realm.RealmMovie;

import java.io.Serializable;

/**
 * Created by ridhaaaaazis on 19/03/18.
 */

public class Movie implements Serializable{
    long id;
    String title;
    @SerializedName("poster_path")
    String posterPath;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("vote_average")
    String rating;

    public Movie() {
    }

    public Movie(RealmMovie realmMovie){
        this(
                realmMovie.getId(),
                realmMovie.getTitle(),
                realmMovie.getPosterPath(),
                realmMovie.getOverview(),
                realmMovie.getReleaseDate(),
                realmMovie.getRating()
        );
    }

    public Movie(long id, String title, String posterPath, String overview, String releaseDate, String rating) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath185(){
        return "http://image.tmdb.org/t/p/w185"+getPosterPath();
    }

    public String getPosterPath500(){
        return "http://image.tmdb.org/t/p/w500"+getPosterPath();
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseYear(){
        return releaseDate.substring(0,4);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
