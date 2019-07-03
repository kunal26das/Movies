package kudos26.aboutmovies.ui;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kudos26.aboutmovies.pojo.MovieEntry;

import static kudos26.aboutmovies.Constants.TABLE_MOVIE;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM " + TABLE_MOVIE)
    LiveData<List<MovieEntry>> getMovies();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(MovieEntry movieEntry);

    @Query("DELETE FROM " + TABLE_MOVIE)
    void deleteAll();
}
