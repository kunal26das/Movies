package kudos26.movies.review.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kudos26.movies.review.ReviewObject;

public class ReviewsApiResponse {

    private static final String KEY_ID = "id";
    private static final String KEY_PAGE = "page";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_TOTAL_PAGES = "total_pages";
    private static final String KEY_TOTAL_RESULTS = "total_results";

    @SerializedName(KEY_ID)
    private int mId;

    @SerializedName(KEY_PAGE)
    private int mPage;

    @SerializedName(KEY_TOTAL_RESULTS)
    private int mTotalResults;

    @SerializedName(KEY_TOTAL_PAGES)
    private int mTotalPages;

    @SerializedName(KEY_RESULTS)
    private List<ReviewObject> mReviews;

    public ReviewsApiResponse(int mId,
                              int mPage,
                              int mTotalResults,
                              int mTotalPages,
                              List<ReviewObject> mReviews) {
        this.mId = mId;
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
