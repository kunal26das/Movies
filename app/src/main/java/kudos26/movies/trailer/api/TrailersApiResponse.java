package kudos26.movies.trailer.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kudos26.movies.trailer.TrailerObject;

public class TrailersApiResponse {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("results")
    private List<TrailerObject> mTrailers;

    public TrailersApiResponse(Integer mId, List<TrailerObject> mTrailers) {
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
