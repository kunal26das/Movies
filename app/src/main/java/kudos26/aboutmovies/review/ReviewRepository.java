package kudos26.aboutmovies.review;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReviewRepository {

    private static ReviewDao mReviewDao;
    private ReviewDatabase mReviewDatabase;
    //private LiveData<List<ReviewEntity>> mReviewEntries;

    ReviewRepository(Application application) {
        mReviewDatabase = ReviewDatabase.getDatabase(application);
        mReviewDao = mReviewDatabase.reviewDao();
    }

    LiveData<List<ReviewEntity>> getReviews(int movieId) {
        mReviewDatabase.fetchMovieReviews(movieId);
        return mReviewDao.getReviews(movieId);
    }
}
