package kudos26.aboutmovies.Movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;
    private LiveData<List<Movie>> mMovieLiveData;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
        mMovieLiveData = mMovieRepository.getMovies();
    }

    public LiveData<List<Movie>> getMovieLiveData() {
        return mMovieLiveData;
    }

    void insert(Movie movie) {
        mMovieRepository.insert(movie);
    }

}
