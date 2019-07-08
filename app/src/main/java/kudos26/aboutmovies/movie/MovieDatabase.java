package kudos26.aboutmovies.movie;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

import kudos26.aboutmovies.api.Api;
import kudos26.aboutmovies.api.ApiClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static kudos26.aboutmovies.Constants.API_KEY;
import static kudos26.aboutmovies.Constants.DATABASE;
import static kudos26.aboutmovies.Constants.EN_US;
import static kudos26.aboutmovies.Constants.POPULAR_MOVIES;
import static kudos26.aboutmovies.Constants.TOP_RATED_MOVIES;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static volatile MovieDatabase MOVIE_DATABASE_INSTANCE;
    private static RoomDatabase.Callback MovieFetchCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            super.onOpen(supportSQLiteDatabase);
            new FetchTopRatedMoviesTask(MOVIE_DATABASE_INSTANCE).execute(1);
        }
    };

    static MovieDatabase getDatabase(final Context context) {
        if (MOVIE_DATABASE_INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (MOVIE_DATABASE_INSTANCE == null) {
                    MOVIE_DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, DATABASE)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(MovieFetchCallback)
                            .build();
                }
            }
        }
        return MOVIE_DATABASE_INSTANCE;
    }

    public MovieEntity getMovieEntry(int id) {
        return movieDao().getMovie(id);
    }

    public void getMoviesPage(int sortCriteria, int page) {
        switch (sortCriteria) {
            case POPULAR_MOVIES: {
                new FetchTopRatedMoviesTask(MOVIE_DATABASE_INSTANCE).execute(page);
            }
            case TOP_RATED_MOVIES: {
                new FetchPopularMoviesTask(MOVIE_DATABASE_INSTANCE).execute(page);
            }
        }
    }

    private static class FetchTopRatedMoviesTask extends AsyncTask<Integer, Void, Void> {

        private final MovieDao mDao;

        FetchTopRatedMoviesTask(MovieDatabase movieDatabase) {
            mDao = movieDatabase.movieDao();
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
                            mDao.insertMovie(new MovieEntity(movieObject));
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

        private final MovieDao mDao;

        FetchPopularMoviesTask(MovieDatabase movieDatabase) {
            mDao = movieDatabase.movieDao();
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
                            mDao.insertMovie(new MovieEntity(movie));
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
}