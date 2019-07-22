package kudos26.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kudos26.movies.R;
import kudos26.movies.movie.MovieEntity;
import kudos26.movies.movie.MovieScrollListener;
import kudos26.movies.movie.MovieViewModel;

import static kudos26.movies.Constants.BASE_URL_IMAGE_LOW;
import static kudos26.movies.Constants.FAVORITE_MOVIES;
import static kudos26.movies.Constants.KEY_ID;
import static kudos26.movies.Constants.KEY_ID_MOVIE;
import static kudos26.movies.Constants.POPULAR_MOVIES;
import static kudos26.movies.Constants.TOP_RATED_MOVIES;

public class MainActivity extends AppCompatActivity {

    private static boolean mTwoPane;

    private MovieViewModel mMovieViewModel;
    private MovieListAdapter mMovieListAdapter;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.movie_detail_container) != null;

        final RecyclerView mMovieListRecyclerView = findViewById(R.id.movie_list_recycler_view);
        mMovieListAdapter = new MovieListAdapter(this);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mMovieListRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieListRecyclerView.setAdapter(mMovieListAdapter);
        mMovieListRecyclerView.setItemViewCacheSize(100);

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        try {
            mMovieViewModel.getMovies().observe(this, new Observer<List<MovieEntity>>() {
                @Override
                public void onChanged(List<MovieEntity> movies) {
                    if (movies != null) {
                        mMovieListAdapter.setMovies(movies);
                    }
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mMovieListRecyclerView.addOnScrollListener(new MovieScrollListener(mGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i("Page Request", String.valueOf(page));
                mMovieViewModel.fetchMovies(page);
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
                //mToolbar.setTitle(STRING_POPULAR_MOVIES);
                mMovieViewModel.setSortCriteria(POPULAR_MOVIES);
                return true;
            }
            case R.id.top_rated: {
                //mToolbar.setTitle(STRING_TOP_RATED_MOVIES);
                mMovieViewModel.setSortCriteria(TOP_RATED_MOVIES);
                return true;
            }
            case R.id.favorites: {
                //mToolbar.setTitle(STRING_FAVORITE_MOVIES);
                mMovieViewModel.setSortCriteria(FAVORITE_MOVIES);
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
        @NonNull
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View movie = mLayoutInflater.inflate(R.layout.item_movie, parent, false);
            return new MovieHolder(movie);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
            movieHolder.updateMovie(mMovies.get(position));
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) movieHolder.itemView.getLayoutParams();
            if (position % 2 == 0) {
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
            }

        }

        class MovieHolder extends RecyclerView.ViewHolder {

            int mMovieId;
            TextView mMovieTitle;
            ShimmerFrameLayout mMovieShimmer;
            ImageView mMoviePoster;

            MovieHolder(View view) {
                super(view);
                mMovieTitle = view.findViewById(R.id.tv_movie_title);
                mMovieShimmer = view.findViewById(R.id.shimmer_movie);
                mMoviePoster = view.findViewById(R.id.iv_movie_poster);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putInt(KEY_ID_MOVIE, mMovieId);
                            MovieDetailFragment fragment = new MovieDetailFragment();
                            fragment.setArguments(arguments);
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
