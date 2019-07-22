package kudos26.movies.trailer.api;

import kudos26.movies.trailer.TrailerObject;

public interface TrailersApiCallback {
    void onSuccess(TrailerObject trailer);

    void onFailure(Throwable error);
}
