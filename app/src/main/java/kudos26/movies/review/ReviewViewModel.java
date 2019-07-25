package kudos26.movies.review;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import kudos26.movies.review.api.Review;
import kudos26.movies.review.api.ReviewsApiCallback;
import kudos26.movies.review.api.ReviewsApiClient;

import static kudos26.movies.Constants.API_KEY;
import static kudos26.movies.Constants.LANGUAGE_EN_US;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewsApiClient mApiClient;
    private MutableLiveData<List<Review>> mReviews;

    public ReviewViewModel(Application application) {
        super(application);
        mReviews = new MutableLiveData<>();
        mReviews.setValue(new ArrayList<>());
        mApiClient = new ReviewsApiClient();
    }

    public LiveData<List<Review>> getMovieReviews(int movieId) {
        mApiClient.getReviews(new ReviewsApiCallback() {
            @Override
            public void onSuccess(Review review) {
                List<Review> tempList = mReviews.getValue();
                if (tempList != null) {
                    tempList.add(review);
                }
                mReviews.setValue(tempList);
            }

            @Override
            public void onFailure() {

            }
        }, movieId, API_KEY, LANGUAGE_EN_US, 1);
        return mReviews;
    }
}
