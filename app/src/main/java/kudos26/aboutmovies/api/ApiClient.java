package kudos26.aboutmovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static Retrofit mRetroFit = null;

    public static Retrofit getApiClient() {
        if (mRetroFit == null) {
            mRetroFit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetroFit;
    }
}
