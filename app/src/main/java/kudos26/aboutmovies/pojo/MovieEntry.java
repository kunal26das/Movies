package kudos26.aboutmovies.pojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static kudos26.aboutmovies.Constants.KEY_ID;
import static kudos26.aboutmovies.Constants.KEY_POPULARITY;
import static kudos26.aboutmovies.Constants.KEY_POSTER_PATH;
import static kudos26.aboutmovies.Constants.KEY_TITLE;
import static kudos26.aboutmovies.Constants.KEY_VOTE_AVERAGE;
import static kudos26.aboutmovies.Constants.TABLE_MOVIE;

@Entity(tableName = TABLE_MOVIE)
public class MovieEntry {

    @NonNull
    @PrimaryKey
    @SerializedName(KEY_ID)
    @ColumnInfo(name = KEY_ID)
    private Integer mId;

    @NonNull
    @SerializedName(KEY_TITLE)
    @ColumnInfo(name = KEY_TITLE)
    private String mTitle;

    /*@NonNull
    @SerializedName(KEY_ORIGINAL_TITLE)
    @ColumnInfo(name = KEY_ORIGINAL_TITLE)
    private String mOriginalTitle;

    @NonNull
    @SerializedName(KEY_RELEASE_DATE)
    @ColumnInfo(name = KEY_RELEASE_DATE)
    private String mReleaseDate;

    @NonNull
    @SerializedName(KEY_ADULT)
    @ColumnInfo(name = KEY_ADULT)
    private Boolean mAdult;

    @NonNull
    @SerializedName(KEY_VOTE_COUNT)
    @ColumnInfo(name = KEY_VOTE_COUNT)
    private Integer mVoteCount;*/

    @NonNull
    @SerializedName(KEY_VOTE_AVERAGE)
    @ColumnInfo(name = KEY_VOTE_AVERAGE)
    private Float mVoteAverage;

    /*@NonNull
    @SerializedName(KEY_VIDEO)
    @ColumnInfo(name = KEY_VIDEO)
    private Boolean mVideo;*/

    @NonNull
    @SerializedName(KEY_POPULARITY)
    @ColumnInfo(name = KEY_POPULARITY)
    private Float mPopularity;

    @Nullable
    @SerializedName(KEY_POSTER_PATH)
    @ColumnInfo(name = KEY_POSTER_PATH)
    private String mPosterPath;

    /*@NonNull
    @SerializedName(KEY_ORIGINAL_LANGUAGE)
    @ColumnInfo(name = KEY_ORIGINAL_LANGUAGE)
    private String mOriginalLanguage;

    @NonNull
    @SerializedName(KEY_GENRE_IDS)
    @ColumnInfo(name = KEY_GENRE_IDS)
    private String mGenreIds;

    @Nullable
    @SerializedName(KEY_BACKDROP_PATH)
    @ColumnInfo(name = KEY_BACKDROP_PATH)
    private String mBackdropPath;

    @NonNull
    @SerializedName(KEY_OVERVIEW)
    @ColumnInfo(name = KEY_OVERVIEW)
    private String mOverview;*/

    public MovieEntry(@NonNull Integer mId, @NonNull String mTitle, @NonNull Float mVoteAverage, @NonNull Float mPopularity, @NonNull String mPosterPath) {
        this.mId = mId;
        this.mTitle = mTitle;
        /*this.mOriginalTitle = mOriginalTitle;
        this.mReleaseDate = mReleaseDate;
        this.mAdult = mAdult;
        this.mVoteCount = mVoteCount;*/
        this.mVoteAverage = mVoteAverage;
        /*this.mVideo = mVideo;*/
        this.mPopularity = mPopularity;
        this.mPosterPath = mPosterPath;
        /*this.mOriginalLanguage = mOriginalLanguage;
        this.mGenreIds = mGenreIds;
        this.mBackdropPath = mBackdropPath;
        this.mOverview = mOverview;*/
    }

    public MovieEntry(@NonNull MovieObject movieObject) {
        this.mId = movieObject.getId();
        this.mTitle = movieObject.getTitle();
        /*this.mOriginalTitle = movieObject.getOriginalTitle();
        this.mReleaseDate = movieObject.getReleaseDate();
        this.mAdult = movieObject.getAdult();
        this.mVoteCount = movieObject.getVoteCount();*/
        this.mVoteAverage = movieObject.getVoteAverage();
        /*this.mVideo = movieObject.getVideo();*/
        this.mPopularity = movieObject.getPopularity();
        this.mPosterPath = movieObject.getPosterPath();
        /*this.mGenreIds = movieObject.getGenreIds().toString();
        this.mBackdropPath = movieObject.getBackdropPath();
        this.mOverview = movieObject.getOverview();*/
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
    }

    @NonNull
    public String getReleaseDate() {
        return mReleaseDate;
    }

    @NonNull
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
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }*/

    public void setmId(@NonNull Integer mId) {
        this.mId = mId;
    }

    public void setmTitle(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    /*public void setmOriginalTitle(@NonNull String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public void setmReleaseDate(@NonNull String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public void setmAdult(@NonNull Boolean mAdult) {
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
    }

    public void setmOverview(@NonNull String mOverview) {
        this.mOverview = mOverview;
    }*/
}
