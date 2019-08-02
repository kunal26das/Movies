package kudos26.movies.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import kudos26.movies.movie.api.MoviesApiClient;

@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    void inject(MoviesApiClient moviesApiClient);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        @BindsInstance
        Builder application(Application application);
    }
}
