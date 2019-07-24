package kudos26.movies.trailer.api;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TrailerObject {

    private static final String KEY_ID = "id";
    private static final String KEY_ISO_LANGUAGE = "iso_639_1";
    private static final String KEY_ISO_LOCALE = "iso_3166_1";
    private static final String KEY_ADDRESS_KEY = "key";
    private static final String KEY_NAME = "name";
    private static final String KEY_SITE = "site";
    private static final String KEY_SIZE = "size";
    private static final String KEY_TYPE = "type";

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
