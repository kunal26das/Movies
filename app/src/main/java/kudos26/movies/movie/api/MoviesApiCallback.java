package kudos26.movies.movie.api;

public interface MoviesApiCallback {
    void onSuccess(Movie movie);

    void onFailure(Throwable e);
}
