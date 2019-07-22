package kudos26.movies.trailer;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.movies.SingletonDao;
import kudos26.movies.SingletonDatabase;
import kudos26.movies.trailer.api.TrailersApiCallback;
import kudos26.movies.trailer.api.TrailersApiClient;

import static android.provider.MediaStore.Video.VideoColumns.LANGUAGE;
import static kudos26.movies.Constants.API_KEY;

public class TrailerViewModel extends AndroidViewModel {

    private TrailerRepository mTrailerRepository;

    public TrailerViewModel(Application application) {
        super(application);
        mTrailerRepository = new TrailerRepository(application);
    }

    public LiveData<List<TrailerEntity>> getMovieTrailers(int movieId) {
        return mTrailerRepository.getTrailers(movieId);
    }

    // Repository
    public static class TrailerRepository {

        private static SingletonDao mDao;
        private static TrailersApiClient mApiClient;

        TrailerRepository(Application application) {
            mApiClient = new TrailersApiClient();
            mDao = SingletonDatabase.getDatabase(application).getDao();
        }

        LiveData<List<TrailerEntity>> getTrailers(final int movieId) {
            mApiClient.getTrailers(new TrailersApiCallback() {
                @Override
                public void onSuccess(TrailerObject trailer) {
                    mDao.insertTrailer(new TrailerEntity(movieId, trailer));
                }

                @Override
                public void onFailure(Throwable error) {

                }
            }, movieId, API_KEY, LANGUAGE);
            return mDao.getTrailers(movieId);
        }

    }
}
