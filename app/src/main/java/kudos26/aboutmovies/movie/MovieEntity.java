package kudos26.aboutmovies.movie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static kudos26.aboutmovies.Constants.KEY_FAVORITE;
import static kudos26.aboutmovies.Constants.KEY_ID;
import static kudos26.aboutmovies.Constants.KEY_OVERVIEW;
import static kudos26.aboutmovies.Constants.KEY_POPULARITY;
import static kudos26.aboutmovies.Constants.KEY_POSTER_PATH;
import static kudos26.aboutmovies.Constants.KEY_RELEASE_DATE;
import static kudos26.aboutmovies.Constants.KEY_TITLE;
import static kudos26.aboutmovies.Constants.KEY_VOTE_AVERAGE;
import static kudos26.aboutmovies.Constants.TABLE_MOVIES;

@Entity(tableName = TABLE_MOVIES)
public class MovieEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = KEY_ID)
    private Integer mId;

    @NonNull
    @ColumnInfo(name = KEY_TITLE)
    private String mTitle;

    /*@NonNull
    @ColumnInfo(name = KEY_ORIGINAL_TITLE)
    private String mOriginalTitle;*/

    @NonNull
    @ColumnInfo(name = KEY_RELEASE_DATE)
    private String mReleaseDate;

    /*@NonNull
    @ColumnInfo(name = KEY_ADULT)
    private Boolean mAdult;

    @NonNull
    @ColumnInfo(name = KEY_VOTE_COUNT)
    private Integer mVoteCount;*/

    @NonNull
    @ColumnInfo(name = KEY_VOTE_AVERAGE)
    private Float mVoteAverage;

    /*@NonNull
    @ColumnInfo(name = KEY_VIDEO)
    private Boolean mVideo;*/

    @NonNull
    @ColumnInfo(name = KEY_POPULARITY)
    private Float mPopularity;

    @Nullable
    @ColumnInfo(name = KEY_POSTER_PATH)
    private String mPosterPath;

    /*@NonNull
    @ColumnInfo(name = KEY_ORIGINAL_LANGUAGE)
    private String mOriginalLanguage;

    @NonNull
    @ColumnInfo(name = KEY_GENRE_IDS)
    private String mGenreIds;

    @Nullable
    @ColumnInfo(name = KEY_BACKDROP_PATH)
    private String mBackdropPath;*/

    @NonNull
    @ColumnInfo(name = KEY_OVERVIEW)
    private String mOverview;

    @NonNull
    @ColumnInfo(name = KEY_FAVORITE)
    private Boolean mFavorite;

    public MovieEntity(@NonNull Integer mId, @NonNull String mTitle, @NonNull String mReleaseDate, @NonNull Float mVoteAverage, @NonNull Float mPopularity, @NonNull String mPosterPath, @NonNull String mOverview, @NonNull Boolean favorite) {
        this.mId = mId;
        this.mTitle = mTitle;
        /*this.mOriginalTitle = mOriginalTitle;*/
        this.mReleaseDate = mReleaseDate;
        /*this.mAdult = mAdult;
        this.mVoteCount = mVoteCount;*/
        this.mVoteAverage = mVoteAverage;
        /*this.mVideo = mVideo;*/
        this.mPopularity = mPopularity;
        this.mPosterPath = mPosterPath;
        /*this.mOriginalLanguage = mOriginalLanguage;
        this.mGenreIds = mGenreIds;
        this.mBackdropPath = mBackdropPath;*/
        this.mOverview = mOverview;
        this.mFavorite = favorite;
    }

    public MovieEntity(@NonNull MovieObject movie, @NonNull Boolean favorite) {
        this.mId = movie.getId();
        this.mTitle = movie.getTitle();
        /*this.mOriginalTitle = movieObject.getOriginalTitle();*/
        this.mReleaseDate = movie.getReleaseDate();
        /*this.mAdult = movieObject.getAdult();
        this.mVoteCount = movieObject.getVoteCount();*/
        this.mVoteAverage = movie.getVoteAverage();
        /*this.mVideo = movieObject.getVideo();*/
        this.mPopularity = movie.getPopularity();
        this.mPosterPath = movie.getPosterPath();
        /*this.mGenreIds = movieObject.getGenreIds().toString();
        this.mBackdropPath = movieObject.getBackdropPath();*/
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

    /*@NonNull
    public String getOriginalTitle() {
        return mOriginalTitle;
    }*/

    @NonNull
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /*@NonNull
    public Boolean getAdult() {
        return mAdult;
    }

    @NonNull
    public Integer getVoteCount() {
        return mVoteCount;
    }*/

    @NonNull
    public Float getVoteAverage() {
        return mVoteAverage;
    }

    /*@NonNull
    public Boolean getVideo() {
        return mVideo;
    }*/

    @NonNull
    public Float getPopularity() {
        return mPopularity;
    }

    @Nullable
    public String getPosterPath() {
        return mPosterPath;
    }

    /*@NonNull
    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    @NonNull
    public String getGenreIds() {
        return mGenreIds;
    }

    @Nullable
    public String getBackdropPath() {
        return mBackdropPath;
    }*/

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

    /*public void setmOriginalTitle(@NonNull String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }*/

    public void setmReleaseDate(@NonNull String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    /*public void setmAdult(@NonNull Boolean mAdult) {
        this.mAdult = mAdult;
    }

    public void setmVoteCount(@NonNull Integer mVoteCount) {
        this.mVoteCount = mVoteCount;
    }*/

    public void setmVoteAverage(@NonNull Float mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    /*public void setmVideo(@NonNull Boolean mVideo) {
        this.mVideo = mVideo;
    }*/

    public void setmPopularity(@NonNull Float mPopularity) {
        this.mPopularity = mPopularity;
    }

    public void setmPosterPath(@Nullable String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    /*public void setmOriginalLanguage(@NonNull String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    public void setmGenreIds(@NonNull String mGenreIds) {
        this.mGenreIds = mGenreIds;
    }

    public void setmBackdropPath(@Nullable String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }*/

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
