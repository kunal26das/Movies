package kudos26.movies.review.api;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ReviewObject {

    private static final String KEY_ID = "id";
    private static final String KEY_URL = "url";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    @SerializedName(KEY_ID)
    private String mId;

    @SerializedName(KEY_CONTENT)
    private String mContent;

    @SerializedName(KEY_AUTHOR)
    private String mAuthor;

    @SerializedName(KEY_URL)
    private String mUrl;

    public ReviewObject(@NonNull String mId,
                        @NonNull String mContent,
                        @NonNull String mAuthor,
                        @NonNull String mUrl) {
        this.mId = mId;
        this.mContent = mContent;
        this.mAuthor = mAuthor;
        this.mUrl = mUrl;
    }

    @NonNull
    public String getmId() {
        return mId;
    }

    @NonNull
    public String getmContent() {
        return mContent;
    }

    @NonNull
    public String getmAuthor() {
        return mAuthor;
    }

    @NonNull
    public String getmUrl() {
        return mUrl;
    }
}
