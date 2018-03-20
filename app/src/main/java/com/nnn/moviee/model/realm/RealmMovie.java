package com.nnn.moviee.model.realm;

import com.google.gson.annotations.SerializedName;
import com.nnn.moviee.model.Movie;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ridhaaaaazis on 26/01/18.
 */

public class RealmMovie extends RealmObject{


    @PrimaryKey
    long id;
    String title;
    @SerializedName("poster_path")
    String posterPath;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("vote_average")
    String rating;

    public RealmMovie() {
    }

    public RealmMovie(Movie movie){
        this(
                movie.getId(),
                movie.getTitle(),
                movie.getPosterPath(),
                movie.getOverview(),
                movie.getReleaseDate(),
                movie.getRating()
        );
    }

    public RealmMovie(long id, String title, String posterPath, String overview, String releaseDate, String rating) {
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
