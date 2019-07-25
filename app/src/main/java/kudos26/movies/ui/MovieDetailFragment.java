package kudos26.movies.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kudos26.movies.R;
import kudos26.movies.movie.api.Movie;
import kudos26.movies.review.ReviewListAdapter;
import kudos26.movies.review.ReviewViewModel;
import kudos26.movies.trailer.TrailerListAdapter;
import kudos26.movies.trailer.TrailerViewModel;

import static kudos26.movies.Constants.BASE_URL_YOUTUBE;
import static kudos26.movies.Constants.KEY_MOVIE_ENTITY;

public class MovieDetailFragment extends Fragment {

    private Movie mMovie;
    private String mShareableLink;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(KEY_MOVIE_ENTITY)) {
            mMovie = getArguments().getParcelable(KEY_MOVIE_ENTITY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_detail, container, false);
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

            String movieInfo = mMovie.getReleaseDate().split("-")[0] + "  |";
            movieRatingBar.setRating(mMovie.getVoteAverage() / 2);
            synopsisTextView.setText(mMovie.getOverview());
            movieTitleTextView.setText(mMovie.getTitle());
            movieTitleTextView.setVisibility(View.VISIBLE);
            infoTextView.setText(movieInfo);

            initTrailers();
            initReviews();
        }
    }

    private void initTrailers() {
        final ProgressBar trailersProgressBar = getView().findViewById(R.id.progress_trailers);
        final RecyclerView trailersRecyclerView = getView().findViewById(R.id.rv_movie_trailers);
        final TrailerListAdapter trailerListAdapter = new TrailerListAdapter(getContext());
        trailersRecyclerView.setItemViewCacheSize(10);
        trailersRecyclerView.setAdapter(trailerListAdapter);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        TrailerViewModel trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        trailerViewModel.getMovieTrailers(mMovie.getId()).observe(this, trailers -> {
            if (trailers != null && !trailers.isEmpty()) {
                trailerListAdapter.setTrailers(trailers);
                trailersProgressBar.setVisibility(View.GONE);
                mShareableLink = BASE_URL_YOUTUBE + trailers.get(0).getAddressKey();
            }
        });
    }

    private void initReviews() {
        final ProgressBar reviewsProgressBar = getView().findViewById(R.id.progress_reviews);
        final RecyclerView reviewRecyclerView = getView().findViewById(R.id.rv_movie_reviews);
        final ReviewListAdapter reviewListAdapter = new ReviewListAdapter(getContext());
        reviewRecyclerView.setAdapter(reviewListAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ReviewViewModel mReviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        mReviewViewModel.getMovieReviews(mMovie.getId()).observe(this, reviews -> {
            if (reviews != null && !reviews.isEmpty()) {
                reviewListAdapter.setReviews(reviews);
                reviewsProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
