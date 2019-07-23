package kudos26.movies.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;
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

import static kudos26.movies.Constants.BASE_URL_YOUTUBE;
import static kudos26.movies.Constants.INTENT_KEY_MOVIE_ID;

public class MovieDetailFragment extends Fragment {

    private String mShareableLink;
    private MovieEntity mMovieEntity;
    private MovieViewModel mMovieViewModel;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        if (getArguments() != null && getArguments().containsKey(INTENT_KEY_MOVIE_ID)) {
            mMovieEntity = mMovieViewModel.getMovie(getArguments().getInt(INTENT_KEY_MOVIE_ID));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null && getContext() != null) {
            final TextView infoTextView = getView().findViewById(R.id.tv_info);
            final RatingBar movieRatingBar = getView().findViewById(R.id.movie_rating_bar);
            final TextView movieTitleTextView = getView().findViewById(R.id.tv_movie_title);
            final TextView synopsisTextView = getView().findViewById(R.id.tv_movie_synopsis);
            final FloatingActionButton favoriteButton = getView().findViewById(R.id.fab_favorite);

            String movieInfo = mMovieEntity.getReleaseDate().split("-")[0] + "  |";
            movieRatingBar.setRating(mMovieEntity.getVoteAverage() / 2);
            synopsisTextView.setText(mMovieEntity.getOverview());
            movieTitleTextView.setText(mMovieEntity.getTitle());
            infoTextView.setText(movieInfo);

            initTrailers();
            initReviews();

            if (mMovieEntity.isFavorite()) {
                favoriteButton.setImageDrawable(ContextCompat.getDrawable(getContext(),
                        R.mipmap.ic_heart_filled_foreground));
            }
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mMovieViewModel.updateFavorite(mMovieEntity.getId());
                        if (mMovieViewModel.isFavorite(mMovieEntity.getId())) {
                            favoriteButton.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                                    R.mipmap.ic_heart_filled_foreground));
                        } else {
                            favoriteButton.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                                    R.mipmap.ic_heart_outline_foreground));
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void initTrailers() {
        if (getView() != null) {
            final CardView trailersCardView = getView().findViewById(R.id.cv_movie_trailers);
            final RecyclerView trailersRecyclerView = getView().findViewById(R.id.rv_movie_trailers);
            final TrailerListAdapter trailerListAdapter = new TrailerListAdapter(getContext());
            trailersRecyclerView.setItemViewCacheSize(10);
            trailersRecyclerView.setAdapter(trailerListAdapter);
            trailersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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
    }

    private void initReviews() {
        if (getView() != null && getContext() != null) {
            final CardView reviewsCardView = getView().findViewById(R.id.cv_movie_reviews);
            final RecyclerView reviewRecyclerView = getView().findViewById(R.id.rv_movie_reviews);
            final ReviewListAdapter reviewListAdapter = new ReviewListAdapter(getContext());
            reviewRecyclerView.setAdapter(reviewListAdapter);
            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            reviewRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
    }
}
