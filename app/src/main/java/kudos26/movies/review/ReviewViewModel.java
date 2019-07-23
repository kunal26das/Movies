package kudos26.movies.review;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.movies.review.api.ReviewsApiCallback;
import kudos26.movies.review.api.ReviewsApiClient;
import kudos26.movies.room.Database;
import kudos26.movies.room.ReviewsDao;

import static android.provider.MediaStore.Video.VideoColumns.LANGUAGE;
import static kudos26.movies.Constants.API_KEY;

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
    public static class ReviewRepository {

        private static ReviewsDao mDao;
        private static ReviewsApiClient mApiClient;

        ReviewRepository(Application application) {
            mDao = Database.getDatabase(application).getReviewsDao();
            mApiClient = new ReviewsApiClient();
        }

        LiveData<List<ReviewEntity>> getReviews(final int movieId) {
            mApiClient.getReviews(new ReviewsApiCallback() {
                @Override
                public void onSuccess(ReviewObject review) {
                    mDao.insertReview(new ReviewEntity(movieId, review));
                }

                @Override
                public void onFailure() {

                }
            }, movieId, API_KEY, LANGUAGE, 1);
            return mDao.getReviews(movieId);
        }
    }
}
