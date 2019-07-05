package kudos26.aboutmovies.api;

import kudos26.aboutmovies.pojo.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    String QUERY_PAGE = "page";
    String QUERY_API_KEY = "api_key";
    String QUERY_LANGUAGE = "language";

    @GET("movie/popular")
    Call<ApiResponse> getPopularMovies(@Query(QUERY_API_KEY) String apiKey,
                                       @Query(QUERY_LANGUAGE) String language,
                                       @Query(QUERY_PAGE) String page);

}
