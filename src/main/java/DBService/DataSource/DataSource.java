package DBService.DataSource;

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

    public boolean checkTable(String name) throws SQLException {
        boolean chk = true;
        boolean doCreate = false;
        try {
            statement.execute("update " + name + " set id = 5, where 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            //   System.out.println("  Utils GOT:  " + theError);
            /** If table exists will get -  WARNING 02000: No row was found **/
            if (theError.equals("42X05"))   // Table does not exist
            {
                return false;
            } else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("checkTable: Incorrect table definition. Drop table WISH_LIST and rerun this program");
                throw sqle;
            } else {
                System.out.println("checkTable: Unhandled SQLException");
                throw sqle;
            }
        }
        //  System.out.println("Just got the warning - table exists OK ");
        return true;
    }

    private void createStatement() throws SQLException{
        statement = connection.createStatement();
    }
}
