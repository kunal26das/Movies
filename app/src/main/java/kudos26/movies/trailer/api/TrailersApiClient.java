package kudos26.movies.trailer.api;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kudos26.movies.trailer.TrailerObject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static kudos26.movies.Constants.BASE_URL_API;

@Singleton
public class TrailersApiClient {

    private static Retrofit mRetroFit = null;
    private final TrailersApi mTrailersApi;

    public TrailersApiClient() {
        if (mRetroFit == null) {
            mRetroFit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        mTrailersApi = mRetroFit.create(TrailersApi.class);
    }

    public void getTrailers(final TrailersApiCallback trailersApiCallback, int movieId, String apiKey, String language) {
        mTrailersApi.getMovieTrailers(movieId, apiKey, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TrailersApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TrailersApiResponse trailersApiResponse) {
                        if (trailersApiResponse != null) {
                            List<TrailerObject> trailers = trailersApiResponse.getTrailers();
                            for (TrailerObject trailer : trailers) {
                                trailersApiCallback.onSuccess(trailer);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        trailersApiCallback.onError(e);
                    }
                });
    }
}
