package kudos26.aboutmovies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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
import kudos26.aboutmovies.ui.MovieScrollListener;
import kudos26.aboutmovies.ui.MovieViewModel;

import static kudos26.aboutmovies.Constants.POPULAR_MOVIES;
import static kudos26.aboutmovies.Constants.TOP_RATED_MOVIES;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private MovieViewModel mMovieViewModel;
    private RecyclerView mMovieListRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MovieListAdapter mMovieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        }

        mMovieListAdapter = new MovieListAdapter(this);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        mMovieListRecyclerView = findViewById(R.id.movie_list_recycler_view);
        mMovieListRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieListRecyclerView.setAdapter(mMovieListAdapter);

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.getMovieLiveData(POPULAR_MOVIES).observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable final List<MovieEntry> movieEntryList) {
                mMovieListAdapter.setMovieList(movieEntryList);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular: {
                mMovieViewModel.getMovieLiveData(POPULAR_MOVIES).observe(this, new Observer<List<MovieEntry>>() {
                    @Override
                    public void onChanged(@Nullable final List<MovieEntry> movieEntryList) {
                        mMovieListAdapter.setMovieList(movieEntryList);
                    }
                });
                mMovieListRecyclerView.addOnScrollListener(new MovieScrollListener(mGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        mMovieViewModel.fetchPopularMovies(page + 1);
                    }
                });
                return true;
            }
            case R.id.top_rated: {
                mMovieViewModel.getMovieLiveData(TOP_RATED_MOVIES).observe(this, new Observer<List<MovieEntry>>() {
                    @Override
                    public void onChanged(@Nullable final List<MovieEntry> movieEntryList) {
                        mMovieListAdapter.setMovieList(movieEntryList);
                    }
                });
                mMovieListRecyclerView.addOnScrollListener(new MovieScrollListener(mGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        mMovieViewModel.fetchTopRatedMovies(page + 1);
                    }
                });
                return true;
            }
            case R.id.favorites: {
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
