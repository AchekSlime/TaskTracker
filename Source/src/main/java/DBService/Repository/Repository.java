package DBService.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Repository {
    void initialize();
    ResultSet getAll() throws SQLException;
    ResultSet getByName(String name) throws SQLException;
    ResultSet getById(int id) throws SQLException;
    void deleteById(int id) throws SQLException;
}
