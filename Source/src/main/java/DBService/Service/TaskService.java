package DBService.Service;

import DBService.Entities.Project;
import DBService.Entities.Task;
import DBService.Entities.User;
import DBService.Repository.TaskRepository;
import DBService.Utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Component
public class TaskService implements EntityService{

    @Autowired
    TaskRepository taskRepository;

    public void initialize() {
        taskRepository.initialize();
    }

    public Task add(String name) throws ServiceException  {
        Task newTask = new Task(name);

        try {
            taskRepository.addTask(newTask);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Task  with  this  name  already  exists",  sqlEX.getMessage());
        }

        return newTask;
    }

    public LinkedList<Task> getAll() throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = taskRepository.getAll();
            return getTasksFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  getting  all  tasks",  sqlEX.getMessage());
        }
        //return null;
    }

    public Task getById(int id) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = taskRepository.getById(id);
            return getTasksFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  the  project",  sqlEX.getMessage());
        }
        //return null;
    }

    public Task getByName(String name) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = taskRepository.getByName(name);
            return getTasksFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  a  task  with  this  name",  sqlEX.getMessage());
        }
        //return null;
    }

    public LinkedList<Task> getUserTasks(User user) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = taskRepository.getTaskByUserId(user.getId());
            return getTasksFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  getting  userTasks",  sqlEX.getMessage());
        }
        //return null;
    }

    public void delete(int id) throws ServiceException {
        try {
            taskRepository.deleteById(id);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  delete  a  user  with  this name",  sqlEX.getMessage());
        }
    }

    public void setTaskOnUser(Task task, User user) throws ServiceException {
        try {
            taskRepository.setTaskOnUser(task.getId(), user.getId());
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  setting  task  on user",  sqlEX.getMessage());
        }
    }

    private LinkedList<Task> getTasksFromResponse(ResultSet sqlResponse) throws SQLException {
        LinkedList<Task> result = new LinkedList<>();
        while (sqlResponse.next()) {
            Task newTask = new Task(sqlResponse.getInt(1), sqlResponse.getString(2), sqlResponse.getInt(3));
            result.add(newTask);
        }
        return result;
    }
}
