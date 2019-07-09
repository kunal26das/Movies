package kudos26.aboutmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.Collections;
import java.util.List;

import kudos26.aboutmovies.R;
import kudos26.aboutmovies.movie.MovieEntity;
import kudos26.aboutmovies.movie.MovieViewModel;
import kudos26.aboutmovies.review.ReviewEntity;
import kudos26.aboutmovies.review.ReviewViewModel;
import kudos26.aboutmovies.trailer.TrailerEntity;
import kudos26.aboutmovies.trailer.TrailerViewModel;

import static kudos26.aboutmovies.Constants.KEY_ID;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/original";
    private static final String BASE_URL_THUMBNAIL = "https://img.youtube.com/vi/";
    private static final String DEFAULT = "/default.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView infoTextView = findViewById(R.id.tv_info);
        final CardView reviewsCardView = findViewById(R.id.cv_movie_reviews);
        final CardView trailersCardView = findViewById(R.id.cv_movie_trailers);
        final TextView synopsisTextView = findViewById(R.id.tv_movie_synopsis);
        final RecyclerView reviewRecyclerView = findViewById(R.id.rv_movie_reviews);
        final RecyclerView trailersRecyclerView = findViewById(R.id.rv_movie_trailers);

        final int movieId = getIntent().getIntExtra(KEY_ID, 0);
        final MovieViewModel mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        final MovieEntity movie = mMovieViewModel.getMovieEntry(movieId);

        final Toolbar toolbar = findViewById(R.id.detail_toolbar);
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
        infoTextView.setText(movieInfo);
        synopsisTextView.setText(movie.getOverview());

        final TrailerListAdapter trailerListAdapter = new TrailerListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        trailersRecyclerView.setAdapter(trailerListAdapter);
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

        final ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this);
        reviewRecyclerView.setLayoutManager(linearLayoutManager);
        reviewRecyclerView.setAdapter(reviewListAdapter);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerHolder> {

        private final LayoutInflater mLayoutInflater;
        private List<TrailerEntity> mTrailers = Collections.emptyList();

        TrailerListAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        void setTrailers(List<TrailerEntity> trailers) {
            mTrailers = trailers;
            notifyDataSetChanged();
            Log.i("Trailer", String.valueOf(mTrailers.size()));
        }

        @NonNull
        @Override
        public TrailerListAdapter.TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View trailer = mLayoutInflater.inflate(R.layout.item_trailer, parent, false);
            return new TrailerHolder(trailer);
        }

        @Override
        public void onBindViewHolder(@NonNull TrailerListAdapter.TrailerHolder trailerHolder, int position) {
            trailerHolder.updateTrailer(mTrailers.get(position));
        }

        @Override
        public int getItemCount() {
            return mTrailers.size();
        }

        class TrailerHolder extends RecyclerView.ViewHolder {

            ImageView mTrailerImageView;

            public TrailerHolder(@NonNull View view) {
                super(view);
                mTrailerImageView = view.findViewById(R.id.iv_trailer);
            }

            void updateTrailer(TrailerEntity trailer) {
                Picasso.get().load(BASE_URL_THUMBNAIL + trailer.getAddressKey() + DEFAULT).into(mTrailerImageView);
            }

        }
    }

    public static class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewHolder> {

        private final LayoutInflater mLayoutInflater;
        private List<ReviewEntity> mReviews = Collections.emptyList();

        ReviewListAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        void setReviews(List<ReviewEntity> reviews) {
            mReviews = reviews;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View review = mLayoutInflater.inflate(R.layout.item_review, parent, false);
            return new ReviewHolder(review);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewHolder reviewHolder, int position) {
            reviewHolder.updateReview(mReviews.get(position), position + 1);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        class ReviewHolder extends RecyclerView.ViewHolder {

            TextView mReviewNumber;
            TextView mReviewContent;

            ReviewHolder(@NonNull View view) {
                super(view);
                mReviewNumber = view.findViewById(R.id.tv_review_number);
                mReviewContent = view.findViewById(R.id.tv_review_content);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int maxLines = mReviewContent.getMaxLines();
                        if (maxLines == 5) {
                            mReviewContent.setMaxLines(100);
                        } else {
                            mReviewContent.setMaxLines(5);
                        }
                    }
                });
            }

            void updateReview(ReviewEntity review, int number) {
                String serialNumber = "#" + number;
                mReviewNumber.setText(serialNumber);
                mReviewContent.setText(review.getContent().trim());
            }
        }
    }
}
