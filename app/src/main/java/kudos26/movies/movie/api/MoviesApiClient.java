package kudos26.movies.movie.api;

import android.app.Application;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kudos26.movies.dagger.AppController;
import retrofit2.Retrofit;

@Singleton
public class MoviesApiClient {

    //private static Retrofit mRetroFit = null;
    private final MoviesApi mMoviesApi;
    //private Application application;

    @Inject
    Retrofit retrofit;

    public MoviesApiClient(Application application) {
        ((AppController) application).getAppComponent().inject(this);
        /*if (mRetroFit == null) {
            mRetroFit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .client(new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }*/
        mMoviesApi = retrofit.create(MoviesApi.class);
        //this.application = application;
    }

    public void getPopularMovies(final MoviesApiCallback moviesApiCallback, String apiKey, String language, int page) {
        mMoviesApi.getPopularMovies(apiKey, language, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MoviesApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MoviesApiResponse moviesApiResponse) {
                        if (moviesApiResponse != null) {
                            List<Movie> movies = moviesApiResponse.getResults();
                            for (Movie movie : movies) {
                                moviesApiCallback.onSuccess(movie);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesApiCallback.onFailure(e);
                    }
                });
    }

    public void getTopRatedMovies(final MoviesApiCallback moviesApiCallback, String apiKey, String language, int page) {
        mMoviesApi.getTopRatedMovies(apiKey, language, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MoviesApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MoviesApiResponse moviesApiResponse) {
                        if (moviesApiResponse != null) {
                            List<Movie> movies = moviesApiResponse.getResults();
                            for (Movie movie : movies) {
                                moviesApiCallback.onSuccess(movie);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesApiCallback.onFailure(e);
                    }
                });
    }
}
