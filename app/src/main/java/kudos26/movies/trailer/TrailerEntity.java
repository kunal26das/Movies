package kudos26.movies.trailer;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kudos26.movies.trailer.api.TrailerObject;

import static kudos26.movies.Database.TABLE_TRAILERS;

@Entity(tableName = TABLE_TRAILERS)
public class TrailerEntity {

    public static final String COL_ID_MOVIE = "id_movie";
    public static final String COL_ID_TRAILER = "id_trailer";
    public static final String COL_ISO_LANGUAGE = "iso_639_1";
    public static final String COL_ISO_LOCALE = "iso_3166_1";
    public static final String COL_ADDRESS_KEY = "key";
    public static final String COL_NAME = "name";
    public static final String COL_SITE = "site";
    public static final String COL_SIZE = "size";
    public static final String COL_TYPE = "type";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = COL_ID_TRAILER)
    private String mTrailerId;

    @NonNull
    @ColumnInfo(name = COL_ID_MOVIE)
    private Integer mMovieId;

    @NonNull
    @ColumnInfo(name = COL_ISO_LANGUAGE)
    private String mIsoLanguage;

    @NonNull
    @ColumnInfo(name = COL_ISO_LOCALE)
    private String mIsoLocale;

    @NonNull
    @ColumnInfo(name = COL_ADDRESS_KEY)
    private String mAddressKey;

    @NonNull
    @ColumnInfo(name = COL_NAME)
    private String mName;

    @NonNull
    @ColumnInfo(name = COL_SITE)
    private String mSite;

    @NonNull
    @ColumnInfo(name = COL_SIZE)
    private Integer mSize;

    @NonNull
    @ColumnInfo(name = COL_TYPE)
    private String mType;

    public TrailerEntity(@NonNull String mTrailerId,
                         @NonNull Integer mMovieId,
                         @NonNull String mIsoLanguage,
                         @NonNull String mIsoLocale,
                         @NonNull String mAddressKey,
                         @NonNull String mName,
                         @NonNull String mSite,
                         @NonNull Integer mSize,
                         @NonNull String mType) {
        this.mTrailerId = mTrailerId;
        this.mMovieId = mMovieId;
        this.mIsoLanguage = mIsoLanguage;
        this.mIsoLocale = mIsoLocale;
        this.mAddressKey = mAddressKey;
        this.mName = mName;
        this.mSite = mSite;
        this.mSize = mSize;
        this.mType = mType;
    }

    public TrailerEntity(@NonNull Integer movieId,
                         @NonNull TrailerObject trailer) {
        mMovieId = movieId;
        mTrailerId = trailer.getId();
        mIsoLanguage = trailer.getIsoLanguage();
        mIsoLocale = trailer.getIdLocale();
        mAddressKey = trailer.getAddressKey();
        mName = trailer.getName();
        mSite = trailer.getSite();
        mSize = trailer.getSize();
        mType = trailer.getType();
    }

    @NonNull
    public String getTrailerId() {
        return mTrailerId;
    }

    public void setReviewId(@NonNull String mReviewId) {
        this.mTrailerId = mReviewId;
    }

    @NonNull
    public Integer getMovieId() {
        return mMovieId;
    }

    public void setMovieId(@NonNull Integer mMovieId) {
        this.mMovieId = mMovieId;
    }

    @NonNull
    public String getIsoLanguage() {
        return mIsoLanguage;
    }

    public void setIsoLanguage(@NonNull String mIsoLanguage) {
        this.mIsoLanguage = mIsoLanguage;
    }

    @NonNull
    public String getIsoLocale() {
        return mIsoLocale;
    }

    public void setIsoLocale(@NonNull String mIsoLocale) {
        this.mIsoLocale = mIsoLocale;
    }

    @NonNull
    public String getAddressKey() {
        return mAddressKey;
    }

    public void setAddressKey(@NonNull String mAddressKey) {
        this.mAddressKey = mAddressKey;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String mName) {
        this.mName = mName;
    }

    @NonNull
    public String getSite() {
        return mSite;
    }

    public void setSite(@NonNull String mSite) {
        this.mSite = mSite;
    }

    @NonNull
    public Integer getSize() {
        return mSize;
    }

    public void setSize(@NonNull Integer mSize) {
        this.mSize = mSize;
    }

    @NonNull
    public String getType() {
        return mType;
    }

    public void setType(@NonNull String mType) {
        this.mType = mType;
    }
}
