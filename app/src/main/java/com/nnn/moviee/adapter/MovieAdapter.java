package com.nnn.moviee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nnn.moviee.R;
import com.nnn.moviee.activity.MovieActivity;
import com.nnn.moviee.model.Movie;
import com.nnn.moviee.utils.S;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ridhaaaaazis on 26/01/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();

        View itemLayoutView = LayoutInflater.from(ctx)
                .inflate(R.layout.item_movie, null);

        ViewHolder viewHolder = new ViewHolder(ctx,itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(movieList.get(position));
    }

    @Override
    public int getItemCount() {
       return movieList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Context ctx;
        Movie movie;

        @BindView(R.id.text_title)
        public TextView textTitle;

        @BindView(R.id.text_overview)
        public TextView textOverview;

        @BindView(R.id.text_year)
        public TextView textYear;

        @BindView(R.id.text_rating)
        public TextView textRating;

        @BindView(R.id.img)
        public ImageView poster;

        public ViewHolder(Context ctx,View view) {
            super(view);
            this.ctx = ctx;
            ButterKnife.bind(this,view);
        }

        public void setData(Movie movie){
            this.movie=movie;

            textTitle.setText(movie.getTitle());
            textOverview.setText(movie.getOverview());
            textYear.setText(movie.getReleaseYear());
            textRating.setText(movie.getRating());

            Glide.with(ctx).load(movie.getPosterPath185()).into(poster);

        }

        @OnClick(R.id.btn)
        public void intentCall(){
            S.goTo(ctx, MovieActivity.class,movie);
        }

    }


}
