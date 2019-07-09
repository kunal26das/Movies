package kudos26.aboutmovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kudos26.aboutmovies.movie.MovieEntity;
import kudos26.aboutmovies.review.ReviewEntity;
import kudos26.aboutmovies.trailer.TrailerEntity;

import static kudos26.aboutmovies.Constants.KEY_FAVORITE;
import static kudos26.aboutmovies.Constants.KEY_ID;
import static kudos26.aboutmovies.Constants.KEY_ID_MOVIE;
import static kudos26.aboutmovies.Constants.KEY_POPULARITY;
import static kudos26.aboutmovies.Constants.KEY_VOTE_AVERAGE;
import static kudos26.aboutmovies.Constants.TABLE_MOVIES;
import static kudos26.aboutmovies.Constants.TABLE_REVIEWS;
import static kudos26.aboutmovies.Constants.TABLE_TRAILERS;

@Dao
public interface SingletonDao {

    @Query("SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + KEY_VOTE_AVERAGE + " DESC")
    LiveData<List<MovieEntity>> getTopRatedMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " ORDER BY " + KEY_POPULARITY + " DESC")
    LiveData<List<MovieEntity>> getPopularMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " WHERE " + KEY_FAVORITE + " = " + 1)
    LiveData<List<MovieEntity>> getFavoriteMovies();

    @Query("SELECT * FROM " + TABLE_MOVIES + " WHERE " + KEY_ID + " = :id")
    MovieEntity getMovie(Integer id);

    @Query("SELECT " + KEY_FAVORITE + " FROM " + TABLE_MOVIES + " WHERE " + KEY_ID + " = :id")
    Boolean isFavorite(Integer id);

    @Query("UPDATE " + TABLE_MOVIES + " SET " + KEY_FAVORITE + " = :favorite " + " WHERE " + KEY_ID + " = :id")
    void setFavorite(Integer id, Boolean favorite);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(MovieEntity movie);

    @Update
    void update(MovieEntity movie);

    @Query("DELETE FROM " + TABLE_MOVIES)
    void deleteAllMovies();

    @Query("SELECT * FROM " + TABLE_TRAILERS + " WHERE " + KEY_ID_MOVIE + " = :movieId")
    LiveData<List<TrailerEntity>> getTrailers(Integer movieId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrailer(TrailerEntity reviewEntity);

    @Query("DELETE FROM " + TABLE_TRAILERS)
    void deleteAllTrailers();

    @Query("SELECT * FROM " + TABLE_REVIEWS + " WHERE " + KEY_ID_MOVIE + " = :movieId")
    LiveData<List<ReviewEntity>> getReviews(Integer movieId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReview(ReviewEntity reviewEntity);

    @Query("DELETE FROM " + TABLE_REVIEWS)
    void deleteAllReviews();
}
