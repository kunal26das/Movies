package kudos26.movies.movie;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kudos26.movies.SingletonDao;
import kudos26.movies.SingletonDatabase;
import kudos26.movies.movie.api.MoviesApiCallback;
import kudos26.movies.movie.api.MoviesApiClient;

import static kudos26.movies.Constants.API_KEY;
import static kudos26.movies.Constants.EN_US;
import static kudos26.movies.Constants.FAVORITE_MOVIES;
import static kudos26.movies.Constants.POPULAR_MOVIES;
import static kudos26.movies.Constants.TOP_RATED_MOVIES;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;
    private MutableLiveData<Integer> mSortCriteria;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
        mSortCriteria = new MutableLiveData<>();
        mSortCriteria.setValue(POPULAR_MOVIES);
    }

    public LiveData<List<MovieEntity>> getMovies() throws ExecutionException, InterruptedException {
        return mMovieRepository.getMovies();
    }

    public MovieEntity getMovie(int id) throws ExecutionException, InterruptedException {
        return mMovieRepository.getMovie(id);
    }

    public void fetchMovies(int page) {
        mMovieRepository.fetchMovies(mSortCriteria, page);
    }

    public boolean isFavorite(int movieId) throws ExecutionException, InterruptedException {
        return mMovieRepository.isFavorite(movieId);
    }

    public void updateFavorite(int movieId) {
        mMovieRepository.updateFavorite(movieId);
    }

    public void setSortCriteria(int sortCriteria) {
        switch (sortCriteria) {
            case POPULAR_MOVIES: {
                mSortCriteria.setValue(sortCriteria);
                Log.i("Sort Criteria", "POPULAR_MOVIES");
                break;
            }
            case TOP_RATED_MOVIES: {
                mSortCriteria.setValue(sortCriteria);
                Log.i("Sort Criteria", "TOP_RATED_MOVIES");
                break;
            }
            case FAVORITE_MOVIES: {
                mSortCriteria.setValue(sortCriteria);
                Log.i("Sort Criteria", "FAVORITE_MOVIES");
                break;
            }
            default: {
            }
        }
    }

    // Repository
    class MovieRepository {

        private SingletonDao mDao;
        private ExecutorService mExecutorService;
        private MoviesApiClient mMoviesApiClient;

        MovieRepository(Application application) {
            mMoviesApiClient = new MoviesApiClient();
            mExecutorService = Executors.newSingleThreadExecutor();
            mDao = SingletonDatabase.getDatabase(application).getDao();
        }

        LiveData<List<MovieEntity>> getMovies() throws ExecutionException, InterruptedException {
            return mExecutorService.submit(new Callable<LiveData<List<MovieEntity>>>() {
                @Override
                public LiveData<List<MovieEntity>> call() {
                    if (mSortCriteria.getValue() != null) {
                        switch (mSortCriteria.getValue()) {
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
                    return null;
                }
            }).get();
        }

        MovieEntity getMovie(final int movieId) throws ExecutionException, InterruptedException {
            return mExecutorService.submit(new Callable<MovieEntity>() {
                @Override
                public MovieEntity call() throws Exception {
                    return mDao.getMovie(movieId);
                }
            }).get();
        }

        void fetchMovies(LiveData<Integer> sortCriteria, int page) {
            if (sortCriteria.getValue() != null) {
                mMoviesApiClient.getMovies(new MoviesApiCallback() {
                    @Override
                    public void onSuccess(MovieObject movie) {
                        mDao.insertMovie(new MovieEntity(movie, false));
                    }

                    @Override
                    public void onFailure() {
                    }
                }, sortCriteria.getValue(), API_KEY, EN_US, page);
            }
        }

        boolean isFavorite(final int movieId) throws ExecutionException, InterruptedException {
            return mExecutorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return mDao.isFavorite(movieId);
                }
            }).get();
        }

        void updateFavorite(final int movieId) {
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    mDao.updateFavorite(movieId);
                }
            });
        }
    }
}
