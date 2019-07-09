package kudos26.aboutmovies.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import kudos26.aboutmovies.R;
import kudos26.aboutmovies.movie.MovieEntity;
import kudos26.aboutmovies.movie.MovieViewModel;
import kudos26.aboutmovies.review.ReviewEntity;
import kudos26.aboutmovies.review.ReviewListAdapter;
import kudos26.aboutmovies.review.ReviewViewModel;
import kudos26.aboutmovies.trailer.TrailerEntity;
import kudos26.aboutmovies.trailer.TrailerListAdapter;
import kudos26.aboutmovies.trailer.TrailerViewModel;

import static kudos26.aboutmovies.Constants.KEY_ID;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/original";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView infoTextView = findViewById(R.id.tv_info);
        final Toolbar toolbar = findViewById(R.id.detail_toolbar);
        final TextView synopsisTextView = findViewById(R.id.tv_movie_synopsis);
        final MovieViewModel mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        final int movieId = getIntent().getIntExtra(KEY_ID, 0);
        final MovieEntity movie = mMovieViewModel.getMovieEntry(movieId);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        toolbar.setTitle(movie.getTitle());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ImageView toolbarPosterImageView = findViewById(R.id.iv_toolbar_poster);
        Picasso.get().load(BASE_URL_IMAGE + movie.getPosterPath()).into(toolbarPosterImageView);

        String movieInfo = movie.getReleaseDate().split("-")[0] + " | " + movie.getVoteAverage() + " Rating";
        synopsisTextView.setText(movie.getOverview());
        infoTextView.setText(movieInfo);

        initTrailers(movieId);
        initReviews(movieId);

        final FloatingActionButton favoriteButton = findViewById(R.id.fab);
        if (movie.isFavorite()) {
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.mipmap.ic_heart_filled_foreground));
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMovieViewModel.isMovieFavorite(movieId)) {
                    mMovieViewModel.setNotFavorite(movieId);
                    favoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                            R.mipmap.ic_heart_outline_foreground));
                } else {
                    mMovieViewModel.setFavorite(movieId);
                    favoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                            R.mipmap.ic_heart_filled_foreground));
                }
            }
        });

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }

    private void initTrailers(final int movieId) {
        final CardView trailersCardView = findViewById(R.id.cv_movie_trailers);
        final RecyclerView trailersRecyclerView = findViewById(R.id.rv_movie_trailers);
        final TrailerListAdapter trailerListAdapter = new TrailerListAdapter(this);
        trailersRecyclerView.setAdapter(trailerListAdapter);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailersRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        TrailerViewModel trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        trailerViewModel.getMovieTrailers(movieId).observe(this, new Observer<List<TrailerEntity>>() {

            @Override
            public void onChanged(List<TrailerEntity> trailers) {
                if (trailers != null && trailers.isEmpty()) {
                    trailersCardView.setVisibility(View.GONE);
                } else if (trailers != null) {
                    trailerListAdapter.setTrailers(trailers);
                    trailersCardView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initReviews(final int movieId) {
        final CardView reviewsCardView = findViewById(R.id.cv_movie_reviews);
        final RecyclerView reviewRecyclerView = findViewById(R.id.rv_movie_reviews);
        final ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this);
        reviewRecyclerView.setAdapter(reviewListAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ReviewViewModel mReviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        mReviewViewModel.getMovieReviews(movieId).observe(this, new Observer<List<ReviewEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ReviewEntity> reviews) {
                if (reviews != null && reviews.isEmpty()) {
                    reviewsCardView.setVisibility(View.GONE);
                } else if (reviews != null) {
                    reviewListAdapter.setReviews(reviews);
                    reviewsCardView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
