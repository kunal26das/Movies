package kudos26.movies.movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kudos26.movies.Database;
import kudos26.movies.movie.api.MovieObject;
import kudos26.movies.movie.api.MoviesApiCallback;
import kudos26.movies.movie.api.MoviesApiClient;

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

        mMovies = Transformations.switchMap(mSortCriteria,
                sortBy -> {
                    try {
                        switch (sortBy) {
                            case POPULAR_MOVIES: {
                                return mMovieRepository.getPopularMovies();
                            }
                            case TOP_RATED_MOVIES: {
                                return mMovieRepository.getTopRatedMovies();
                            }
                            case FAVORITE_MOVIES: {
                                return mMovieRepository.getFavoriteMovies();
                            }
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
        return mMovies;
    }

    public void fetchMovies(int page) {
        mMovieRepository.fetchMovies(page);
    }

    public void switchToPopularMovies() {
        mSortCriteria.setValue(POPULAR_MOVIES);
        try {
            mMovies = mMovieRepository.getPopularMovies();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchTopTopRatedMovies() {
        mSortCriteria.setValue(TOP_RATED_MOVIES);
        try {
            mMovies = mMovieRepository.getTopRatedMovies();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchToFavoriteMovies() {
        mSortCriteria.setValue(FAVORITE_MOVIES);
        try {
            mMovies = mMovieRepository.getPopularMovies();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isFavorite(int movieId) throws ExecutionException, InterruptedException {
        return mMovieRepository.isFavorite(movieId);
    }

    public void updateFavorite(int movieId) {
        mMovieRepository.updateFavorite(movieId);
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

        LiveData<List<MovieEntity>> getPopularMovies() throws ExecutionException, InterruptedException {
            return mExecutorService.submit(new Callable<LiveData<List<MovieEntity>>>() {
                @Override
                public LiveData<List<MovieEntity>> call() {
                    return mDao.getPopularMovies();
                }
            }).get();
        }

        LiveData<List<MovieEntity>> getTopRatedMovies() throws ExecutionException, InterruptedException {
            return mExecutorService.submit(new Callable<LiveData<List<MovieEntity>>>() {
                @Override
                public LiveData<List<MovieEntity>> call() {
                    return mDao.getTopRatedMovies();
                }
            }).get();
        }

        LiveData<List<MovieEntity>> getFavoriteMovies() throws ExecutionException, InterruptedException {
            return mExecutorService.submit(new Callable<LiveData<List<MovieEntity>>>() {
                @Override
                public LiveData<List<MovieEntity>> call() {
                    return mDao.getFavoriteMovies();
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
