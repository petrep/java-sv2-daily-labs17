package Day01;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ActorsRepositoryTest {

    MariaDbDataSource datasource = new MariaDbDataSource();
    ActorsRepository actorsRepository = new ActorsRepository(datasource);

    @BeforeEach
    void init() {

        try {
            datasource.setUrl("jdbc:mariadb://localhost:3307/movies-actors?useUnicode=true");
            datasource.setUserName("root");
            datasource.setPassword("password");
        } catch (
                SQLException sqle) {
            throw new IllegalStateException("Cannot reach DataBase!", sqle);
        }

        Flyway flyway = Flyway.configure().dataSource(datasource).load();
//        flyway.clean();
        flyway.migrate();

        ActorsRepository actorsRepository = new ActorsRepository(datasource);

    }
    @Test
    void testInsert(){
        actorsRepository.saveActor("Jack Doe");
    }
}