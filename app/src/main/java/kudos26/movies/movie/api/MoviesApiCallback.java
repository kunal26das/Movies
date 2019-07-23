package kudos26.movies.movie.api;

import kudos26.movies.movie.MovieObject;

public interface MoviesApiCallback {
    void onSuccess(MovieObject movie);

    void onFailure(Throwable e);
}
