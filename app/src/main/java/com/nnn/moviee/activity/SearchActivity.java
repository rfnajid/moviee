package com.nnn.moviee.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nnn.moviee.R;
import com.nnn.moviee.adapter.MovieAdapter;
import com.nnn.moviee.api.MovieApi;
import com.nnn.moviee.model.Movie;
import com.nnn.moviee.model.response.ListMovieResponse;
import com.nnn.moviee.utils.S;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    List<Movie> movieList=new ArrayList<>();
    MovieAdapter movieAdapter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    Retrofit retrofit;
    MovieApi movieApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(llm);

        retrofit=S.getRetrofit();
        movieApi=retrofit.create(MovieApi.class);

        getPopularMovies();
        //searchMovies("Bad");


    }

    void initDummyData(){
        movieList.clear();
        for(int i=0;i<30;i++) {
            movieList.add(new Movie(
                    1,
                    "It "+i,
                    "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg",
                    "In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.",
                    "2017-09-05",
                    "7.1"
            ));
        }

        movieAdapter.notifyDataSetChanged();
    }

    void getPopularMovies(){

        Call<ListMovieResponse> call=movieApi.getPopularMovies();
        call.enqueue(new Callback<ListMovieResponse>() {
            @Override
            public void onResponse(Call<ListMovieResponse> call, Response<ListMovieResponse> response) {
                if(response.code()==200){
                    ListMovieResponse movieResponse = response.body();
                    movieList.clear();
                    movieList.addAll(movieResponse.getListResults());
                    movieAdapter.notifyDataSetChanged();
                }else{
                    S.toast(getApplicationContext(),response.code()+" : "+response.message());
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ListMovieResponse> call, Throwable t) {
                S.toast(getApplicationContext(),"Unexpected Error");
                finish();

            }
        });
    }

    void searchMovies(String query){

        Call<ListMovieResponse> call=movieApi.getSearch(query);
        call.enqueue(new Callback<ListMovieResponse>() {
            @Override
            public void onResponse(Call<ListMovieResponse> call, Response<ListMovieResponse> response) {
                if(response.code()==200){
                    ListMovieResponse movieResponse = response.body();
                    movieList.clear();
                    movieList.addAll(movieResponse.getListResults());
                    movieAdapter.notifyDataSetChanged();
                }else{
                    S.toast(getApplicationContext(),response.code()+" : "+response.message());
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ListMovieResponse> call, Throwable t) {
                S.toast(getApplicationContext(),"Unexpected Error");
                finish();

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);


        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);

        SearchView searchView =(SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchMovies(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
