package kudos26.aboutmovies.Movie;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import kudos26.aboutmovies.R;

public class MovieHolder extends RecyclerView.ViewHolder {

    private TextView mMovieTitleTextView;
    private ShimmerFrameLayout mMovieTitleShimmer;

    public MovieHolder(View view) {
        super(view);
        mMovieTitleTextView = view.findViewById(R.id.movie_title_text_view);
        mMovieTitleShimmer = view.findViewById(R.id.movie_title_shimmer);
    }

    public void updateMovie(Movie movie) {
        mMovieTitleTextView.setText(movie.getMovieTitle());
        mMovieTitleShimmer.stopShimmer();
    }
}
