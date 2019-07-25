package kudos26.movies.review.api;

public interface ReviewsApiCallback {
    void onSuccess(Review review);

    void onFailure();
}
