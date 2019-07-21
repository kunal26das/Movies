package kudos26.movies;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

import kudos26.movies.api.Api;
import kudos26.movies.api.ApiClient;
import kudos26.movies.movie.MovieEntity;
import kudos26.movies.movie.MovieObject;
import kudos26.movies.movie.MoviesResponse;
import kudos26.movies.review.ReviewEntity;
import kudos26.movies.review.ReviewObject;
import kudos26.movies.review.ReviewsResponse;
import kudos26.movies.trailer.TrailerEntity;
import kudos26.movies.trailer.TrailerObject;
import kudos26.movies.trailer.TrailersResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static kudos26.movies.Constants.API_KEY;
import static kudos26.movies.Constants.EN_US;
import static kudos26.movies.Constants.POPULAR_MOVIES;
import static kudos26.movies.Constants.TOP_RATED_MOVIES;

@androidx.room.Database(entities = {MovieEntity.class, ReviewEntity.class, TrailerEntity.class}, version = 1, exportSchema = false)
public abstract class SingletonDatabase extends RoomDatabase {

    private static final String DATABASE = "database";
    private static volatile SingletonDatabase singletonDatabaseInstance;
    private static RoomDatabase.Callback MovieFetchCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            super.onOpen(supportSQLiteDatabase);
            new FetchTopRatedMoviesTask(singletonDatabaseInstance).execute(1);
        }
    };

    public static SingletonDatabase getDatabase(final Context context) {
        if (singletonDatabaseInstance == null) {
            synchronized (SingletonDatabase.class) {
                if (singletonDatabaseInstance == null) {
                    singletonDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            SingletonDatabase.class, DATABASE)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(MovieFetchCallback)
                            .build();
                }
            }
        }
        return singletonDatabaseInstance;
    }

    public abstract SingletonDao getDao();

    public MovieEntity getMovieEntry(int id) {
        return getDao().getMovie(id);
    }

    public void getMoviesPage(int sortCriteria, int page) {
        switch (sortCriteria) {
            case POPULAR_MOVIES: {
                new FetchTopRatedMoviesTask(singletonDatabaseInstance).execute(page);
            }
            case TOP_RATED_MOVIES: {
                new FetchPopularMoviesTask(singletonDatabaseInstance).execute(page);
            }
        }
    }

    public void fetchMovieTrailers(int movieId) {
        new FetchMovieTrailers(singletonDatabaseInstance).execute(movieId);
    }

    public void fetchMovieReviews(int movieId) {
        new FetchMovieReviews(singletonDatabaseInstance).execute(movieId);
    }

    private static class FetchTopRatedMoviesTask extends AsyncTask<Integer, Void, Void> {

        private final SingletonDao mDao;

        FetchTopRatedMoviesTask(SingletonDatabase singletonDatabase) {
            mDao = singletonDatabase.getDao();
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            Api api = ApiClient.getApiClient().create(Api.class);
            Call<MoviesResponse> movieJsonResponseCall = api.getTopRatedMovies(API_KEY, EN_US, String.valueOf(params[0]));
            movieJsonResponseCall.enqueue(new retrofit2.Callback<MoviesResponse>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    MoviesResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<MovieObject> movieArray = responseBody.getResults();
                        for (MovieObject movieObject : movieArray) {
                            mDao.insertMovie(new MovieEntity(movieObject, false));
                        }
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                }
            });
            return null;
        }
    }

    private static class FetchPopularMoviesTask extends AsyncTask<Integer, Void, Void> {

        private final SingletonDao mDao;

        FetchPopularMoviesTask(SingletonDatabase singletonDatabase) {
            mDao = singletonDatabase.getDao();
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            Api api = ApiClient.getApiClient().create(Api.class);
            Call<MoviesResponse> moviesResponseCall = api.getPopularMovies(API_KEY, EN_US, String.valueOf(params[0]));
            moviesResponseCall.enqueue(new retrofit2.Callback<MoviesResponse>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    MoviesResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<MovieObject> movieArray = responseBody.getResults();
                        for (MovieObject movie : movieArray) {
                            mDao.insertMovie(new MovieEntity(movie, false));
                        }
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                }
            });
            return null;
        }
    }

    private static class FetchMovieTrailers extends AsyncTask<Integer, Void, Void> {

        private final SingletonDao mDao;

        FetchMovieTrailers(SingletonDatabase singletonDatabase) {
            mDao = singletonDatabase.getDao();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            final Integer movieId = params[0];
            Api api = ApiClient.getApiClient().create(Api.class);
            Call<TrailersResponse> trailerResponseCall = api.getMovieTrailers(movieId, API_KEY, EN_US);
            trailerResponseCall.enqueue(new retrofit2.Callback<TrailersResponse>() {

                @Override
                @EverythingIsNonNull
                public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                    TrailersResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<TrailerObject> trailerArray = responseBody.getTrailers();
                        for (TrailerObject trailer : trailerArray) {
                            mDao.insertTrailer(new TrailerEntity(movieId, trailer));
                        }
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<TrailersResponse> call, Throwable t) {

                }
            });
            return null;
        }
    }

    private static class FetchMovieReviews extends AsyncTask<Integer, Void, Void> {

        private final SingletonDao mDao;

        FetchMovieReviews(SingletonDatabase singletonDatabase) {
            mDao = singletonDatabase.getDao();
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            final Integer movieId = params[0];
            Api api = ApiClient.getApiClient().create(Api.class);
            Call<ReviewsResponse> reviewsResponseCall = api.getMovieReviews(movieId, API_KEY, EN_US, "1");
            reviewsResponseCall.enqueue(new retrofit2.Callback<ReviewsResponse>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                    ReviewsResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<ReviewObject> reviewArray = responseBody.getResults();
                        for (ReviewObject review : reviewArray) {
                            mDao.insertReview(new ReviewEntity(movieId, review));
                        }
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ReviewsResponse> call, Throwable t) {

                }
            });
            return null;
        }
    }
}