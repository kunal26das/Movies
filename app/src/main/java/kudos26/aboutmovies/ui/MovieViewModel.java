package kudos26.aboutmovies.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kudos26.aboutmovies.pojo.MovieEntry;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;
    //private LiveData<List<MovieEntry>> mMovieLiveData;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
        //mMovieLiveData = mMovieRepository.getMovieEntries(POPULAR_MOVIES);
    }

    public LiveData<List<MovieEntry>> getMovieLiveData(int sortCriteria) {
        return mMovieRepository.getMovieEntries(sortCriteria);
    }

    public void fetchPopularMovies(int page) {
        mMovieRepository.fetchPopularMovies(page);
    }

    public void fetchTopRatedMovies(int page) {
        mMovieRepository.fetchTopRatedMovies(page);
    }

}
