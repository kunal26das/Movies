package kudos26.movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.concurrent.ExecutionException;

import kudos26.movies.R;
import kudos26.movies.movie.MovieEntity;
import kudos26.movies.movie.MovieViewModel;
import kudos26.movies.review.ReviewEntity;
import kudos26.movies.review.ReviewListAdapter;
import kudos26.movies.review.ReviewViewModel;
import kudos26.movies.trailer.TrailerEntity;
import kudos26.movies.trailer.TrailerListAdapter;
import kudos26.movies.trailer.TrailerViewModel;

import static kudos26.movies.Constants.BASE_URL_IMAGE_HIGH;
import static kudos26.movies.Constants.BASE_URL_YOUTUBE;
import static kudos26.movies.Constants.INTENT_KEY_MOVIE_ID;

public class MovieDetailActivity extends AppCompatActivity {


    private MovieEntity mMovieEntity;
    private String mShareableLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView infoTextView = findViewById(R.id.tv_info);
        final FloatingActionButton favoriteButton = findViewById(R.id.fab_favorite);
        final RatingBar movieRatingBar = findViewById(R.id.movie_rating_bar);
        final TextView synopsisTextView = findViewById(R.id.tv_movie_synopsis);
        final ImageView toolbarPosterImageView = findViewById(R.id.iv_toolbar_poster);
        final int movieId = getIntent().getIntExtra(INTENT_KEY_MOVIE_ID, 0);
        final MovieViewModel mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        mMovieEntity = mMovieViewModel.getMovie(movieId);

        if (getSupportActionBar() == null) {
            Toolbar toolbar = findViewById(R.id.detail_toolbar);
            toolbar.setTitle(mMovieEntity.getTitle());
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Picasso.get().load(BASE_URL_IMAGE_HIGH + mMovieEntity.getPosterPath()).into(toolbarPosterImageView);
        String movieInfo = mMovieEntity.getReleaseDate().split("-")[0] + "  |";
        movieRatingBar.setRating(mMovieEntity.getVoteAverage() / 2);
        synopsisTextView.setText(mMovieEntity.getOverview());
        infoTextView.setText(movieInfo);

        initTrailers();
        initReviews();

        if (mMovieEntity.isFavorite()) {
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.mipmap.ic_heart_filled_foreground));
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMovieViewModel.updateFavorite(movieId);
                try {
                    if (mMovieViewModel.isFavorite(movieId)) {
                        favoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                R.mipmap.ic_heart_filled_foreground));
                    } else {
                        favoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                R.mipmap.ic_heart_outline_foreground));
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        /*if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }*/
    }

    private void initTrailers() {
        final CardView trailersCardView = findViewById(R.id.cv_movie_trailers);
        final RecyclerView trailersRecyclerView = findViewById(R.id.rv_movie_trailers);
        final TrailerListAdapter trailerListAdapter = new TrailerListAdapter(this);
        trailersRecyclerView.setItemViewCacheSize(10);
        trailersRecyclerView.setAdapter(trailerListAdapter);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        TrailerViewModel trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        trailerViewModel.getMovieTrailers(mMovieEntity.getId()).observe(this, new Observer<List<TrailerEntity>>() {

            @Override
            public void onChanged(List<TrailerEntity> trailers) {
                if (trailers != null && trailers.isEmpty()) {
                    trailersCardView.setVisibility(View.GONE);
                } else if (trailers != null) {
                    trailerListAdapter.setTrailers(trailers);
                    trailersCardView.setVisibility(View.VISIBLE);
                    mShareableLink = BASE_URL_YOUTUBE + trailers.get(0).getAddressKey();
                }
            }
        });
    }

    private void initReviews() {
        final CardView reviewsCardView = findViewById(R.id.cv_movie_reviews);
        final RecyclerView reviewRecyclerView = findViewById(R.id.rv_movie_reviews);
        final ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this);
        reviewRecyclerView.setAdapter(reviewListAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ReviewViewModel mReviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        mReviewViewModel.getMovieReviews(mMovieEntity.getId()).observe(this, new Observer<List<ReviewEntity>>() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
