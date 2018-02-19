package com.nnn.moviee.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nnn.moviee.R;
import com.nnn.moviee.model.Movie;
import com.nnn.moviee.utils.db.DB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MovieActivity extends BackButtonActivity {

    Movie movie;

    @BindView(R.id.text_title)
    TextView textTitle;

    @BindView(R.id.text_overview)
    TextView textOverview;

    @BindView(R.id.text_date)
    TextView textDate;

    @BindView(R.id.text_rating)
    TextView textRating;

    @BindView(R.id.img)
    ImageView poster;

    @BindView(R.id.img_favorite)
    ImageView favorite;

    boolean isFavorite;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        movie = new Movie(
                b.getLong("id"),
                b.getString("title"),
                b.getString("posterPath"),
                b.getString("overview"),
                b.getString("releaseDate"),
                b.getString("rating")
        );

        realm = Realm.getDefaultInstance();
        isFavorite = DB.isFavorite(realm,movie);

        updateView();
    }

    void updateView(){
        textTitle.setText(movie.getTitle());
        textOverview.setText(movie.getOverview());
        textDate.setText(movie.getReleaseDate());
        textRating.setText(movie.getRating());

        setFavoriteLayout();

        Glide.with(this).load(movie.getPosterPath500()).into(poster);
    }

    @OnClick(R.id.img_favorite)
    void clickFavorite(){
        if(isFavorite)
            DB.removeFavorite(realm,movie);
        else
            DB.addFavorite(realm,movie);

        isFavorite=!isFavorite;
        setFavoriteLayout();
    }

    void setFavoriteLayout(){
        Drawable drawableFavorite = ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_black_24dp);
        if(isFavorite)
            drawableFavorite = ContextCompat.getDrawable(this,R.drawable.ic_favorite_black_24dp);
        favorite.setImageDrawable(drawableFavorite);
    }
}
