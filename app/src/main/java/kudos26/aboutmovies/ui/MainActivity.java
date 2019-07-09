package kudos26.aboutmovies.ui;

import android.content.Context;
import android.content.Intent;
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

import kudos26.aboutmovies.R;
import kudos26.aboutmovies.movie.MovieEntity;
import kudos26.aboutmovies.movie.MovieScrollListener;
import kudos26.aboutmovies.movie.MovieViewModel;

import static kudos26.aboutmovies.Constants.KEY_ID;
import static kudos26.aboutmovies.Constants.POPULAR_MOVIES;
import static kudos26.aboutmovies.Constants.TOP_RATED_MOVIES;

public class MainActivity extends AppCompatActivity {

    private static final String STRING_POPULAR_MOVIES = "Popular Movies";
    private static final String STRING_FAVORITE_MOVIES = "Favorite Movies";
    private MovieViewModel mMovieViewModel;
    private MovieListAdapter mMovieListAdapter;
    private RecyclerView mMovieListRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private static boolean mTwoPane;
    private Toolbar mToolbar;
    private static final String STRING_TOP_RATED_MOVIES = "Top Rated Movies";
    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/original";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(STRING_POPULAR_MOVIES);
        setSupportActionBar(mToolbar);

        mTwoPane = findViewById(R.id.movie_detail_container) != null;

        mMovieListAdapter = new MovieListAdapter(this);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        mMovieListRecyclerView = findViewById(R.id.movie_list_recycler_view);
        mMovieListRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieListRecyclerView.setAdapter(mMovieListAdapter);

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.getMovieLiveData(POPULAR_MOVIES).observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MovieEntity> movies) {
                mMovieListAdapter.setMovies(movies);
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGridLayoutManager.scrollToPosition(0);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular: {
                mToolbar.setTitle(STRING_POPULAR_MOVIES);
                mMovieViewModel.getMovieLiveData(POPULAR_MOVIES).observe(this, new Observer<List<MovieEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<MovieEntity> movieEntityList) {
                        mMovieListAdapter.setMovies(movieEntityList);
                    }
                });
                mMovieListRecyclerView.addOnScrollListener(new MovieScrollListener(mGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        mMovieViewModel.getMoviesPage(POPULAR_MOVIES, page + 1);
                    }
                });
                return true;
            }
            case R.id.top_rated: {
                mToolbar.setTitle(STRING_TOP_RATED_MOVIES);
                mMovieViewModel.getMovieLiveData(TOP_RATED_MOVIES).observe(this, new Observer<List<MovieEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<MovieEntity> movieEntityList) {
                        mMovieListAdapter.setMovies(movieEntityList);
                    }
                });
                mMovieListRecyclerView.addOnScrollListener(new MovieScrollListener(mGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        mMovieViewModel.getMoviesPage(TOP_RATED_MOVIES, page + 1);
                    }
                });
                return true;
            }
            case R.id.favorites: {
                mToolbar.setTitle(STRING_FAVORITE_MOVIES);
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

    public static class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {

        private final LayoutInflater mLayoutInflater;
        private List<MovieEntity> mMovies = Collections.emptyList();

        MovieListAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getItemCount() {
            return mMovies.size();
        }

        void setMovies(List<MovieEntity> movies) {
            mMovies = movies;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
            movieHolder.updateMovie(mMovies.get(position));
        }

        @Override
        @NonNull
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View movie = mLayoutInflater.inflate(R.layout.item_movie, parent, false);
            return new MovieHolder(movie);
        }

        class MovieHolder extends RecyclerView.ViewHolder {

            int mMovieId;
            TextView mMovieTitleTextView;
            ImageView mMoviePosterImageView;

            MovieHolder(View view) {
                super(view);
                mMovieTitleTextView = view.findViewById(R.id.movie_title_text_view);
                mMoviePosterImageView = view.findViewById(R.id.movie_poster_image_view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mTwoPane) {

                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, MovieDetailActivity.class);
                            intent.putExtra(KEY_ID, mMovieId);
                            context.startActivity(intent);
                        }
                    }
                });
            }

            void updateMovie(MovieEntity movie) {
                mMovieId = movie.getId();
                mMovieTitleTextView.setText(movie.getTitle());
                String moviePosterPath = movie.getPosterPath();
                Picasso.get()
                        .load(BASE_URL_IMAGE + moviePosterPath)
                        .resize(0, 812)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mMoviePosterImageView);
            }
        }
    }
}
