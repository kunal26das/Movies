package kudos26.aboutmovies.Movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import kudos26.aboutmovies.R;

public class MovieListAdapter extends RecyclerView.Adapter<MovieHolder> {

    private List<Movie> mMovieList = Collections.emptyList();
    private final LayoutInflater mLayoutInflater;

    public MovieListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    @Override @NonNull
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View movie = mLayoutInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(movie);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
        movieHolder.updateMovie(mMovieList.get(position));
    }
}
