package kudos26.movies.movie.api;

public interface MoviesApiCallback {
    void onSuccess(MovieObject movie);

    void onFailure(Throwable e);
}
