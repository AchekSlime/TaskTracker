package DBService.Repository;

import DBService.Entities.Project;
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
public class  ProjectRepository implements Repository {
    private Environment env;

    private DataSource dataSource;
    private Statement statement;

    @Autowired
    public ProjectRepository(DataSource dataSource, Environment env) {
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
        statement.execute(env.getProperty("createProjectTable"));
    }

    @Override
    public ResultSet getAll() throws SQLException {
        return statement.executeQuery("select * from project");
    }

    @Override
    public ResultSet getByName(String name) throws SQLException {
        return statement.executeQuery("select id, name from project where name = '" + name + "'");
    }

    @Override
    public ResultSet getById(int id) throws SQLException {
        return statement.executeQuery("select id, name from project where id = '" + id + "'");
    }

    public void addProject(Project newProject) throws SQLException {
        statement.executeUpdate("insert into project(name) values('" + newProject.getName() + "')");
    }

    @Override
    public void deleteById(int id) throws SQLException {
        statement.executeUpdate("delete from project where id = " + id);
    }

}
