package Day01;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

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

            ActorsRepository actorsRepository = new ActorsRepository(datasource);
//            actorsRepository.saveActor("John Doe");

        System.out.println(actorsRepository.findActorsWithPrefix("J"));
    }
}
