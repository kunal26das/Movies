package kudos26.movies.review;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kudos26.movies.review.api.ReviewObject;

import static kudos26.movies.Database.TABLE_REVIEWS;

@Entity(tableName = TABLE_REVIEWS)
public class ReviewEntity {

    public static final String COL_URL = "url";
    public static final String COL_AUTHOR = "author";
    public static final String COL_CONTENT = "content";
    public static final String COL_ID_MOVIE = "id_movie";
    public static final String COL_ID_REVIEW = "id_review";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = COL_ID_REVIEW)
    private String mReviewId;

    @NonNull
    @ColumnInfo(name = COL_ID_MOVIE)
    private Integer mMovieId;

    @NonNull
    @ColumnInfo(name = COL_AUTHOR)
    private String mAuthor;

    @NonNull
    @ColumnInfo(name = COL_URL)
    private String mUrl;

    @NonNull
    @ColumnInfo(name = COL_CONTENT)
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
