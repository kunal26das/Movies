package kudos26.movies.trailer.api;

public interface TrailersApiCallback {
    void onSuccess(Trailer trailer);

    void onError(Throwable error);
}
