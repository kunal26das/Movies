package kudos26.aboutmovies;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import kudos26.aboutmovies.pojo.MovieEntry;
import kudos26.aboutmovies.ui.MovieScrollListener;
import kudos26.aboutmovies.ui.MovieViewModel;

import static kudos26.aboutmovies.Constants.POPULAR_MOVIES;
import static kudos26.aboutmovies.Constants.TOP_RATED_MOVIES;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private MovieViewModel mMovieViewModel;
    private MovieListAdapter mMovieListAdapter;
    private RecyclerView mMovieListRecyclerView;
    private GridLayoutManager mGridLayoutManager;

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

    public static class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {

        private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/w185";
        private final LayoutInflater mLayoutInflater;
        private List<MovieEntry> mMovieEntryList = Collections.emptyList();

        public MovieListAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getItemCount() {
            return mMovieEntryList.size();
        }

        public void setMovieList(List<MovieEntry> movieEntryList) {
            mMovieEntryList = movieEntryList;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
            movieHolder.updateMovie(mMovieEntryList.get(position));
        }

        @Override
        @NonNull
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View movie = mLayoutInflater.inflate(R.layout.item_movie, parent, false);
            return new MovieHolder(movie);
        }

        public class MovieHolder extends RecyclerView.ViewHolder {

            private TextView mMovieTitleTextView;
            private ImageView mMoviePosterImageView;

            public MovieHolder(View view) {
                super(view);
                mMoviePosterImageView = view.findViewById(R.id.movie_poster_image_view);
                mMovieTitleTextView = view.findViewById(R.id.movie_title_text_view);
            }

            public void updateMovie(MovieEntry movieEntry) {
                String imageUrl = BASE_URL_IMAGE + movieEntry.getPosterPath();
                mMovieTitleTextView.setText(movieEntry.getTitle());
                Picasso.get()
                        .load(imageUrl)
                        .resize(0, 812)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mMoviePosterImageView);
            }
        }
    }
}
