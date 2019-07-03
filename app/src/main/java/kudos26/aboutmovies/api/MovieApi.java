package kudos26.aboutmovies.api;

import kudos26.aboutmovies.pojo.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static kudos26.aboutmovies.Constants.API_KEY_QUERY;

public interface MovieApi {

    @GET("movie/popular")
    Call<ApiResponse> getPopularMovies(@Query(API_KEY_QUERY) String apiKey);

}
