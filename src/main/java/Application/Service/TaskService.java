package Application.Service;

import Application.Entities.Task;
import Application.Entities.User;
import Application.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Component
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public void initialize(){
        taskRepository.initialize();
    }

    public Task addTask(String title) {
        Task newTask = new Task(title);

        try {
            taskRepository.addTask(newTask);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return newTask;
    }

    public LinkedList<Task> getAllTasks() {
        ResultSet sqlResponse;
        try {
            sqlResponse = taskRepository.getAll();
            return getTasksFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public Task getTaskById(int id){
        ResultSet sqlResponse;
        try {
            sqlResponse = taskRepository.getById(id);
            return getTasksFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public LinkedList<Task> getUserTasks(User user){
        ResultSet sqlResponse;
        try {
            sqlResponse = taskRepository.getTaskByUserId(user.getId());
            return getTasksFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    private LinkedList<Task> getTasksFromResponse(ResultSet sqlResponse) throws SQLException {
        LinkedList<Task> result = new LinkedList<>();
        while (sqlResponse.next()) {
            Task newTask = new Task(sqlResponse.getString(2));
            newTask.setId(sqlResponse.getInt(1));
            newTask.setUserHolder(sqlResponse.getInt(3));

            result.add(newTask);
        }
        return result;
    }

    public void deleteTask(Task task) {
        try {
            taskRepository.deleteById(task.getId());
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }
    }

    public void setTaskOnUser(Task task, User user) {
        try {
            taskRepository.setTaskOnUser(task.getId(), user.getId());
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }
    }
}
