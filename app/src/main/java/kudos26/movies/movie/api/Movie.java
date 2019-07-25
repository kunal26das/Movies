package kudos26.movies.movie.api;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static kudos26.movies.Database.TABLE_MOVIES_FAVORITES;

@Entity(tableName = TABLE_MOVIES_FAVORITES)
public class Movie implements Parcelable {

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_RELEASE_DATE = "release_date";
    public static final String KEY_ADULT = "adult";
    public static final String KEY_VOTE_COUNT = "vote_count";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_POPULARITY = "popularity";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_ORIGINAL_LANGUAGE = "original_language";
    public static final String KEY_GENRE_IDS = "genre_ids";
    public static final String KEY_BACKDROP_PATH = "backdrop_path";
    public static final String KEY_OVERVIEW = "overview";
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = KEY_ID)
    @SerializedName(KEY_ID)
    private Integer mId;
    @NonNull
    @ColumnInfo(name = KEY_TITLE)
    @SerializedName(KEY_TITLE)
    private String mTitle;
    @NonNull
    @ColumnInfo(name = KEY_ORIGINAL_TITLE)
    @SerializedName(KEY_ORIGINAL_TITLE)
    private String mOriginalTitle;
    @NonNull
    @ColumnInfo(name = KEY_RELEASE_DATE)
    @SerializedName(KEY_RELEASE_DATE)
    private String mReleaseDate;
    @NonNull
    @ColumnInfo(name = KEY_ADULT)
    @SerializedName(KEY_ADULT)
    private Boolean mAdult;
    @NonNull
    @ColumnInfo(name = KEY_VOTE_COUNT)
    @SerializedName(KEY_VOTE_COUNT)
    private Integer mVoteCount;
    @NonNull
    @ColumnInfo(name = KEY_VOTE_AVERAGE)
    @SerializedName(KEY_VOTE_AVERAGE)
    private Float mVoteAverage;
    @NonNull
    @ColumnInfo(name = KEY_VIDEO)
    @SerializedName(KEY_VIDEO)
    private Boolean mVideo;
    @NonNull
    @ColumnInfo(name = KEY_POPULARITY)
    @SerializedName(KEY_POPULARITY)
    private Float mPopularity;
    @Nullable
    @ColumnInfo(name = KEY_POSTER_PATH)
    @SerializedName(KEY_POSTER_PATH)
    private String mPosterPath;
    @NonNull
    @ColumnInfo(name = KEY_ORIGINAL_LANGUAGE)
    @SerializedName(KEY_ORIGINAL_LANGUAGE)
    private String mOriginalLanguage;
    @Ignore
    @NonNull
    @Expose(deserialize = false)
    @ColumnInfo(name = KEY_GENRE_IDS)
    @SerializedName(KEY_GENRE_IDS)
    private List<Integer> mGenreIds;
    @Nullable
    @ColumnInfo(name = KEY_BACKDROP_PATH)
    @SerializedName(KEY_BACKDROP_PATH)
    private String mBackdropPath;
    @NonNull
    @ColumnInfo(name = KEY_OVERVIEW)
    @SerializedName(KEY_OVERVIEW)
    private String mOverview;

    public Movie(@NonNull Integer mId,
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
                 //@NonNull List<Integer> mGenreIds,
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
        //this.mGenreIds = mGenreIds;
        this.mBackdropPath = mBackdropPath;
        this.mOverview = mOverview;
    }

    protected Movie(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mTitle = in.readString();
        this.mOriginalTitle = in.readString();
        this.mReleaseDate = in.readString();
        this.mAdult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mVoteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mVoteAverage = (Float) in.readValue(Float.class.getClassLoader());
        this.mVideo = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mPopularity = (Float) in.readValue(Float.class.getClassLoader());
        this.mPosterPath = in.readString();
        this.mOriginalLanguage = in.readString();
        this.mGenreIds = new ArrayList<Integer>();
        in.readList(this.mGenreIds, Integer.class.getClassLoader());
        this.mBackdropPath = in.readString();
        this.mOverview = in.readString();
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

    /*@NonNull
    public List<Integer> getGenreIds() {
        return mGenreIds;
    }*/

    @Nullable
    public String getPosterPath() {
        return mPosterPath;
    }

    @NonNull
    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    @Nullable
    public String getBackdropPath() {
        return mBackdropPath;
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mReleaseDate);
        dest.writeValue(this.mAdult);
        dest.writeValue(this.mVoteCount);
        dest.writeValue(this.mVoteAverage);
        dest.writeValue(this.mVideo);
        dest.writeValue(this.mPopularity);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mOriginalLanguage);
        dest.writeList(this.mGenreIds);
        dest.writeString(this.mBackdropPath);
        dest.writeString(this.mOverview);
    }
}
