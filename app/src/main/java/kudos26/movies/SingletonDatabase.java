package kudos26.movies;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import kudos26.movies.movie.MovieEntity;
import kudos26.movies.review.ReviewEntity;
import kudos26.movies.trailer.TrailerEntity;

@androidx.room.Database(entities = {MovieEntity.class, ReviewEntity.class, TrailerEntity.class}, version = 1, exportSchema = false)
public abstract class SingletonDatabase extends RoomDatabase {

    public abstract SingletonDao getDao();
    private static final String DATABASE = "database";
    private static volatile SingletonDatabase singletonDatabaseInstance;

    public static SingletonDatabase getDatabase(final Context context) {
        if (singletonDatabaseInstance == null) {
            synchronized (SingletonDatabase.class) {
                if (singletonDatabaseInstance == null) {
                    singletonDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            SingletonDatabase.class, DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return singletonDatabaseInstance;
    }
}