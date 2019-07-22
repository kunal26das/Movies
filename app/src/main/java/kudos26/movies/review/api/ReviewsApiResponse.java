package kudos26.movies.review.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kudos26.movies.review.ReviewObject;

public class ReviewsApiResponse {

    @SerializedName("id")
    private int mId;

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("total_pages")
    private int mTotalPages;

    @SerializedName("results")
    private List<ReviewObject> mReviews;

    public ReviewsApiResponse(int mId,
                              int mPage,
                              int mTotalResults,
                              int mTotalPages,
                              List<ReviewObject> mReviews) {
        this.mPage = mPage;
        this.mTotalResults = mTotalResults;
        this.mTotalPages = mTotalPages;
        this.mReviews = mReviews;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int mTotalResults) {
        this.mTotalResults = mTotalResults;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int mTotalPages) {
        this.mTotalPages = mTotalPages;
    }

    public List<ReviewObject> getResults() {
        return mReviews;
    }

    public void setResults(List<ReviewObject> mResults) {
        this.mReviews = mResults;
    }


}
