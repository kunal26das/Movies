package kudos26.aboutmovies.review;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.aboutmovies.SingletonDao;
import kudos26.aboutmovies.SingletonDatabase;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewRepository mReviewRepository;

    public ReviewViewModel(Application application) {
        super(application);
        mReviewRepository = new ReviewRepository(application);
    }

    public LiveData<List<ReviewEntity>> getMovieReviews(int movieId) {
        return mReviewRepository.getReviews(movieId);
    }

    public static class ReviewRepository {

        private static SingletonDao mReviewDao;
        private SingletonDatabase mSingletonDatabase;
        //private LiveData<List<ReviewEntity>> mReviewEntries;

        ReviewRepository(Application application) {
            mSingletonDatabase = SingletonDatabase.getDatabase(application);
            mReviewDao = mSingletonDatabase.getDao();
        }

        LiveData<List<ReviewEntity>> getReviews(int movieId) {
            mSingletonDatabase.fetchMovieReviews(movieId);
            return mReviewDao.getReviews(movieId);
        }
    }
}
