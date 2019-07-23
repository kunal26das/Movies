package kudos26.movies.movie.api;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kudos26.movies.movie.MovieObject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static kudos26.movies.Constants.BASE_URL_API;

@Singleton
public class MoviesApiClient {

    private static Retrofit mRetroFit = null;
    private final MoviesApi mMoviesApi;

    public MoviesApiClient() {
        if (mRetroFit == null) {
            mRetroFit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        mMoviesApi = mRetroFit.create(MoviesApi.class);
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
                            List<MovieObject> movies = moviesApiResponse.getResults();
                            for (MovieObject movie : movies) {
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
                            List<MovieObject> movies = moviesApiResponse.getResults();
                            for (MovieObject movie : movies) {
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
