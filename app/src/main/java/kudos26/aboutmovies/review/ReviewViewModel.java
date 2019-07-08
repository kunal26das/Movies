package kudos26.aboutmovies.review;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewRepository mReviewRepository;

    public ReviewViewModel(Application application) {
        super(application);
        mReviewRepository = new ReviewRepository(application);
    }

    public LiveData<List<ReviewEntity>> getMovieReviews(int movieId) {
        return mReviewRepository.getReviews(movieId);
    }

}
