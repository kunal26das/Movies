package kudos26.movies.review;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static kudos26.movies.Database.TABLE_REVIEWS;
import static kudos26.movies.review.ReviewEntity.COL_ID_MOVIE;

@Dao
public interface ReviewsDao {

    @Query("SELECT * FROM " + TABLE_REVIEWS + " WHERE " + COL_ID_MOVIE + " = :movieId")
    LiveData<List<ReviewEntity>> getReviews(Integer movieId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReview(ReviewEntity reviewEntity);

    @Query("DELETE FROM " + TABLE_REVIEWS)
    void deleteAllReviews();
}
