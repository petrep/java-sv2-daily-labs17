package Day01;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepository {
    private DataSource dataSource;

    public MoviesRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public Long saveMovie(String title, LocalDate releaseDate) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement("insert into movies(title, release_date) values(?,?)", Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,title);
            statement.setDate(2, Date.valueOf(releaseDate));
            statement.executeUpdate();

            try(ResultSet rs = statement.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getLong(1);
                }
                throw new IllegalStateException("Insert failed to movies!");
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot connect!", sqle);
        }
    }

    public List<Movie> findAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movies");
            ResultSet rs = statement.executeQuery()){

                while (rs.next()) {
                    long id = rs.getLong("id");
                    String title = rs.getString("title");
                    LocalDate ld = rs.getDate("release_date").toLocalDate();
                    Movie actualMovie = new Movie(id, title, ld);
                    movies.add(actualMovie);
                }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot query!", sqle);
        }

        return movies;
        }


}
