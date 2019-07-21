package kudos26.movies.trailer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersResponse {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("results")
    private List<TrailerObject> mTrailers;

    public TrailersResponse(Integer mId, List<TrailerObject> mTrailers) {
        this.mId = mId;
        this.mTrailers = mTrailers;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public List<TrailerObject> getTrailers() {
        return mTrailers;
    }

    public void setmTrailers(List<TrailerObject> mTrailers) {
        this.mTrailers = mTrailers;
    }
}
