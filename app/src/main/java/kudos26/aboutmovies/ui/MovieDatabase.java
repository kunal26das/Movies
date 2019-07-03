package kudos26.aboutmovies.ui;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kudos26.aboutmovies.pojo.MovieEntry;

import static kudos26.aboutmovies.Constants.DATABASE_MOVIE;

@Database(entities = {MovieEntry.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static volatile MovieDatabase MOVIE_DATABASE_INSTANCE;

    static MovieDatabase getDatabase(final Context context) {
        if (MOVIE_DATABASE_INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (MOVIE_DATABASE_INSTANCE == null) {
                    MOVIE_DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, DATABASE_MOVIE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return MOVIE_DATABASE_INSTANCE;
    }

    /*private static RoomDatabase.Callback sMovieDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            super.onOpen(supportSQLiteDatabase);
            new PopulateDatabaseAsyncTask().execute();
        }

    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private final MovieDao mMovieDao;

        PopulateDatabaseAsyncTask() {
            mMovieDao = MOVIE_DATABASE_INSTANCE.movieDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }

    }*/

}
