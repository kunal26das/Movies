package kudos26.aboutmovies.review;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static kudos26.aboutmovies.Constants.KEY_ID_MOVIE;
import static kudos26.aboutmovies.Constants.TABLE_REVIEWS;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM " + TABLE_REVIEWS + " WHERE " + KEY_ID_MOVIE + " = :movieId")
    LiveData<List<ReviewEntity>> getReviews(Integer movieId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReview(ReviewEntity reviewEntity);

    @Query("DELETE FROM " + TABLE_REVIEWS)
    void deleteAllReviews();
}
