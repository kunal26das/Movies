package kudos26.aboutmovies.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.aboutmovies.pojo.MovieEntry;

import static kudos26.aboutmovies.Constants.POPULAR_MOVIES;
import static kudos26.aboutmovies.Constants.TOP_RATED_MOVIES;

class MovieRepository {

    private static MovieDao mMovieDao;
    private MovieDatabase mMovieDatabase;
    private LiveData<List<MovieEntry>> mMovieEntries;

    MovieRepository(Application application) {
        mMovieDatabase = MovieDatabase.getDatabase(application);
        mMovieDao = mMovieDatabase.movieDao();
        mMovieEntries = mMovieDao.getPopularMovies();
    }

    LiveData<List<MovieEntry>> getMovieEntries(int sortCriteria) {
        switch (sortCriteria) {
            case POPULAR_MOVIES: {
                return mMovieDao.getPopularMovies();
            }
            case TOP_RATED_MOVIES: {
                return mMovieDao.getTopRatedMovies();
            }
            default: {
                return null;
            }
        }
    }

    public void fetchPopularMovies(int page) {
        mMovieDatabase.fetchPopularMovies(page);
    }

    public void fetchTopRatedMovies(int page) {
        mMovieDatabase.fetchTopRatedMovies(page);
    }
}
