package kudos26.movies.movie.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {

    String QUERY_PAGE = "page";
    String QUERY_API_KEY = "api_key";
    String QUERY_LANGUAGE = "language";
    String ADDRESS_POPULAR = "popular";
    String ADDRESS_TOP_RATED = "top_rated";

    @GET(ADDRESS_TOP_RATED)
    Single<MoviesApiResponse> getTopRatedMovies(@Query(QUERY_API_KEY) String apiKey,
                                                @Query(QUERY_LANGUAGE) String language,
                                                @Query(QUERY_PAGE) String page);

    @GET(ADDRESS_POPULAR)
    Single<MoviesApiResponse> getPopularMovies(@Query(QUERY_API_KEY) String apiKey,
                                               @Query(QUERY_LANGUAGE) String language,
                                               @Query(QUERY_PAGE) String page);

}
