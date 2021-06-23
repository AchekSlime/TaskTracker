package DBService.Repository;

import DBService.Entities.User;
import DBService.DataSource.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@PropertySource("classpath:h2.properties")
public class UserRepository {
    private Environment env;

    private DataSource dataSource;
    private Statement statement;

    @Autowired
    public UserRepository(DataSource dataSource, Environment env) {
        this.dataSource = dataSource;
        this.statement = dataSource.getStatement();
        this.env = env;
    }

    public void initialize(){
        try {
            createTable();
        } catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        statement.execute(env.getProperty("createUserTable"));
    }

    public ResultSet getAll() throws SQLException {
        return statement.executeQuery("select * from user");
    }

    public ResultSet getByName(String name) throws SQLException {
        return statement.executeQuery("select * from user where name = '" + name + "'");
    }

    public ResultSet getById(int id) throws SQLException {
        return statement.executeQuery("select * from user where id = '" + id + "'");
    }

    public ResultSet getUserByProjectId(int projectId) throws SQLException {
        return statement.executeQuery("select * from user where project_id = " + projectId);
    }

    public void addUser(User newUser) throws SQLException {
        statement.executeUpdate("insert into user(name) values('" + newUser.getName() + "')");
    }

    public void deleteById(int id) throws SQLException {
        statement.executeUpdate("delete from user where id = " + id);
    }

    public void setUserOnProject(int userId, int projectId) throws SQLException{
        statement.executeUpdate("update user set project_id = " + projectId + " where id = '" + userId + "'");
    }

}
