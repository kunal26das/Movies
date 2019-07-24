package kudos26.movies.trailer.api;

public interface TrailersApiCallback {
    void onSuccess(TrailerObject trailer);

    void onError(Throwable error);
}
