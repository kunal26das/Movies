package kudos26.aboutmovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiClient {

    private static Retrofit mRetroFit = null;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit getClient() {
        if (mRetroFit == null) {
            mRetroFit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetroFit;
    }
}
