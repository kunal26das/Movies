package kudos26.movies.movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kudos26.movies.movie.api.Movie;

import static kudos26.movies.Database.TABLE_MOVIES_FAVORITES;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM " + TABLE_MOVIES_FAVORITES)
    LiveData<List<Movie>> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

}
