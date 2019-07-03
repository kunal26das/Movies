package kudos26.aboutmovies.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Collections;
import java.util.List;

import kudos26.aboutmovies.R;
import kudos26.aboutmovies.pojo.MovieEntry;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {

    private List<MovieEntry> mMovieEntryList = Collections.emptyList();
    private final LayoutInflater mLayoutInflater;

    @Override
    public int getItemCount() {
        return mMovieEntryList.size();
    }

    public MovieListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setMovieList(List<MovieEntry> movieEntryList) {
        mMovieEntryList = movieEntryList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
        movieHolder.updateMovie(mMovieEntryList.get(position));
    }

    @Override @NonNull
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View movie = mLayoutInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(movie);
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        private TextView mMovieTitleTextView;
        private ShimmerFrameLayout mMovieTitleShimmer;

        public MovieHolder(View view) {
            super(view);
            mMovieTitleTextView = view.findViewById(R.id.movie_title_text_view);
            mMovieTitleShimmer = view.findViewById(R.id.movie_title_shimmer);
        }

        public void updateMovie(MovieEntry movieEntry) {
            mMovieTitleTextView.setText(movieEntry.getTitle());
            mMovieTitleShimmer.stopShimmer();
        }
    }
}
