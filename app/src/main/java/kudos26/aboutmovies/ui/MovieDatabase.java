package kudos26.aboutmovies.ui;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

import kudos26.aboutmovies.api.MovieApi;
import kudos26.aboutmovies.api.MovieApiClient;
import kudos26.aboutmovies.pojo.ApiResponse;
import kudos26.aboutmovies.pojo.MovieEntry;
import kudos26.aboutmovies.pojo.MovieObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static kudos26.aboutmovies.Constants.API_KEY;
import static kudos26.aboutmovies.Constants.LANGUAGE;

@Database(entities = {MovieEntry.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static final String DATABASE_MOVIE = "movie_database";
    private static volatile MovieDatabase MOVIE_DATABASE_INSTANCE;
    private static RoomDatabase.Callback MovieFetchCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new MovieFetchTask(MOVIE_DATABASE_INSTANCE).execute();
        }
    };

    static MovieDatabase getDatabase(final Context context) {
        if (MOVIE_DATABASE_INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (MOVIE_DATABASE_INSTANCE == null) {
                    MOVIE_DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, DATABASE_MOVIE)
                            .fallbackToDestructiveMigration()
                            .addCallback(MovieFetchCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return MOVIE_DATABASE_INSTANCE;
    }

    private static class MovieFetchTask extends AsyncTask<Void, Void, Void> {

        private final MovieDao mDao;

        MovieFetchTask(MovieDatabase movieDatabase) {
            mDao = movieDatabase.movieDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            MovieApi movieApi = MovieApiClient.getClient().create(MovieApi.class);
            Call<ApiResponse> movieJsonResponseCall = movieApi.getPopularMovies(API_KEY, LANGUAGE, String.valueOf(1));
            movieJsonResponseCall.enqueue(new retrofit2.Callback<ApiResponse>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    ApiResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<MovieObject> movieArray = responseBody.getResults();
                        for (MovieObject movieObject : movieArray) {
                            mDao.insertMovie(new MovieEntry(movieObject));
                        }
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                }
            });
            return null;
        }
    }
}
