package kudos26.movies.movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kudos26.movies.Database;
import kudos26.movies.movie.api.Movie;
import kudos26.movies.movie.api.MoviesApiCallback;
import kudos26.movies.movie.api.MoviesApiClient;

import static kudos26.movies.Constants.API_KEY;
import static kudos26.movies.Constants.LANGUAGE_EN_US;

public class MovieViewModel extends AndroidViewModel {

    private static final int POPULAR_MOVIES = 1;
    private static final int TOP_RATED_MOVIES = 2;
    private static final int FAVORITE_MOVIES = 3;

    private MovieRepository mMovieRepository;
    private LiveData<List<Movie>> mFavoriteMovies;
    private MutableLiveData<Integer> mSortCriteria;
    private MutableLiveData<List<Movie>> mPopularMovies;
    private MutableLiveData<List<Movie>> mTopRatedMovies;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);

        mPopularMovies = new MutableLiveData<>();
        mPopularMovies.setValue(new ArrayList<>());

        mTopRatedMovies = new MutableLiveData<>();
        mTopRatedMovies.setValue(new ArrayList<>());

        try {
            mFavoriteMovies = mMovieRepository.getFavoriteMovies();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mSortCriteria = new MutableLiveData<>();
        mSortCriteria.setValue(POPULAR_MOVIES);
    }

    public LiveData<List<Movie>> getMovies() {
        return Transformations.switchMap(mSortCriteria, sortBy -> {
            switch (sortBy) {
                case POPULAR_MOVIES: {
                    return mPopularMovies;
                }
                case TOP_RATED_MOVIES: {
                    return mTopRatedMovies;
                }
                case FAVORITE_MOVIES: {
                    return mFavoriteMovies;
                }
            }
            return mPopularMovies;
        });
    }

    private void mutatePopularMovies(Movie movie) {
        List<Movie> tempList = mPopularMovies.getValue();
        if (tempList != null) {
            tempList.add(movie);
        }
        mPopularMovies.setValue(tempList);
    }

    private void mutateTopRatedMovies(Movie movie) {
        List<Movie> tempList = mTopRatedMovies.getValue();
        if (tempList != null) {
            tempList.add(movie);
        }
        mTopRatedMovies.setValue(tempList);
    }

    public void fetchMovies(int page) {
        mMovieRepository.fetchMovies(page);
    }

    public void switchToPopularMovies() {
        mSortCriteria.setValue(POPULAR_MOVIES);
    }

    public void switchTopTopRatedMovies() {
        mSortCriteria.setValue(TOP_RATED_MOVIES);
    }

    public void switchToFavoriteMovies() {
        mSortCriteria.setValue(FAVORITE_MOVIES);
    }

    public void updateFavorite(Movie movie) {
        if (isFavorite(movie)) {
            mMovieRepository.deleteMovie(movie);
        } else {
            mMovieRepository.insertMovie(movie);
        }
    }

    public boolean isFavorite(Movie movie) {
        List<Movie> tempList = mFavoriteMovies.getValue();
        return tempList != null && tempList.contains(movie);
    }

    class MovieRepository {

        private MoviesDao mDao;
        private ExecutorService mExecutorService;
        private MoviesApiClient mMoviesApiClient;

        MovieRepository(Application application) {
            mMoviesApiClient = new MoviesApiClient();
            mExecutorService = Executors.newSingleThreadExecutor();
            mDao = Database.getDatabase(application).getFavoriteMoviesDao();
        }

        void fetchMovies(int page) {
            if (mSortCriteria.getValue() != null) {
                if (mSortCriteria.getValue() == POPULAR_MOVIES) {
                    mMoviesApiClient.getPopularMovies(new MoviesApiCallback() {
                        @Override
                        public void onSuccess(final Movie movie) {
                            mutatePopularMovies(movie);
                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    }, API_KEY, LANGUAGE_EN_US, page);
                } else if (mSortCriteria.getValue() == TOP_RATED_MOVIES) {
                    mMoviesApiClient.getTopRatedMovies(new MoviesApiCallback() {
                        @Override
                        public void onSuccess(final Movie movie) {
                            mutateTopRatedMovies(movie);
                        }

                        @Override
                        public void onFailure(Throwable e) {

                        }
                    }, API_KEY, LANGUAGE_EN_US, page);
                }
            }
        }

        LiveData<List<Movie>> getFavoriteMovies() throws ExecutionException, InterruptedException {
            return mExecutorService.submit(new Callable<LiveData<List<Movie>>>() {
                @Override
                public LiveData<List<Movie>> call() throws Exception {
                    return mDao.getFavoriteMovies();
                }
            }).get();
        }

        void insertMovie(Movie movie) {
            mExecutorService.submit(() -> {
                mDao.insertMovie(movie);
            });
        }

        void deleteMovie(Movie movie) {
            mExecutorService.submit(() -> {
                mDao.deleteMovie(movie);
            });
        }
    }
}
