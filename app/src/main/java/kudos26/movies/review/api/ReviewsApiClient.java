package kudos26.movies.review.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static kudos26.movies.Constants.BASE_URL_API;

@Singleton
public class ReviewsApiClient {

    private static Retrofit mRetroFit = null;
    private final ReviewsApi mReviewsApi;

    public ReviewsApiClient() {
        if (mRetroFit == null) {
            mRetroFit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .client(new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        mReviewsApi = mRetroFit.create(ReviewsApi.class);
    }

    public void getReviews(final ReviewsApiCallback reviewsApiCallback, int movieId, String apiKey, String language, int page) {
        mReviewsApi.getMovieReviews(movieId, apiKey, language, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ReviewsApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ReviewsApiResponse reviewsApiResponse) {
                        if (reviewsApiResponse != null) {
                            List<Review> reviews = reviewsApiResponse.getResults();
                            for (Review review : reviews) {
                                reviewsApiCallback.onSuccess(review);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
