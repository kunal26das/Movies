package kudos26.aboutmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import kudos26.aboutmovies.movie.MovieEntity;
import kudos26.aboutmovies.movie.MovieViewModel;
import kudos26.aboutmovies.review.ReviewEntity;
import kudos26.aboutmovies.review.ReviewViewModel;

import static kudos26.aboutmovies.Constants.KEY_ID;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String BASE_URL_IMAGE = "http://image.tmdb.org/t/p/original";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Toolbar toolbar = findViewById(R.id.detail_toolbar);

        int movieId = getIntent().getIntExtra(KEY_ID, 0);
        MovieViewModel mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        MovieEntity movieEntity = mMovieViewModel.getMovieEntry(movieId);

        String movieTitle = movieEntity.getTitle();
        toolbar.setTitle(movieTitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        ImageView toolbarPosterImageView = findViewById(R.id.toolbar_poster_image_view);
        Picasso.get().load(BASE_URL_IMAGE + movieEntity.getPosterPath()).into(toolbarPosterImageView);

        final CardView reviewsCardView = findViewById(R.id.movie_reviews_card_view);
        CardView synopsisCardView = findViewById(R.id.movie_synopsis_card_view);
        CardView trailersCardView = findViewById(R.id.movie_trailers_card_view);

        TextView synopsisTextView = findViewById(R.id.movie_synopsis_text_view);
        TextView infoTextView = findViewById(R.id.tv_info);

        RecyclerView trailersRecyclerView = findViewById(R.id.movie_trailers_recycler_view);
        RecyclerView reviewRecyclerView = findViewById(R.id.movie_reviews_recycler_view);

        String movieInfo = movieEntity.getReleaseDate().split("-")[0] + " | " + movieEntity.getVoteAverage() + " Rating";
        infoTextView.setText(movieInfo);
        synopsisTextView.setText(movieEntity.getOverview());

        final ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(linearLayoutManager);
        reviewRecyclerView.setAdapter(reviewListAdapter);
        reviewRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ReviewViewModel mReviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        mReviewViewModel.getMovieReviews(movieId).observe(this, new Observer<List<ReviewEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ReviewEntity> reviews) {
                if (reviews != null && reviews.isEmpty()) {
                    reviewsCardView.setVisibility(View.GONE);
                } else {
                    reviewListAdapter.setReview(reviews);
                    reviewsCardView.setVisibility(View.VISIBLE);
                }
            }
        });


        final FloatingActionButton likeButton = findViewById(R.id.fab);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.mipmap.ic_heart_filled_foreground));
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

    public static class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewHolder> {

        private final LayoutInflater mLayoutInflater;
        private List<ReviewEntity> mReviews = Collections.emptyList();

        ReviewListAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        void setReview(List<ReviewEntity> reviews) {
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
