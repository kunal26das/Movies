package kudos26.aboutmovies;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kudos26.aboutmovies.pojo.MovieEntry;
import kudos26.aboutmovies.ui.MovieListAdapter;
import kudos26.aboutmovies.ui.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private MovieViewModel mMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        RecyclerView recyclerView = findViewById(R.id.movie_list_recycler_view);
        final MovieListAdapter movieListAdapter = new MovieListAdapter(this);
        recyclerView.setAdapter(movieListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.getMovieLiveData().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable final List<MovieEntry> movieEntryList) {
                movieListAdapter.setMovieList(movieEntryList);
            }
        });
    }
}
