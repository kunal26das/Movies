package kudos26.aboutmovies.trailer;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static kudos26.aboutmovies.Constants.KEY_ADDRESS_KEY;
import static kudos26.aboutmovies.Constants.KEY_ID_MOVIE;
import static kudos26.aboutmovies.Constants.KEY_ID_TRAILER;
import static kudos26.aboutmovies.Constants.KEY_ISO_LANGUAGE;
import static kudos26.aboutmovies.Constants.KEY_ISO_LOCALE;
import static kudos26.aboutmovies.Constants.KEY_NAME;
import static kudos26.aboutmovies.Constants.KEY_SITE;
import static kudos26.aboutmovies.Constants.KEY_SIZE;
import static kudos26.aboutmovies.Constants.KEY_TYPE;
import static kudos26.aboutmovies.Constants.TABLE_TRAILERS;

@Entity(tableName = TABLE_TRAILERS)
public class TrailerEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = KEY_ID_TRAILER)
    private String mTrailerId;

    @NonNull
    @ColumnInfo(name = KEY_ID_MOVIE)
    private Integer mMovieId;

    @NonNull
    @ColumnInfo(name = KEY_ISO_LANGUAGE)
    private String mIsoLanguage;

    @NonNull
    @ColumnInfo(name = KEY_ISO_LOCALE)
    private String mIsoLocale;

    @NonNull
    @ColumnInfo(name = KEY_ADDRESS_KEY)
    private String mAddressKey;

    @NonNull
    @ColumnInfo(name = KEY_NAME)
    private String mName;

    @NonNull
    @ColumnInfo(name = KEY_SITE)
    private String mSite;

    @NonNull
    @ColumnInfo(name = KEY_SIZE)
    private Integer mSize;

    @NonNull
    @ColumnInfo(name = KEY_TYPE)
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
