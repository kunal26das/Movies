package kudos26.aboutmovies.pojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static kudos26.aboutmovies.Constants.KEY_ADULT;
import static kudos26.aboutmovies.Constants.KEY_BACKDROP_PATH;
import static kudos26.aboutmovies.Constants.KEY_GENRE_IDS;
import static kudos26.aboutmovies.Constants.KEY_ID;
import static kudos26.aboutmovies.Constants.KEY_ORIGINAL_LANGUAGE;
import static kudos26.aboutmovies.Constants.KEY_ORIGINAL_TITLE;
import static kudos26.aboutmovies.Constants.KEY_OVERVIEW;
import static kudos26.aboutmovies.Constants.KEY_POPULARITY;
import static kudos26.aboutmovies.Constants.KEY_POSTER_PATH;
import static kudos26.aboutmovies.Constants.KEY_RELEASE_DATE;
import static kudos26.aboutmovies.Constants.KEY_TITLE;
import static kudos26.aboutmovies.Constants.KEY_VIDEO;
import static kudos26.aboutmovies.Constants.KEY_VOTE_AVERAGE;
import static kudos26.aboutmovies.Constants.KEY_VOTE_COUNT;

public class MovieObject {

    @SerializedName(KEY_ID)
    private Integer mId;

    @SerializedName(KEY_TITLE)
    private String mTitle;

    @SerializedName(KEY_ORIGINAL_TITLE)
    private String mOriginalTitle;

    @SerializedName(KEY_RELEASE_DATE)
    private String mReleaseDate;

    @SerializedName(KEY_ADULT)
    private Boolean mAdult;

    @SerializedName(KEY_VOTE_COUNT)
    private Integer mVoteCount;

    @SerializedName(KEY_VOTE_AVERAGE)
    private Float mVoteAverage;

    @SerializedName(KEY_VIDEO)
    private Boolean mVideo;

    @SerializedName(KEY_POPULARITY)
    private Float mPopularity;

    @SerializedName(KEY_POSTER_PATH)
    private String mPosterPath;

    @SerializedName(KEY_ORIGINAL_LANGUAGE)
    private String mOriginalLanguage;

    @SerializedName(KEY_GENRE_IDS)
    private List<Integer> mGenreIds;

    @SerializedName(KEY_BACKDROP_PATH)
    private String mBackdropPath;

    @SerializedName(KEY_OVERVIEW)
    private String mOverview;

    public MovieObject(@NonNull Integer mId,
                       @NonNull String mTitle,
                       @NonNull String mOriginalTitle,
                       @NonNull String mReleaseDate,
                       @NonNull Boolean mAdult,
                       @NonNull Integer mVoteCount,
                       @NonNull Float mVoteAverage,
                       @NonNull Boolean mVideo,
                       @NonNull Float mPopularity,
                       @Nullable String mPosterPath,
                       @NonNull String mOriginalLanguage,
                       @NonNull List<Integer> mGenreIds,
                       @Nullable String mBackdropPath,
                       @NonNull String mOverview) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mOriginalTitle = mOriginalTitle;
        this.mReleaseDate = mReleaseDate;
        this.mAdult = mAdult;
        this.mVoteCount = mVoteCount;
        this.mVoteAverage = mVoteAverage;
        this.mVideo = mVideo;
        this.mPopularity = mPopularity;
        this.mPosterPath = mPosterPath;
        this.mOriginalLanguage = mOriginalLanguage;
        this.mGenreIds = mGenreIds;
        this.mBackdropPath = mBackdropPath;
        this.mOverview = mOverview;
    }

    @NonNull
    public Integer getId() {
        return mId;
    }

    public void setId(@NonNull Integer mId) {
        this.mId = mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    @NonNull
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(@NonNull String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    @NonNull
    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(@NonNull String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    @NonNull
    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(@NonNull Boolean mAdult) {
        this.mAdult = mAdult;
    }

    @NonNull
    public Integer getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(@NonNull Integer mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    @NonNull
    public Float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(@NonNull Float mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    @NonNull
    public Boolean getVideo() {
        return mVideo;
    }

    public void setVideo(@NonNull Boolean mVideo) {
        this.mVideo = mVideo;
    }

    @NonNull
    public Float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(@NonNull Float mPopularity) {
        this.mPopularity = mPopularity;
    }

    @Nullable
    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(@Nullable String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    @NonNull
    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(@NonNull String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    @NonNull
    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(@NonNull List<Integer> mGenreIds) {
        this.mGenreIds = mGenreIds;
    }

    @Nullable
    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(@Nullable String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }

    public void setOverview(@NonNull String mOverview) {
        this.mOverview = mOverview;
    }
}
