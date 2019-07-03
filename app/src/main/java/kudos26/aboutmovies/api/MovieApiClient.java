package kudos26.aboutmovies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static kudos26.aboutmovies.Constants.BASE_URL;

public class MovieApiClient {

    private static Retrofit mRetroFit = null;

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
