package kudos26.aboutmovies.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import java.util.List;

import kudos26.aboutmovies.api.MovieWorker;
import kudos26.aboutmovies.pojo.MovieEntry;

public class MovieRepository {

    private static MovieDao mMovieDao;
    private WorkManager mWorkManager;
    private LiveData<List<MovieEntry>> mMovieEntries;

    MovieRepository(Application application) {
        mMovieDao = MovieDatabase.getDatabase(application).movieDao();
        mWorkManager = WorkManager.getInstance();
        mMovieEntries = mMovieDao.getMovies();

        WorkContinuation workContinuation = mWorkManager.beginUniqueWork(
                "SYNC_DATABASE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(MovieWorker.class));
        workContinuation.enqueue();
    }

    LiveData<List<MovieEntry>> getMovieEntries() {
        return mMovieEntries;
    }

    /*private static class SyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }

    }*/

}
