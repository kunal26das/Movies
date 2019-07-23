package kudos26.movies.movie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static kudos26.movies.room.Database.TABLE_MOVIES;

@Entity(tableName = TABLE_MOVIES)
public class MovieEntity {

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_ORIGINAL_TITLE = "original_title";
    public static final String COL_RELEASE_DATE = "release_date";
    public static final String COL_ADULT = "adult";
    public static final String COL_VOTE_COUNT = "vote_count";
    public static final String COL_VOTE_AVERAGE = "vote_average";
    public static final String COL_VIDEO = "video";
    public static final String COL_POPULARITY = "popularity";
    public static final String COL_POSTER_PATH = "poster_path";
    public static final String COL_ORIGINAL_LANGUAGE = "original_language";
    public static final String COL_GENRE_IDS = "genre_ids";
    public static final String COL_BACKDROP_PATH = "backdrop_path";
    public static final String COL_OVERVIEW = "overview";
    public static final String COL_FAVORITE = "favorite";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = COL_ID)
    private Integer mId;

    @NonNull
    @ColumnInfo(name = COL_TITLE)
    private String mTitle;

    @NonNull
    @ColumnInfo(name = COL_RELEASE_DATE)
    private String mReleaseDate;

    @NonNull
    @ColumnInfo(name = COL_VOTE_AVERAGE)
    private Float mVoteAverage;

    @NonNull
    @ColumnInfo(name = COL_POPULARITY)
    private Float mPopularity;

    @Nullable
    @ColumnInfo(name = COL_POSTER_PATH)
    private String mPosterPath;

    @NonNull
    @ColumnInfo(name = COL_OVERVIEW)
    private String mOverview;

    @NonNull
    @ColumnInfo(name = COL_FAVORITE)
    private Boolean mFavorite;

    public MovieEntity(@NonNull Integer mId, @NonNull String mTitle, @NonNull String mReleaseDate, @NonNull Float mVoteAverage, @NonNull Float mPopularity, @NonNull String mPosterPath, @NonNull String mOverview, @NonNull Boolean favorite) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mReleaseDate = mReleaseDate;
        this.mVoteAverage = mVoteAverage;
        this.mPopularity = mPopularity;
        this.mPosterPath = mPosterPath;
        this.mOverview = mOverview;
        this.mFavorite = favorite;
    }

    public MovieEntity(@NonNull MovieObject movie, @NonNull Boolean favorite) {
        this.mId = movie.getId();
        this.mTitle = movie.getTitle();
        this.mReleaseDate = movie.getReleaseDate();
        this.mVoteAverage = movie.getVoteAverage();
        this.mPopularity = movie.getPopularity();
        this.mPosterPath = movie.getPosterPath();
        this.mOverview = movie.getOverview();
        this.mFavorite = favorite;
    }

    @NonNull
    public Integer getId() {
        return mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getReleaseDate() {
        return mReleaseDate;
    }

    @NonNull
    public Float getVoteAverage() {
        return mVoteAverage;
    }

    @NonNull
    public Float getPopularity() {
        return mPopularity;
    }

    @Nullable
    public String getPosterPath() {
        return mPosterPath;
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }

    public void setmId(@NonNull Integer mId) {
        this.mId = mId;
    }

    public void setmTitle(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmReleaseDate(@NonNull String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public void setmVoteAverage(@NonNull Float mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public void setmPopularity(@NonNull Float mPopularity) {
        this.mPopularity = mPopularity;
    }

    public void setmPosterPath(@Nullable String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public void setmOverview(@NonNull String mOverview) {
        this.mOverview = mOverview;
    }

    @NonNull
    public Boolean isFavorite() {
        return mFavorite;
    }

    public void setFavourite(@NonNull Boolean mFavourite) {
        this.mFavorite = mFavourite;
    }
}
