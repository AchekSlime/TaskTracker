package Application.Service;

import Application.Entities.Project;
import Application.Entities.User;
import Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void initialize() {
        userRepository.initialize();
    }

    public User addUser(String name) {
        User newUser = new User(name);

        try {
            userRepository.addUser(newUser);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return newUser;
    }

    public LinkedList<User> getAllUsers() {
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getAll();
            return getUsersFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public User getUsersByName(String name) {
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getByName(name);
            return getUsersFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public User getUserById(int id) {
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getById(id);
            return getUsersFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public LinkedList<User> getProjectUsers(Project project){
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getUserByProjectId(project.getId());
            return getUsersFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public LinkedList<User> getUsersFromResponse(ResultSet sqlResponse) throws SQLException {
        LinkedList<User> result = new LinkedList<>();
        while (sqlResponse.next()) {
            User newUser = new User(sqlResponse.getString(2));
            newUser.setId(sqlResponse.getInt(1));
            newUser.setProjectHolder(sqlResponse.getInt(3));

            result.add(newUser);
        }
        return result;
    }

    public void deleteUser(User user) {
        try {
            userRepository.deleteById(user.getId());
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }
    }

    public void setUserOnProject(User user, Project project) {
        try {
            userRepository.setUserOnProject(user.getId(), project.getId());
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }
    }


}
