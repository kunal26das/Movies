package kudos26.aboutmovies.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.aboutmovies.pojo.MovieEntry;

class MovieRepository {

    private static MovieDao mMovieDao;
    private LiveData<List<MovieEntry>> mMovieEntries;

    MovieRepository(Application application) {
        mMovieDao = MovieDatabase.getDatabase(application).movieDao();
        mMovieEntries = mMovieDao.getMovies();
    }

    LiveData<List<MovieEntry>> getMovieEntries() {
        return mMovieEntries;
    }
}
