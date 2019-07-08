package kudos26.aboutmovies.review;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

import kudos26.aboutmovies.api.Api;
import kudos26.aboutmovies.api.ApiClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static kudos26.aboutmovies.Constants.API_KEY;
import static kudos26.aboutmovies.Constants.DATABASE;
import static kudos26.aboutmovies.Constants.EN_US;

@Database(entities = {ReviewEntity.class}, version = 2, exportSchema = false)
public abstract class ReviewDatabase extends RoomDatabase {

    private static volatile ReviewDatabase REVIEW_DATABASE_INSTANCE;

    static ReviewDatabase getDatabase(final Context context) {
        if (REVIEW_DATABASE_INSTANCE == null) {
            synchronized (ReviewDatabase.class) {
                if (REVIEW_DATABASE_INSTANCE == null) {
                    REVIEW_DATABASE_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ReviewDatabase.class, DATABASE)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return REVIEW_DATABASE_INSTANCE;
    }

    public abstract ReviewDao reviewDao();

    public void fetchMovieReviews(int movieId) {
        new FetchMovieReviews(REVIEW_DATABASE_INSTANCE).execute(movieId);
    }

    private static class FetchMovieReviews extends AsyncTask<Integer, Void, Void> {

        private final ReviewDao mDao;

        FetchMovieReviews(ReviewDatabase reviewDatabase) {
            mDao = reviewDatabase.reviewDao();
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            final Integer movieId = params[0];
            Api api = ApiClient.getApiClient().create(Api.class);
            Call<ReviewsResponse> reviewsResponseCall = api.getMovieReviews(movieId, API_KEY, EN_US, "1");
            reviewsResponseCall.enqueue(new retrofit2.Callback<ReviewsResponse>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                    ReviewsResponse responseBody = response.body();
                    if (responseBody != null) {
                        List<ReviewObject> reviewArray = responseBody.getResults();
                        for (ReviewObject review : reviewArray) {
                            Log.e("Content", review.getmContent());
                            mDao.insertReview(new ReviewEntity(movieId, review));
                        }
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<ReviewsResponse> call, Throwable t) {

                }
            });
            return null;
        }
    }
}
