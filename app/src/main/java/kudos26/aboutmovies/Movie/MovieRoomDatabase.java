package kudos26.aboutmovies.Movie;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static volatile MovieRoomDatabase INSTANCE;

    static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sMovieDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sMovieDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            super.onOpen(supportSQLiteDatabase);
            new PopulateDatabaseAsyncTask(INSTANCE).execute();
        }

    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private final MovieDao mMovieDao;

        PopulateDatabaseAsyncTask(MovieRoomDatabase movieRoomDatabase) {
            mMovieDao = movieRoomDatabase.movieDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mMovieDao.deleteAll();
            mMovieDao.insertMovie(new Movie("One"));
            mMovieDao.insertMovie(new Movie("Two"));
            return null;
        }

    }

}
