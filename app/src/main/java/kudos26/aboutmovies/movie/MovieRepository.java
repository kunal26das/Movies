package kudos26.aboutmovies.movie;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import static kudos26.aboutmovies.Constants.POPULAR_MOVIES;
import static kudos26.aboutmovies.Constants.TOP_RATED_MOVIES;

class MovieRepository {

    private static MovieDao mMovieDao;
    private MovieDatabase mMovieDatabase;
    //private LiveData<List<MovieEntity>> mMovieEntries;

    MovieRepository(Application application) {
        mMovieDatabase = MovieDatabase.getDatabase(application);
        mMovieDao = mMovieDatabase.movieDao();
        //mMovieEntries = mMovieDao.getPopularMovies();
    }

    LiveData<List<MovieEntity>> getMovies(int sortCriteria) {
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

    MovieEntity getMovie(int id) {
        return mMovieDatabase.getMovieEntry(id);
    }

    void getMoviesPage(int sortCriteria, int page) {
        mMovieDatabase.getMoviesPage(sortCriteria, page);
    }

}
