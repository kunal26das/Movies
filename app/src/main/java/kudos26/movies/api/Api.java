package kudos26.movies.api;

import kudos26.movies.movie.MoviesResponse;
import kudos26.movies.review.ReviewsResponse;
import kudos26.movies.trailer.TrailersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String PATH_ID = "id";
    String QUERY_PAGE = "page";
    String QUERY_API_KEY = "api_key";
    String QUERY_LANGUAGE = "language";
    String ADDRESS_POPULAR = "popular";
    String ADDRESS_TOP_RATED = "top_rated";
    String ADDRESS_REVIEWS = "{id}/reviews";
    String ADDRESS_TRAILERS = "{id}/videos";

    @GET(ADDRESS_POPULAR)
    Call<MoviesResponse> getTopRatedMovies(@Query(QUERY_API_KEY) String apiKey,
                                           @Query(QUERY_LANGUAGE) String language,
                                           @Query(QUERY_PAGE) String page);

    @GET(ADDRESS_TOP_RATED)
    Call<MoviesResponse> getPopularMovies(@Query(QUERY_API_KEY) String apiKey,
                                          @Query(QUERY_LANGUAGE) String language,
                                          @Query(QUERY_PAGE) String page);

    @GET(ADDRESS_REVIEWS)
    Call<ReviewsResponse> getMovieReviews(@Path(PATH_ID) int movieId,
                                          @Query(QUERY_API_KEY) String apiKey,
                                          @Query(QUERY_LANGUAGE) String language,
                                          @Query(QUERY_PAGE) String page);

    @GET(ADDRESS_TRAILERS)
    Call<TrailersResponse> getMovieTrailers(@Path(PATH_ID) int movieId,
                                            @Query(QUERY_API_KEY) String apiKey,
                                            @Query(QUERY_LANGUAGE) String language);
}
