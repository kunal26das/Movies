package kudos26.aboutmovies.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;
import java.util.List;

import kudos26.aboutmovies.pojo.ApiResponse;
import kudos26.aboutmovies.pojo.MovieObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static kudos26.aboutmovies.Constants.API_KEY;

public class MovieWorker extends Worker {

    public MovieWorker(@NonNull Context appContext,
                       @NonNull WorkerParameters workerParameters) {
        super(appContext, workerParameters);
    }

    @NonNull
    @Override
    public Worker.Result doWork() {
        final List<String> movieTitles = new ArrayList<>();
        MovieApi movieApi = MovieApiClient.getClient().create(MovieApi.class);
        Call<ApiResponse> movieJsonResponseCall = movieApi.getPopularMovies(API_KEY);
        movieJsonResponseCall.enqueue(new retrofit2.Callback<ApiResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse responseBody = response.body();
                if (responseBody != null) {
                    List<MovieObject> movieArray = responseBody.getResults();
                    for (MovieObject movieObject : movieArray) {
                        movieTitles.add(movieObject.getTitle());
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                movieTitles.clear();
            }
        });
        if (movieTitles.isEmpty()) {
            String[] titles = (String[]) movieTitles.toArray();
            Data workResult = new Data.Builder()
                    .putStringArray("MOVIE_TITLES", titles)
                    .build();
            return Result.success(workResult);
        } else return Result.failure();
    }
}
