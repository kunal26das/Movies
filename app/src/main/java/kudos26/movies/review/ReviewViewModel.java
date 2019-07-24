package kudos26.movies.review;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kudos26.movies.Database;
import kudos26.movies.review.api.ReviewObject;
import kudos26.movies.review.api.ReviewsApiCallback;
import kudos26.movies.review.api.ReviewsApiClient;

import static kudos26.movies.Constants.API_KEY;
import static kudos26.movies.Constants.LANGUAGE_EN_US;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewRepository mReviewRepository;

    public ReviewViewModel(Application application) {
        super(application);
        mReviewRepository = new ReviewRepository(application);
    }

    public LiveData<List<ReviewEntity>> getMovieReviews(int movieId) {
        return mReviewRepository.getReviews(movieId);
    }

    // Repository
    public class ReviewRepository {

        private ReviewsDao mDao;
        private ReviewsApiClient mApiClient;
        private ExecutorService mExecutorService;

        ReviewRepository(Application application) {
            mDao = Database.getDatabase(application).getReviewsDao();
            mExecutorService = Executors.newSingleThreadExecutor();
            mApiClient = new ReviewsApiClient();
        }

        LiveData<List<ReviewEntity>> getReviews(final int movieId) {
            mApiClient.getReviews(new ReviewsApiCallback() {
                @Override
                public void onSuccess(ReviewObject review) {
                    mExecutorService.submit(() -> mDao.insertReview(new ReviewEntity(movieId, review)));
                }

                @Override
                public void onFailure() {

                }
            }, movieId, API_KEY, LANGUAGE_EN_US, 1);
            return mDao.getReviews(movieId);
        }
    }
}
