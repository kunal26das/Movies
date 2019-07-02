package kudos26.aboutmovies.Movie;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class Movie {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movie")
    private String mMovieTitle;

    public Movie(@NonNull String movieTitle) {
        mMovieTitle = movieTitle;
    }

    @NonNull
    public String getMovieTitle() {
        return mMovieTitle;
    }
}
