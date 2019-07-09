package kudos26.aboutmovies.movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.aboutmovies.SingletonDao;
import kudos26.aboutmovies.SingletonDatabase;

import static kudos26.aboutmovies.Constants.FAVORITE_MOVIES;
import static kudos26.aboutmovies.Constants.POPULAR_MOVIES;
import static kudos26.aboutmovies.Constants.TOP_RATED_MOVIES;

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

        private static SingletonDao mSingletonDao;
        private SingletonDatabase mSingletonDatabase;

        MovieRepository(Application application) {
            mSingletonDatabase = SingletonDatabase.getDatabase(application);
            mSingletonDao = mSingletonDatabase.getDao();
        }

        LiveData<List<MovieEntity>> getMovies(int sortCriteria) {
            switch (sortCriteria) {
                case POPULAR_MOVIES: {
                    return mSingletonDao.getPopularMovies();
                }
                case TOP_RATED_MOVIES: {
                    return mSingletonDao.getTopRatedMovies();
                }
                case FAVORITE_MOVIES: {
                    return null;
                }
                default: {
                    return null;
                }
            }
        }

        MovieEntity getMovie(int movieId) {
            return mSingletonDao.getMovie(movieId);
        }

        void getMoviesPage(int sortCriteria, int page) {
            mSingletonDatabase.getMoviesPage(sortCriteria, page);
        }

        boolean isMovieFavorite(int movieId) {
            return mSingletonDao.isFavorite(movieId);
        }

        void setFavorite(int movieId) {
            mSingletonDao.setFavorite(movieId, true);
        }

        void setNotFavorite(int movieId) {
            mSingletonDao.setFavorite(movieId, false);
        }

        void updateMovie(MovieEntity movie) {
            mSingletonDao.update(movie);
        }

    }
}
