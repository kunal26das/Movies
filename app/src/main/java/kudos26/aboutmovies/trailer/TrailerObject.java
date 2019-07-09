package kudos26.aboutmovies.trailer;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static kudos26.aboutmovies.Constants.KEY_ADDRESS_KEY;
import static kudos26.aboutmovies.Constants.KEY_ID;
import static kudos26.aboutmovies.Constants.KEY_ISO_LANGUAGE;
import static kudos26.aboutmovies.Constants.KEY_ISO_LOCALE;
import static kudos26.aboutmovies.Constants.KEY_NAME;
import static kudos26.aboutmovies.Constants.KEY_SITE;
import static kudos26.aboutmovies.Constants.KEY_SIZE;
import static kudos26.aboutmovies.Constants.KEY_TYPE;

public class TrailerObject {

    @SerializedName(KEY_ID)
    private String mId;

    @SerializedName(KEY_ISO_LANGUAGE)
    private String mIsoLanguage;

    @SerializedName(KEY_ISO_LOCALE)
    private String mIdLocale;

    @SerializedName(KEY_ADDRESS_KEY)
    private String mAddressKey;

    @SerializedName(KEY_NAME)
    private String mName;

    @SerializedName(KEY_SITE)
    private String mSite;

    @SerializedName(KEY_SIZE)
    private Integer mSize;

    @SerializedName(KEY_TYPE)
    private String mType;

    public TrailerObject(@NonNull String mId,
                         @NonNull String mIsoLanguage,
                         @NonNull String mIdLocale,
                         @NonNull String mAddressKey,
                         @NonNull String mName,
                         @NonNull String mSite,
                         @NonNull Integer mSize,
                         @NonNull String mType) {
        this.mId = mId;
        this.mIsoLanguage = mIsoLanguage;
        this.mIdLocale = mIdLocale;
        this.mAddressKey = mAddressKey;
        this.mName = mName;
        this.mSite = mSite;
        this.mSize = mSize;
        this.mType = mType;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getIsoLanguage() {
        return mIsoLanguage;
    }

    @NonNull
    public String getIdLocale() {
        return mIdLocale;
    }

    @NonNull
    public String getAddressKey() {
        return mAddressKey;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getSite() {
        return mSite;
    }

    @NonNull
    public Integer getSize() {
        return mSize;
    }

    @NonNull
    public String getType() {
        return mType;
    }
}
