package Application.DataSource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
@Scope("singleton")
@PropertySource("classpath:h2.properties")
@Getter
@Setter
public class DataSource {
    private Environment env;
    private Connection connection;
    private Statement statement;

    public DataSource(Environment environment) {
        this.env = environment;
        try{
            createConnection();
            createStatement();
        }
        catch(SQLException sqlEX){
            sqlEX.printStackTrace();
        }
    }

    public void execute(String sql) throws SQLException{
        statement.execute(sql);
    }

    public PreparedStatement createPS(String template) throws SQLException{
        return connection.prepareStatement(template);
    }

    private void createConnection() throws SQLException {
        String url = env.getProperty("connectionUrl");
        String username = env.getProperty("username");
        String password = env.getProperty("password");

        connection = DriverManager.getConnection(url, username, password);
    }

    private void createStatement() throws SQLException{
        statement = connection.createStatement();
    }
}
