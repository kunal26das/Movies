package kudos26.movies.trailer;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kudos26.movies.room.Database;
import kudos26.movies.room.TrailersDao;
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
    public class TrailerRepository {

        private TrailersDao mDao;
        private TrailersApiClient mApiClient;
        private ExecutorService mExecutorService;

        TrailerRepository(Application application) {
            mApiClient = new TrailersApiClient();
            mExecutorService = Executors.newSingleThreadExecutor();
            mDao = Database.getDatabase(application).getTrailersDao();
        }

        LiveData<List<TrailerEntity>> getTrailers(final int movieId) {
            mApiClient.getTrailers(new TrailersApiCallback() {
                @Override
                public void onSuccess(final TrailerObject trailer) {
                    mExecutorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            mDao.insertTrailer(new TrailerEntity(movieId, trailer));
                        }
                    });
                }

                @Override
                public void onError(Throwable error) {

                }
            }, movieId, API_KEY, LANGUAGE);
            return mDao.getTrailers(movieId);
        }

    }
}
