package kudos26.movies.movie.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesApiResponse {

    private static final String KEY_PAGE = "page";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_TOTAL_PAGES = "total_pages";
    private static final String KEY_TOTAL_RESULTS = "total_results";

    @SerializedName(KEY_PAGE)
    private int mPage;

    @SerializedName(KEY_TOTAL_RESULTS)
    private int mTotalResults;

    @SerializedName(KEY_TOTAL_PAGES)
    private int mTotalPages;

    @SerializedName(KEY_RESULTS)
    private List<Movie> mResults;

    public MoviesApiResponse(int mPage,
                             int mTotalResults,
                             int mTotalPages,
                             List<Movie> mResults) {
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

    public List<Movie> getResults() {
        return mResults;
    }

    public void setResults(List<Movie> mResults) {
        this.mResults = mResults;
    }
}
