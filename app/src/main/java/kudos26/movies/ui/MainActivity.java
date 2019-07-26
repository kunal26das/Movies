package kudos26.movies.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import kudos26.movies.R;
import kudos26.movies.movie.EndlessScrollListener;
import kudos26.movies.movie.MovieViewModel;
import kudos26.movies.movie.api.Movie;

import static kudos26.movies.Constants.BASE_URL_IMAGE_LOW;
import static kudos26.movies.Constants.KEY_MOVIE_ENTITY;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private static final String KEY_TOOLBAR_TITLE = "TOOLBAR_TITLE";
    private static final String KEY_SCROLL_POSITION = "SCROLL_POSITION";
    private static final String STRING_POPULAR_MOVIES = "Popular Movies";
    private static final String STRING_FAVORITE_MOVIES = "Favorite Movies";
    private static final String STRING_TOP_RATED_MOVIES = "Top Rated Movies";

    private Toolbar mToolbar;
    private static boolean mTwoPane;
    private MovieViewModel mMovieViewModel;
    private RecyclerView mMovieRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        mTwoPane = findViewById(R.id.movie_detail_container) != null;

        mMovieRecyclerView = findViewById(R.id.rv_movie_list);

        final MovieListAdapter movieListAdapter = new MovieListAdapter(this);
        final ContentLoadingProgressBar mProgressBar = findViewById(R.id.progress_movies);

        mGridLayoutManager = new GridLayoutManager(this, 2);
        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieRecyclerView.setAdapter(movieListAdapter);
        mMovieRecyclerView.setItemViewCacheSize(100);

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.getMovies().observe(this, movieListAdapter::setMovies);

        final EndlessScrollListener endlessScrollListener = new EndlessScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mMovieViewModel.fetchMovies(page);
            }
        };

        mMovieRecyclerView.addOnScrollListener(endlessScrollListener);
        endlessScrollListener.getLoadingStatus().observe(this, loading -> {
            if (loading) {
                mProgressBar.show();
            } else {
                mProgressBar.hide();
            }
        });

        findViewById(R.id.fab_scroll_top).setOnClickListener(view -> mMovieRecyclerView.scrollToPosition(0));

        if (getSupportActionBar() == null) {
            mToolbar = findViewById(R.id.toolbar);
            mToolbar.setTitle(STRING_POPULAR_MOVIES);
            setSupportActionBar(mToolbar);
        }
        if (savedInstanceState != null) {
            mToolbar.setTitle(savedInstanceState.getString(KEY_TOOLBAR_TITLE));
            mGridLayoutManager.scrollToPosition(savedInstanceState.getInt(KEY_SCROLL_POSITION));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_TOOLBAR_TITLE, mToolbar.getTitle().toString());
        outState.putInt(KEY_SCROLL_POSITION, mGridLayoutManager.findFirstCompletelyVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular: {
                mToolbar.setTitle(STRING_POPULAR_MOVIES);
                mMovieViewModel.switchToPopularMovies();
                mMovieRecyclerView.scrollToPosition(0);
                return true;
            }
            case R.id.top_rated: {
                mToolbar.setTitle(STRING_TOP_RATED_MOVIES);
                mMovieViewModel.switchTopTopRatedMovies();
                mMovieRecyclerView.scrollToPosition(0);
                return true;
            }
            case R.id.favorite: {
                mToolbar.setTitle(STRING_FAVORITE_MOVIES);
                mMovieViewModel.switchToFavoriteMovies();
                mMovieRecyclerView.scrollToPosition(0);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onItemClickListener(final Movie movie) {
        if (mTwoPane) {
            final FloatingActionButton favoriteButton = findViewById(R.id.fab_favorite);
            favoriteButton.setVisibility(View.VISIBLE);

            if (mMovieViewModel.isFavorite(movie)) {
                favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_filled_foreground));
            } else {
                favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_outline_foreground));
            }

            favoriteButton.setOnClickListener(view -> {
                if (mMovieViewModel.updateFavorite(movie)) {
                    favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_filled_foreground));
                } else {
                    favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_outline_foreground));
                }
            });

            Bundle arguments = new Bundle();
            arguments.putParcelable(KEY_MOVIE_ENTITY, movie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(KEY_MOVIE_ENTITY, movie);
            startActivity(intent);
        }
    }

    public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {

        private MainActivity mParent;
        private List<Movie> mLiveData = Collections.emptyList();

        MovieListAdapter(MainActivity parent) {
            mParent = parent;
        }

        @Override
        public int getItemCount() {
            return mLiveData.size();
        }

        void setMovies(List<Movie> movies) {
            mLiveData = movies;
            if (mLiveData.isEmpty()) {
                mMovieViewModel.fetchMovies(1);
            }
            notifyDataSetChanged();
        }

        @Override
        @NonNull
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View movie = mParent.getLayoutInflater().inflate(R.layout.item_movie, parent, false);
            return new MovieHolder(movie);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder movieHolder, final int position) {
            movieHolder.updateMovie(mLiveData.get(position));
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) movieHolder.itemView.getLayoutParams();
            /*if (position % 2 == 0) {
                layoutParams.setMargins(32, 32, 32, 0);
                int itemCount = getItemCount();
                if (position == itemCount - 2 || position == itemCount - 1) {
                    layoutParams.setMargins(32, 32, 32, 32);
                }
            } else if (position % 2 == 1) {
                layoutParams.setMargins(0, 32, 32, 0);
                if (position == getItemCount() - 1) {
                    layoutParams.setMargins(0, 32, 32, 32);
                }
            }*/
            movieHolder.itemView.setOnClickListener(view -> mParent.onItemClickListener(mLiveData.get(position)));
        }

        class MovieHolder extends RecyclerView.ViewHolder {

            TextView mMovieTitle;
            ImageView mMoviePoster;
            ShimmerFrameLayout mMovieShimmer;

            MovieHolder(View view) {
                super(view);
                mMovieTitle = view.findViewById(R.id.tv_movie_title);
                mMovieShimmer = view.findViewById(R.id.shimmer_movie);
                mMoviePoster = view.findViewById(R.id.iv_movie_poster);
            }

            void updateMovie(Movie movie) {
                mMovieTitle.setText(movie.getTitle());
                mMovieShimmer.setVisibility(View.VISIBLE);
                String moviePosterPath = movie.getPosterPath();
                Picasso.get()
                        .load(BASE_URL_IMAGE_LOW + moviePosterPath)
                        .into(mMoviePoster, new Callback() {
                            @Override
                            public void onSuccess() {
                                mMovieShimmer.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
            }
        }
    }
}
