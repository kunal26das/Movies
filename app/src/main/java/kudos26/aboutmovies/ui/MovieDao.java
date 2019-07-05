package kudos26.aboutmovies.ui;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kudos26.aboutmovies.pojo.MovieEntry;

import static kudos26.aboutmovies.Constants.KEY_POPULARITY;
import static kudos26.aboutmovies.Constants.KEY_VOTE_AVERAGE;
import static kudos26.aboutmovies.Constants.TABLE_MOVIE;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM " + TABLE_MOVIE + " ORDER BY " + KEY_VOTE_AVERAGE + " DESC")
    LiveData<List<MovieEntry>> getTopRatedMovies();

    @Query("SELECT * FROM " + TABLE_MOVIE + " ORDER BY " + KEY_POPULARITY + " DESC")
    LiveData<List<MovieEntry>> getPopularMovies();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(MovieEntry movieEntry);

    @Query("DELETE FROM " + TABLE_MOVIE)
    void deleteAll();
}
