package kudos26.movies.trailer;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.movies.SingletonDao;
import kudos26.movies.SingletonDatabase;

public class TrailerViewModel extends AndroidViewModel {

    private TrailerRepository mTrailerRepository;

    public TrailerViewModel(Application application) {
        super(application);
        mTrailerRepository = new TrailerRepository(application);
    }

    public LiveData<List<TrailerEntity>> getMovieTrailers(int movieId) {
        return mTrailerRepository.getTrailers(movieId);
    }

    public static class TrailerRepository {

        private static SingletonDao mTrailerDao;
        private SingletonDatabase mTrailerSingletonDatabase;

        TrailerRepository(Application application) {
            mTrailerSingletonDatabase = SingletonDatabase.getDatabase(application);
            mTrailerDao = mTrailerSingletonDatabase.getDao();
        }

        LiveData<List<TrailerEntity>> getTrailers(int movieId) {
            mTrailerSingletonDatabase.fetchMovieTrailers(movieId);
            return mTrailerDao.getTrailers(movieId);
        }

    }
}
