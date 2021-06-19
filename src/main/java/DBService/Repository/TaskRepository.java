package DBService.Repository;

import DBService.DataSource.DataSource;
import DBService.Entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@PropertySource("classpath:h2.properties")
public class TaskRepository {
    private Environment env;

    private DataSource dataSource;
    private Statement statement;

    @Autowired
    public TaskRepository(DataSource dataSource, Environment env) {
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
        statement.execute(env.getProperty("createTaskTable"));
    }

    public void addTask(Task newTask) throws SQLException {
        statement.executeUpdate("insert into task(title) values('" + newTask.getTitle() + "')");
    }

    public ResultSet getAll() throws SQLException {
        return statement.executeQuery("select * from task");
    }

    public ResultSet getById(int id) throws SQLException {
        return statement.executeQuery("select * from task where id = " + id);
    }

    public ResultSet getTaskByUserId(int userId) throws SQLException {
        return statement.executeQuery("select * from task where user_id = " + userId);
    }

    public void deleteById(int id) throws SQLException {
        statement.executeUpdate("delete from task where id = " + id);
    }

    public void setTaskOnUser(int taskId, int userId) throws SQLException{
        statement.executeUpdate("update task set user_id = " + userId + " where id = '" + taskId + "'");
    }

}
