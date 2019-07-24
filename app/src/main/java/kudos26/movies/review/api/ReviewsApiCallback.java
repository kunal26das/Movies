package kudos26.movies.review.api;

public interface ReviewsApiCallback {
    void onSuccess(ReviewObject review);

    void onFailure();
}
