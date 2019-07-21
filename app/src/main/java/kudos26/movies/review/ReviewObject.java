package kudos26.movies.review;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static kudos26.movies.Constants.KEY_AUTHOR;
import static kudos26.movies.Constants.KEY_CONTENT;
import static kudos26.movies.Constants.KEY_ID;
import static kudos26.movies.Constants.KEY_URL;

public class ReviewObject {

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
