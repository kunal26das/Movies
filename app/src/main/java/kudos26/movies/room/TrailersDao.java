package kudos26.movies.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kudos26.movies.trailer.TrailerEntity;

import static kudos26.movies.room.Database.TABLE_TRAILERS;
import static kudos26.movies.trailer.TrailerEntity.COL_ID_MOVIE;

@Dao
public interface TrailersDao {

    @Query("SELECT * FROM " + TABLE_TRAILERS + " WHERE " + COL_ID_MOVIE + " = :movieId")
    LiveData<List<TrailerEntity>> getTrailers(Integer movieId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrailer(TrailerEntity reviewEntity);

    @Query("DELETE FROM " + TABLE_TRAILERS)
    void deleteAllTrailers();

}
