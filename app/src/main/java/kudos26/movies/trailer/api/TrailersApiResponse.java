package kudos26.movies.trailer.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersApiResponse {

    private static final String KEY_ID = "id";
    private static final String KEY_RESULTS = "results";

    @SerializedName(KEY_ID)
    private Integer mId;

    @SerializedName(KEY_RESULTS)
    private List<Trailer> mTrailers;

    public TrailersApiResponse(Integer mId, List<Trailer> mTrailers) {
        this.mId = mId;
        this.mTrailers = mTrailers;
    }

    public Integer getmId() {
        return mId;
    }

    public List<Trailer> getTrailers() {
        return mTrailers;
    }

}
