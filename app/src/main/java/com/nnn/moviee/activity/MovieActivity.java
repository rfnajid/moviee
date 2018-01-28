package com.nnn.moviee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nnn.moviee.R;
import com.nnn.moviee.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Intent i = getIntent();
        movie =(Movie) i.getSerializableExtra("extra");

        updateView();
    }

    void updateView(){
        textTitle.setText(movie.getTitle());
        textOverview.setText(movie.getOverview());
        textDate.setText(movie.getReleaseDate());
        textRating.setText(movie.getRating());

        Glide.with(this).load(movie.getPosterPath500()).into(poster);
    }
}
