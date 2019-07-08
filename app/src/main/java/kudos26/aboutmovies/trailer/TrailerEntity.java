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
    //@SerializedName(KEY_ID_TRAILER)
    @ColumnInfo(name = KEY_ID_TRAILER)
    private Integer mTrailerId;

    @NonNull
    //@SerializedName(KEY_ID_MOVIE)
    @ColumnInfo(name = KEY_ID_MOVIE)
    private Integer mMovieId;

    @NonNull
    //@SerializedName(KEY_ISO_LANGUAGE)
    @ColumnInfo(name = KEY_ISO_LANGUAGE)
    private String mIsoLanguage;

    @NonNull
    //@SerializedName(KEY_ISO_LOCALE)
    @ColumnInfo(name = KEY_ISO_LOCALE)
    private String mIsoLocale;

    @NonNull
    //@SerializedName(KEY_ADDRESS_KEY)
    @ColumnInfo(name = KEY_ADDRESS_KEY)
    private String mAddressKey;

    @NonNull
    //@SerializedName(KEY_NAME)
    @ColumnInfo(name = KEY_NAME)
    private String mName;

    @NonNull
    //@SerializedName(KEY_SITE)
    @ColumnInfo(name = KEY_SITE)
    private String mSite;

    @NonNull
    //@SerializedName(KEY_SIZE)
    @ColumnInfo(name = KEY_SIZE)
    private Integer mSize;

    @NonNull
    //@SerializedName(KEY_TYPE)
    @ColumnInfo(name = KEY_TYPE)
    private String mType;

    public TrailerEntity(@NonNull Integer mTrailerId,
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
    public Integer getTrailerId() {
        return mTrailerId;
    }

    public void setmReviewId(@NonNull Integer mReviewId) {
        this.mTrailerId = mReviewId;
    }

    @NonNull
    public Integer getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(@NonNull Integer mMovieId) {
        this.mMovieId = mMovieId;
    }

    @NonNull
    public String getmIsoLanguage() {
        return mIsoLanguage;
    }

    public void setmIsoLanguage(@NonNull String mIsoLanguage) {
        this.mIsoLanguage = mIsoLanguage;
    }

    @NonNull
    public String getmIsoLocale() {
        return mIsoLocale;
    }

    public void setmIsoLocale(@NonNull String mIsoLocale) {
        this.mIsoLocale = mIsoLocale;
    }

    @NonNull
    public String getmAddressKey() {
        return mAddressKey;
    }

    public void setmAddressKey(@NonNull String mAddressKey) {
        this.mAddressKey = mAddressKey;
    }

    @NonNull
    public String getmName() {
        return mName;
    }

    public void setmName(@NonNull String mName) {
        this.mName = mName;
    }

    @NonNull
    public String getmSite() {
        return mSite;
    }

    public void setmSite(@NonNull String mSite) {
        this.mSite = mSite;
    }

    @NonNull
    public Integer getmSize() {
        return mSize;
    }

    public void setmSize(@NonNull Integer mSize) {
        this.mSize = mSize;
    }

    @NonNull
    public String getmType() {
        return mType;
    }

    public void setmType(@NonNull String mType) {
        this.mType = mType;
    }
}
