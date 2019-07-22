package kudos26.movies.review.api;

import kudos26.movies.review.ReviewObject;

public interface ReviewsApiCallback {
    void onSuccess(ReviewObject review);

    void onFailure();
}
