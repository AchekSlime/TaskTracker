package DBService.Service;

import DBService.Entities.Project;
import DBService.Entities.User;
import DBService.Repository.UserRepository;
import DBService.Utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskService taskService;

    public void initialize() {
        userRepository.initialize();
    }

    public User addUser(String name) throws ServiceException {
        User newUser = new User(name);

        try {
            userRepository.addUser(newUser);
        } catch (SQLException sqlEX) {
            throw new ServiceException("User  with  this  name  already  exists",  sqlEX.getMessage());
        }

        return newUser;
    }

    public LinkedList<User> getAllUsers() throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getAll();
            return getUsersFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  getting  all  users",  sqlEX.getMessage());
        }

        //return null;
    }

    public User getUsersByName(String name) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getByName(name);
            User user = getUsersFromResponse(sqlResponse).get(0);
            return user;
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  a  user  with  this  name",  sqlEX.getMessage());
        }
        //return null;
    }

    public User getUserById(int id) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getById(id);
            return getUsersFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  this  task",  sqlEX.getMessage());
        }
        //return null;
    }

    public void deleteUser(User user) throws ServiceException {
        try {
            userRepository.deleteById(user.getId());
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  delete  a  task  with  this  title",  sqlEX.getMessage());
        }
    }

    public void setUserOnProject(User user, Project project) throws ServiceException {
        try {
            userRepository.setUserOnProject(user.getId(), project.getId());
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  setting  user  on  project",  sqlEX.getMessage());
        }
    }

    public LinkedList<User> getProjectUsers(Project project) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = userRepository.getUserByProjectId(project.getId());
            return getUsersFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  getting  projectUsers",  sqlEX.getMessage());
        }

        //return null;
    }

    private LinkedList<User> getUsersFromResponse(ResultSet sqlResponse) throws SQLException {
        LinkedList<User> result = new LinkedList<>();
        while (sqlResponse.next()) {
            User newUser = new User(sqlResponse.getString(2));
            newUser.setId(sqlResponse.getInt(1));
            newUser.setProjectId(sqlResponse.getInt(3));

            result.add(newUser);
        }
        return result;
    }
}
