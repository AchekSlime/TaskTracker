package Application.Test;

import java.sql.*;

public class DBConnection {
    private static final String DB_NAME = "./TackerDb";


    public static void main(String[] args) throws Exception{
        create();
        populate();
        select();
    }

    private static void create(){
        final String SQL_CREATE = "CREATE TABLE USER"
                + "("
                + " ID serial,"
                + " NAME varchar(100) NOT NULL,"
                + " PRIMARY KEY (ID)"
                + ")";

        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + DB_NAME, "sa", "")){
            Statement st = conn.createStatement();
            st.execute(SQL_CREATE);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void populate(){
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + DB_NAME, "sa", "")){
            Statement st = conn.createStatement();
            int row = st.executeUpdate(generateInsert("LOl"));
            System.out.println(row);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void select(){
        String sql = "SELECT * FROM USER";
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + DB_NAME, "sa", "")) {
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {

                long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                System.out.println(id + " " + name);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static String generateInsert(String name) {

        return "INSERT INTO USER (NAME) " +
                "VALUES ('" + name + "')";

    }
}
