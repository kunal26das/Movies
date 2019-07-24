package kudos26.movies.movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static kudos26.movies.Database.TABLE_MOVIES;
import static kudos26.movies.movie.MovieEntity.COL_FAVORITE;
import static kudos26.movies.movie.MovieEntity.COL_ID;
import static kudos26.movies.movie.MovieEntity.COL_POPULARITY;
import static kudos26.movies.movie.MovieEntity.COL_VOTE_AVERAGE;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + COL_VOTE_AVERAGE + " DESC")
    LiveData<List<MovieEntity>> getTopRatedMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + COL_POPULARITY + " DESC")
    LiveData<List<MovieEntity>> getPopularMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " WHERE " + COL_FAVORITE + " = " + 1)
    LiveData<List<MovieEntity>> getFavoriteMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " WHERE " + COL_ID + " = :id")
    MovieEntity getMovie(Integer id);

    @Query("SELECT " + COL_FAVORITE + " FROM " + TABLE_MOVIES + " WHERE " + COL_ID + " = :id")
    Boolean isFavorite(Integer id);

    @Query("UPDATE " + TABLE_MOVIES + " SET " + COL_FAVORITE + " = CASE WHEN " + COL_FAVORITE + " = 1 THEN " + " 0 WHEN " + COL_FAVORITE + " = 0 THEN 1 END WHERE " + COL_ID + " = :id")
    void updateFavorite(Integer id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(MovieEntity movie);

    @Query("DELETE FROM " + TABLE_MOVIES)
    void deleteAllMovies();

}
