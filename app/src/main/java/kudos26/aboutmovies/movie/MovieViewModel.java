package kudos26.aboutmovies.movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository;
    //private LiveData<List<MovieEntity>> mMovieLiveData;

    public MovieViewModel(Application application) {
        super(application);
        mMovieRepository = new MovieRepository(application);
        //mMovieLiveData = mMovieRepository.getMovies(POPULAR_MOVIES);
    }

    public LiveData<List<MovieEntity>> getMovieLiveData(int sortCriteria) {
        return mMovieRepository.getMovies(sortCriteria);
    }

    public MovieEntity getMovieEntry(int id) {
        return mMovieRepository.getMovie(id);
    }

    public void getMoviesPage(int sortCriteria, int page) {
        mMovieRepository.getMoviesPage(sortCriteria, page);
    }

}
