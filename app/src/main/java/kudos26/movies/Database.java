package kudos26.movies;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import kudos26.movies.movie.MoviesDao;
import kudos26.movies.movie.api.Movie;

@androidx.room.Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public static final String TABLE_MOVIES_FAVORITES = "favorite_movies_table";
    private static volatile Database databaseInstance;
    private static final String DATABASE_MOVIES = "movies_database";

    public static Database getDatabase(final Context context) {
        if (databaseInstance == null) {
            synchronized (Database.class) {
                if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, DATABASE_MOVIES)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseInstance;
    }

    public abstract MoviesDao getFavoriteMoviesDao();
}
