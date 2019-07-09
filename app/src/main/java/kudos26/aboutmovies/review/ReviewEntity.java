package kudos26.aboutmovies.review;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static kudos26.aboutmovies.Constants.KEY_AUTHOR;
import static kudos26.aboutmovies.Constants.KEY_CONTENT;
import static kudos26.aboutmovies.Constants.KEY_ID_MOVIE;
import static kudos26.aboutmovies.Constants.KEY_ID_REVIEW;
import static kudos26.aboutmovies.Constants.KEY_URL;
import static kudos26.aboutmovies.Constants.TABLE_REVIEWS;

@Entity(tableName = TABLE_REVIEWS)
public class ReviewEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = KEY_ID_REVIEW)
    private String mReviewId;

    @NonNull
    @ColumnInfo(name = KEY_ID_MOVIE)
    private Integer mMovieId;

    @NonNull
    @ColumnInfo(name = KEY_AUTHOR)
    private String mAuthor;

    @NonNull
    @ColumnInfo(name = KEY_URL)
    private String mUrl;

    @NonNull
    @ColumnInfo(name = KEY_CONTENT)
    private String mContent;

    public ReviewEntity(@NonNull String mReviewId,
                        @NonNull Integer mMovieId,
                        @NonNull String mAuthor,
                        @NonNull String mUrl,
                        @NonNull String content) {
        this.mReviewId = mReviewId;
        this.mMovieId = mMovieId;
        this.mContent = content;
        this.mAuthor = mAuthor;
        this.mUrl = mUrl;
    }

    public ReviewEntity(@NonNull Integer movieId,
                        @NonNull ReviewObject review) {
        this.mMovieId = movieId;
        this.mUrl = review.getmUrl();
        this.mReviewId = review.getmId();
        this.mAuthor = review.getmAuthor();
        this.mContent = review.getmContent();
    }

    @NonNull
    public String getReviewId() {
        return mReviewId;
    }

    public void setReviewId(@NonNull String mReviewId) {
        this.mReviewId = mReviewId;
    }

    @NonNull
    public Integer getMovieId() {
        return mMovieId;
    }

    public void setMovieId(@NonNull Integer mMovieId) {
        this.mMovieId = mMovieId;
    }

    @NonNull
    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(@NonNull String mAuthor) {
        this.mAuthor = mAuthor;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    public void setUrl(@NonNull String mUrl) {
        this.mUrl = mUrl;
    }

    @NonNull
    public String getContent() {
        return mContent;
    }

    public void setContent(@NonNull String mContent) {
        this.mContent = mContent;
    }
}
