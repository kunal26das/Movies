package kudos26.movies.room;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import kudos26.movies.movie.MovieEntity;
import kudos26.movies.review.ReviewEntity;
import kudos26.movies.trailer.TrailerEntity;

@androidx.room.Database(entities = {MovieEntity.class, ReviewEntity.class, TrailerEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public static final String TABLE_MOVIES = "movies_table";
    public static final String TABLE_REVIEWS = "reviews_table";
    public static final String TABLE_TRAILERS = "trailers_table";

    private static final String DATABASE = "database";
    private static volatile Database databaseInstance;

    public static Database getDatabase(final Context context) {
        if (databaseInstance == null) {
            synchronized (Database.class) {
                if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseInstance;
    }

    public abstract MoviesDao getMoviesDao();

    public abstract ReviewsDao getReviewsDao();

    public abstract TrailersDao getTrailersDao();
}