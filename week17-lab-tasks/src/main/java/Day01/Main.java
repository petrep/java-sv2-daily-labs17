package Day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MariaDbDataSource datasource = new MariaDbDataSource();
            try {
                datasource.setUrl("jdbc:mariadb://localhost:3307/movies-actors?useUnicode=true");
                datasource.setUserName("root");
                datasource.setPassword("password");
            } catch (SQLException sqle) {
                throw new IllegalStateException("Cannot reach DataBase!", sqle);
            }

            Flyway flyway = Flyway.configure().dataSource(datasource).load();
            flyway.clean();
            flyway.migrate();

            ActorsRepository actorsRepository = new ActorsRepository(datasource);

            MoviesRepository moviesRepository = new MoviesRepository(datasource);

            ActorsMoviesRepository actorsMoviesRepository = new ActorsMoviesRepository(datasource);
            RatingsRepository ratingsRepository = new RatingsRepository(datasource);
            ActorsMoviesService actorsMoviesService = new ActorsMoviesService(actorsRepository, moviesRepository, actorsMoviesRepository);
            actorsMoviesService.insertMovieWithActors("Titanic", LocalDate.of(1997, 11,13), List.of("Leonardo DiCaprio",
                    "Kate Winslet"));
            MoviesRatingService moviesRatingService = new MoviesRatingService(moviesRepository, ratingsRepository);

            // Here if one of the ratings is invalid, then none of the ratings will be added:
            moviesRatingService.addRatings("Titanic", 5,4,2);


//            moviesRepository.saveMovie("Titanic", LocalDate.of(1997,12,12));
//            moviesRepository.saveMovie("It", LocalDate.of(2007,5,2));

//            actorsRepository.saveActor("John Doe");
//            System.out.println(actorsRepository.findActorByName("John Doe"));

//        System.out.println(actorsRepository.findActorsWithPrefix("J"));
    }
}
