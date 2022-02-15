package Day01;

import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoviesRepositoryTest {

    @Test
    void findAllMoviesTest() {
        MariaDbDataSource datasource = new MariaDbDataSource();
        try {
            datasource.setUrl("jdbc:mariadb://localhost:3307/movies-actors?useUnicode=true");
            datasource.setUserName("root");
            datasource.setPassword("password");
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot reach DataBase!", sqle);
        }
        MoviesRepository mr = new MoviesRepository(datasource);
        List<Movie> movies = mr.findAllMovies();
        System.out.println(movies.size());

    }
}