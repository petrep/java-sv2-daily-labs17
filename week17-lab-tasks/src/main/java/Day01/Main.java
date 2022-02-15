package Day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;

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
            flyway.migrate();

            ActorsRepository actorsRepository = new ActorsRepository(datasource);

            MoviesRepository moviesRepository = new MoviesRepository(datasource);
            moviesRepository.saveMovie("Titanic", LocalDate.of(1997,12,12));
            moviesRepository.saveMovie("It", LocalDate.of(2007,5,2));

//            actorsRepository.saveActor("John Doe");

        System.out.println(actorsRepository.findActorsWithPrefix("J"));
    }
}
