package kudos26.movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import kudos26.movies.R;
import kudos26.movies.movie.MovieViewModel;
import kudos26.movies.movie.api.Movie;
import kudos26.movies.review.ReviewListAdapter;
import kudos26.movies.review.ReviewViewModel;
import kudos26.movies.trailer.TrailerListAdapter;
import kudos26.movies.trailer.TrailerViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static kudos26.movies.Constants.BASE_URL_IMAGE_HIGH;
import static kudos26.movies.Constants.BASE_URL_YOUTUBE;
import static kudos26.movies.Constants.KEY_MOVIE_ENTITY;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private String mShareableLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovie = getIntent().getParcelableExtra(KEY_MOVIE_ENTITY);

        final TextView infoTextView = findViewById(R.id.tv_info);
        final RatingBar movieRatingBar = findViewById(R.id.movie_rating_bar);
        final TextView movieTitleTextView = findViewById(R.id.tv_movie_title);
        final TextView synopsisTextView = findViewById(R.id.tv_movie_synopsis);
        final ImageView toolbarPosterImageView = findViewById(R.id.iv_toolbar_poster);
        final FloatingActionButton favoriteButton = findViewById(R.id.fab_scroll_top);
        final MovieViewModel mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        if (getSupportActionBar() == null) {
            Toolbar toolbar = findViewById(R.id.detail_toolbar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Picasso.get().load(BASE_URL_IMAGE_HIGH + mMovie.getBackdropPath()).into(toolbarPosterImageView);
        String movieInfo = mMovie.getReleaseDate().split("-")[0] + "  |";
        movieRatingBar.setRating(mMovie.getVoteAverage() / 2);
        synopsisTextView.setText(mMovie.getOverview());
        movieTitleTextView.setText(mMovie.getTitle());
        infoTextView.setText(movieInfo);

        initTrailers();
        initReviews();

        if (mMovieViewModel.isFavorite(mMovie)) {
            favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_filled_foreground));
        } else {
            favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_outline_foreground));
        }

        favoriteButton.setOnClickListener(view -> {
            if (mMovieViewModel.updateFavorite(mMovie)) {
                favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_filled_foreground));
            } else {
                favoriteButton.setImageDrawable(getDrawable(R.mipmap.ic_heart_outline_foreground));
            }
        });
    }

    private void initTrailers() {
        final CardView trailersCardView = findViewById(R.id.cv_movie_trailers);
        final ProgressBar trailersProgressBar = findViewById(R.id.progress_trailers);
        final RecyclerView trailersRecyclerView = findViewById(R.id.rv_movie_trailers);
        final TrailerListAdapter trailerListAdapter = new TrailerListAdapter(this);
        trailersRecyclerView.setItemViewCacheSize(10);
        trailersRecyclerView.setAdapter(trailerListAdapter);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TrailerViewModel trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        trailerViewModel.getMovieTrailers(mMovie.getId()).observe(this, trailers -> {
            if (trailers != null && !trailers.isEmpty()) {
                trailerListAdapter.setTrailers(trailers);
                trailersCardView.setVisibility(VISIBLE);
                trailersProgressBar.setVisibility(GONE);
                mShareableLink = BASE_URL_YOUTUBE + trailers.get(0).getAddressKey();
            } else {
                trailersCardView.setVisibility(GONE);
            }
        });
    }

    private void initReviews() {
        final CardView reviewsCardView = findViewById(R.id.cv_movie_reviews);
        final ProgressBar reviewsProgressBar = findViewById(R.id.progress_reviews);
        final RecyclerView reviewRecyclerView = findViewById(R.id.rv_movie_reviews);
        final ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this);
        reviewRecyclerView.setAdapter(reviewListAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ReviewViewModel mReviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        mReviewViewModel.getMovieReviews(mMovie.getId()).observe(this, reviews -> {
            if (reviews != null && !reviews.isEmpty()) {
                reviewListAdapter.setReviews(reviews);
                reviewsCardView.setVisibility(VISIBLE);
                reviewsProgressBar.setVisibility(GONE);
            } else {
                reviewsCardView.setVisibility(GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        } else if (id == R.id.share) {
            if (mShareableLink != null) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, mShareableLink);
                startActivity(Intent.createChooser(shareIntent, "Trailer"));
            } else {
                Toast.makeText(this, "No Trailers Found", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
