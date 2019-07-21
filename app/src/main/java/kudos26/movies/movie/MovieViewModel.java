package kudos26.movies.movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.movies.SingletonDao;
import kudos26.movies.SingletonDatabase;

import static kudos26.movies.Constants.FAVORITE_MOVIES;
import static kudos26.movies.Constants.POPULAR_MOVIES;
import static kudos26.movies.Constants.TOP_RATED_MOVIES;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
    }

    public LiveData<List<MovieEntity>> getMovieLiveData(int sortCriteria) {
        return mMovieRepository.getMovies(sortCriteria);
    }

    public MovieEntity getMovieEntry(int id) {
        return mMovieRepository.getMovie(id);
    }

    public void getMoviesPage(int sortCriteria, int page) {
        mMovieRepository.getMoviesPage(sortCriteria, page);
    }

    public boolean isMovieFavorite(int movieId) {
        return mMovieRepository.isMovieFavorite(movieId);
    }

    public void setFavorite(int movieId) {
        mMovieRepository.setFavorite(movieId);
    }

    public void setNotFavorite(int movieId) {
        mMovieRepository.setNotFavorite(movieId);
    }

    public void updateMovie(MovieEntity movie) {
        mMovieRepository.updateMovie(movie);
    }

    static class MovieRepository {

        private static SingletonDao mDao;
        private SingletonDatabase mDatabase;

        MovieRepository(Application application) {
            mDatabase = SingletonDatabase.getDatabase(application);
            mDao = mDatabase.getDao();
        }

        LiveData<List<MovieEntity>> getMovies(int sortCriteria) {
            mDatabase.getMoviesPage(sortCriteria, 1);
            switch (sortCriteria) {
                case POPULAR_MOVIES: {
                    return mDao.getPopularMovies();
                }
                case TOP_RATED_MOVIES: {
                    return mDao.getTopRatedMovies();
                }
                case FAVORITE_MOVIES: {
                    return mDao.getFavoriteMovies();
                }
                default: {
                    return null;
                }
            }
        }

        MovieEntity getMovie(int movieId) {
            return mDao.getMovie(movieId);
        }

        void getMoviesPage(int sortCriteria, int page) {
            mDatabase.getMoviesPage(sortCriteria, page);
        }

        boolean isMovieFavorite(int movieId) {
            return mDao.isFavorite(movieId);
        }

        void setFavorite(int movieId) {
            mDao.setFavorite(movieId, true);
        }

        void setNotFavorite(int movieId) {
            mDao.setFavorite(movieId, false);
        }

        void updateMovie(MovieEntity movie) {
            mDao.update(movie);
        }

    }
}
