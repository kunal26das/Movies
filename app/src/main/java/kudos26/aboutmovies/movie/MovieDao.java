package kudos26.aboutmovies.movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static kudos26.aboutmovies.Constants.KEY_FAVORITE;
import static kudos26.aboutmovies.Constants.KEY_ID;
import static kudos26.aboutmovies.Constants.KEY_POPULARITY;
import static kudos26.aboutmovies.Constants.KEY_VOTE_AVERAGE;
import static kudos26.aboutmovies.Constants.TABLE_MOVIES;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + KEY_VOTE_AVERAGE + " DESC")
    LiveData<List<MovieEntity>> getTopRatedMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + KEY_POPULARITY + " DESC")
    LiveData<List<MovieEntity>> getPopularMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " WHERE " + KEY_FAVORITE + " = " + true)
    LiveData<List<MovieEntity>> getFavoriteMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " WHERE " + KEY_ID + " = :id")
    MovieEntity getMovie(Integer id);

    @Query("SELECT " + KEY_FAVORITE + " FROM " + TABLE_MOVIES + " WHERE " + KEY_ID + " = :id")
    Boolean isFavorite(Integer id);

    @Query("UPDATE " + TABLE_MOVIES + " SET " + KEY_FAVORITE + " = :favorite " + " WHERE " + KEY_ID + " = :id")
    void setFavorite(int id, boolean favorite);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(MovieEntity movieEntity);

    @Query("DELETE FROM " + TABLE_MOVIES)
    void deleteAllMovies();
}
