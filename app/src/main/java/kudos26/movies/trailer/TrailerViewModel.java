package kudos26.movies.trailer;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import kudos26.movies.trailer.api.Trailer;
import kudos26.movies.trailer.api.TrailersApiCallback;
import kudos26.movies.trailer.api.TrailersApiClient;

import static kudos26.movies.Constants.API_KEY;
import static kudos26.movies.Constants.LANGUAGE_EN_US;

public class TrailerViewModel extends AndroidViewModel {

    private TrailersApiClient mApiClient;
    private MutableLiveData<List<Trailer>> mTrailers;

    public TrailerViewModel(Application application) {
        super(application);
        mTrailers = new MutableLiveData<>();
        mApiClient = new TrailersApiClient();
        mTrailers.setValue(new ArrayList<>());
    }

    public LiveData<List<Trailer>> getMovieTrailers(int movieId) {
        mApiClient.getTrailers(new TrailersApiCallback() {
            @Override
            public void onSuccess(final Trailer trailer) {
                List<Trailer> tempList = mTrailers.getValue();
                if (tempList != null) {
                    tempList.add(trailer);
                }
                mTrailers.setValue(tempList);
            }

            @Override
            public void onError(Throwable error) {

            }
        }, movieId, API_KEY, LANGUAGE_EN_US);
        return mTrailers;
    }
}
