package kudos26.aboutmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import kudos26.aboutmovies.Movie.Movie;
import kudos26.aboutmovies.Movie.MovieListAdapter;
import kudos26.aboutmovies.Movie.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movieListRecyclerView = findViewById(R.id.movie_list_recycler_view);
        final MovieListAdapter movieListAdapter = new MovieListAdapter(this);
        movieListRecyclerView.setAdapter(movieListAdapter);
        movieListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        mMovieViewModel.getMovieLiveData().observe(this, new Observer<List<Movie>>(){
            @Override
            public void onChanged(@Nullable final List<Movie> movieList) {
                movieListAdapter.setMovieList(movieList);
            }
        });
    }
}
