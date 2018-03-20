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
import com.nnn.moviee.model.realm.RealmMovie;
import com.nnn.moviee.utils.S;
import com.nnn.moviee.utils.db.DB;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MovieActivity extends BackButtonActivity {

    RealmMovie realmMovie;

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
        realmMovie = new RealmMovie((Movie)i.getSerializableExtra("extra"));

        realm = Realm.getDefaultInstance();
        isFavorite = DB.isFavorite(realm, realmMovie);

        updateView();
    }

    void updateView(){
        textTitle.setText(realmMovie.getTitle());
        textOverview.setText(realmMovie.getOverview());
        textDate.setText(realmMovie.getReleaseDate());
        textRating.setText(realmMovie.getRating());

        setFavoriteLayout();

        Glide.with(this).load(realmMovie.getPosterPath500()).into(poster);
    }

    @OnClick(R.id.img_favorite)
    void clickFavorite(){
        if(isFavorite)
            DB.removeFavorite(realm, realmMovie);
        else
            DB.addFavorite(realm, realmMovie);

        isFavorite=!isFavorite;
        setFavoriteLayout();

        S.log("FAV SIZE : "+DB.getFavorites(realm).size());

        S.updateWidget(getApplication());
    }

    void setFavoriteLayout(){
        Drawable drawableFavorite = ContextCompat.getDrawable(this,R.drawable.ic_favorite_border_black_24dp);
        if(isFavorite)
            drawableFavorite = ContextCompat.getDrawable(this,R.drawable.ic_favorite_black_24dp);
        favorite.setImageDrawable(drawableFavorite);
    }


}
