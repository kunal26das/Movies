package kudos26.movies.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kudos26.movies.review.ReviewEntity;

import static kudos26.movies.review.ReviewEntity.COL_ID_MOVIE;
import static kudos26.movies.room.Database.TABLE_REVIEWS;

@Dao
public interface ReviewsDao {

    @Query("SELECT * FROM " + TABLE_REVIEWS + " WHERE " + COL_ID_MOVIE + " = :movieId")
    LiveData<List<ReviewEntity>> getReviews(Integer movieId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReview(ReviewEntity reviewEntity);

    @Query("DELETE FROM " + TABLE_REVIEWS)
    void deleteAllReviews();
}
