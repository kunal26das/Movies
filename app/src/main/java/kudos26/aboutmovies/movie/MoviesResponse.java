package kudos26.aboutmovies.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("total_pages")
    private int mTotalPages;

    @SerializedName("results")
    private List<MovieObject> mResults;

    public MoviesResponse(int mPage,
                          int mTotalResults,
                          int mTotalPages,
                          List<MovieObject> mResults) {
        this.mPage = mPage;
        this.mTotalResults = mTotalResults;
        this.mTotalPages = mTotalPages;
        this.mResults = mResults;
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

    public List<MovieObject> getResults() {
        return mResults;
    }

    public void setResults(List<MovieObject> mResults) {
        this.mResults = mResults;
    }
}
