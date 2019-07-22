package kudos26.movies.review.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewsApi {

    String PATH_ID = "id";
    String QUERY_PAGE = "page";
    String QUERY_API_KEY = "api_key";
    String QUERY_LANGUAGE = "language";
    String ADDRESS_REVIEWS = "{id}/reviews";

    @GET(ADDRESS_REVIEWS)
    Single<ReviewsApiResponse> getMovieReviews(@Path(PATH_ID) int movieId,
                                               @Query(QUERY_API_KEY) String apiKey,
                                               @Query(QUERY_LANGUAGE) String language,
                                               @Query(QUERY_PAGE) String page);
}
