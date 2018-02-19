package com.nnn.moviee.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nnn.moviee.R;
import com.nnn.moviee.adapter.MovieAdapter;
import com.nnn.moviee.api.MovieApi;
import com.nnn.moviee.model.Movie;
import com.nnn.moviee.model.response.ListMovieResponse;
import com.nnn.moviee.utils.S;
import com.nnn.moviee.utils.db.DB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListFragment extends Fragment {

    public enum TYPE {
        PLAYING,
        UPCOMING,
        SEARCH,
        FAVORITE
    }

    private TYPE type;
    private String query;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    MovieAdapter movieAdapter;
    List<Movie> movieList;

    Retrofit retrofit;
    MovieApi movieApi;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(TYPE type) {
        ListFragment fragment = new ListFragment();
        fragment.type = type;
        return fragment;
    }

    public void setQuery(String query) {
        this.query = query;
        S.log("Query recieved, now loading data");
        loadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(llm);

        retrofit = S.getRetrofit();
        movieApi = retrofit.create(MovieApi.class);

        loadData();

        S.log("oncreateview done");

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        S.log("On resume");

        if(type==TYPE.FAVORITE)
            loadFavorite();
    }

    void loadData() {
        Call<ListMovieResponse> call=null;
        switch (type) {
            case PLAYING:
                call = movieApi.getPlaying();
                break;
            case UPCOMING:
                call = movieApi.getUpcoming();
                break;
            case SEARCH:
                S.log("SEARCH : "+query);
                if(!S.isStringNull(query))
                    call=movieApi.getSearch(query);
                else
                    call=movieApi.getPopularMovies();
                break;
            default: return;
        }

        execCall(call);

    }

    void execCall(Call<ListMovieResponse> call){
        call.enqueue(new Callback<ListMovieResponse>() {
            @Override
            public void onResponse(Call<ListMovieResponse> call, Response<ListMovieResponse> response) {
                if(response.code()==200){
                    ListMovieResponse movieResponse = response.body();
                    movieList.clear();
                    movieList.addAll(movieResponse.getListResults());
                    movieAdapter.notifyDataSetChanged();
                }else{
                    S.toast(getContext(),response.code()+" : "+response.message());
                }

            }

            @Override
            public void onFailure(Call<ListMovieResponse> call, Throwable t) {
                S.toast(getContext(),"Unexpected Error");
            }
        });
    }

    void loadFavorite(){
        S.log("Load Favorite");
        Realm realm = Realm.getDefaultInstance();
        movieList.clear();
        movieList.addAll(DB.getFavorites(realm));
        movieAdapter.notifyDataSetChanged();

        S.log("count : "+ DB.getFavorites(realm).size());
    }
}
