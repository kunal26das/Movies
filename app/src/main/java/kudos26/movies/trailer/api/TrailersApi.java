package kudos26.movies.trailer.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrailersApi {

    String PATH_ID = "id";
    String QUERY_API_KEY = "api_key";
    String QUERY_LANGUAGE = "language";
    String ADDRESS_TRAILERS = "{id}/videos";

    @GET(ADDRESS_TRAILERS)
    Single<TrailersApiResponse> getMovieTrailers(@Path(PATH_ID) int movieId,
                                                 @Query(QUERY_API_KEY) String apiKey,
                                                 @Query(QUERY_LANGUAGE) String language);
}
