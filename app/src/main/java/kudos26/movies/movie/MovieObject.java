package kudos26.movies.movie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieObject {

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_ADULT = "adult";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VIDEO = "video";
    private static final String KEY_POPULARITY = "popularity";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    private static final String KEY_GENRE_IDS = "genre_ids";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_OVERVIEW = "overview";

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

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
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
    }

    @NonNull
    public Float getVoteAverage() {
        return mVoteAverage;
    }

    @NonNull
    public Boolean getVideo() {
        return mVideo;
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
    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    @NonNull
    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    @Nullable
    public String getBackdropPath() {
        return mBackdropPath;
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }
}
