package kudos26.aboutmovies.Movie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mMovieLiveData;

    MovieRepository(Application application) {
        MovieRoomDatabase movieRoomDatabase = MovieRoomDatabase.getDatabase(application);
        mMovieDao = movieRoomDatabase.movieDao();
        mMovieLiveData = mMovieDao.getMovies();
    }

    LiveData<List<Movie>> getMovies() {
        return mMovieLiveData;
    }

    void insert(Movie movie){
        new insertAsyncTask(mMovieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao movieDao) {
            mAsyncTaskDao = movieDao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insertMovie(params[0]);
            return null;
        }

    }

}
