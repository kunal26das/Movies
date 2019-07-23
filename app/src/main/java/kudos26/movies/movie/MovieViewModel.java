package kudos26.movies.movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kudos26.movies.movie.api.MoviesApiCallback;
import kudos26.movies.movie.api.MoviesApiClient;
import kudos26.movies.room.Database;
import kudos26.movies.room.MoviesDao;

import static kudos26.movies.Constants.API_KEY;
import static kudos26.movies.Constants.EN_US;

public class MovieViewModel extends AndroidViewModel {

    private static final int POPULAR_MOVIES = 1;
    private static final int TOP_RATED_MOVIES = 2;
    private static final int FAVORITE_MOVIES = 3;

    private MovieRepository mMovieRepository;
    private LiveData<List<MovieEntity>> mMovies;
    private MutableLiveData<Integer> mSortCriteria;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
        mSortCriteria = new MutableLiveData<>();
        mSortCriteria.setValue(POPULAR_MOVIES);
        fetchMovies(1);
    }

    public LiveData<List<MovieEntity>> getMovies() {
        updateMoviesLiveData();
        return mMovies;
    }

    public MutableLiveData<Integer> getSortCriteria() {
        return mSortCriteria;
    }

    public MovieEntity getMovie(int id) {
        try {
            return mMovieRepository.getMovie(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fetchMovies(int page) {
        mMovieRepository.fetchMovies(page);
    }

    public boolean isFavorite(int movieId) throws ExecutionException, InterruptedException {
        return mMovieRepository.isFavorite(movieId);
    }

    public void updateFavorite(int movieId) {
        mMovieRepository.updateFavorite(movieId);
    }

    public void switchToPopularMovies() {
        mSortCriteria.setValue(POPULAR_MOVIES);
        updateMoviesLiveData();
    }

    public void switchTopTopRatedMovies() {
        mSortCriteria.setValue(TOP_RATED_MOVIES);
        updateMoviesLiveData();
    }

    public void switchToFavoriteMovies() {
        mSortCriteria.setValue(FAVORITE_MOVIES);
        updateMoviesLiveData();
    }

    private void updateMoviesLiveData() {
        try {
            fetchMovies(1);
            mMovies = mMovieRepository.getMovies();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Repository
    class MovieRepository {

        private MoviesDao mDao;
        private ExecutorService mExecutorService;
        private MoviesApiClient mMoviesApiClient;

        MovieRepository(Application application) {
            mMoviesApiClient = new MoviesApiClient();
            mExecutorService = Executors.newSingleThreadExecutor();
            mDao = Database.getDatabase(application).getMoviesDao();
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

        void fetchMovies(int page) {
            if (mSortCriteria.getValue() != null) {
                if (mSortCriteria.getValue() == POPULAR_MOVIES) {
                    mMoviesApiClient.getPopularMovies(new MoviesApiCallback() {
                        @Override
                        public void onSuccess(final MovieObject movie) {
                            mExecutorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    mDao.insertMovie(new MovieEntity(movie, false));
                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    }, API_KEY, EN_US, page);
                } else if (mSortCriteria.getValue() == TOP_RATED_MOVIES) {
                    mMoviesApiClient.getTopRatedMovies(new MoviesApiCallback() {
                        @Override
                        public void onSuccess(final MovieObject movie) {
                            mExecutorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    mDao.insertMovie(new MovieEntity(movie, false));
                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    }, API_KEY, EN_US, page);
                }
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
