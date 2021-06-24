package DBService;

import DBService.Entities.Project;
import DBService.Entities.Task;
import DBService.Entities.User;
import DBService.Service.ProjectService;
import DBService.Service.TaskService;
import DBService.Service.UserService;
import DBService.Utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;

@Component
@Scope(value="singleton")
public class AppService {
    private final UserService userService;
    private final TaskService taskService;
    private final ProjectService projectService;

    @Autowired
    public AppService(UserService userService, TaskService taskService, ProjectService projectService) {
        this.userService = userService;
        this.taskService = taskService;
        this.projectService = projectService;

        this.projectService.initialize();
        this.userService.initialize();
        this.taskService.initialize();

        populateAll();
    }

    public String getAllUsersAsString() throws ServiceException {
        StringBuilder ans = new StringBuilder();
        ans.append("\t\t\t\t\t. . . ALL USERS . . .\n\n");
        LinkedList<User> result = userService.getAll();
        for (User user : result) {
            initUserTasks(user);
            if(user.getProjectId() != 0)
                user.setProjectHolder(projectService.getById(user.getProjectId()));
            ans.append(user).append("\n");
        }
        return ans.toString();
    }

    public String getAllTasksAsString() throws ServiceException {
        StringBuilder ans = new StringBuilder();
        ans.append("\t\t\t\t\t. . . ALL TASKS . . .\n\n");
        LinkedList<Task> result = taskService.getAll();
        for (Task task : result) {
            if(task.getUserId() != 0)
                task.setUserHolder(userService.getById(task.getUserId()));
            ans.append(task.toString()).append("\n");
        }
        return ans.toString();
    }

    public String getAllProjectsAsString() throws ServiceException {
        StringBuilder ans = new StringBuilder();
        ans.append("\t\t\t\t\t. . . ALL PROJECTS . . .\n\n");
        LinkedList<Project> result = projectService.getAll();
        for (Project project : result) {
            initProjectUsers(project);
            ans.append(project.toString()).append("\n");
        }
        return ans.toString();
    }

    public String getUserByNameAsString(String name) throws ServiceException{
        User user = userService.getByName(name);
        initUserTasks(user);
        if(user.getProjectId() != 0)
            user.setProjectHolder(projectService.getById(user.getProjectId()));
        return user.toString();
    }

    public String getTaskByTitleAsString(String title) throws ServiceException {
        Task task = taskService.getByName(title);
        System.out.println(task.getUserId());

        if(task.getUserId() != 0)
            task.setUserHolder(userService.getById(task.getUserId()));
        return task.toString();
    }

    public String getProjectByNameAsString(String name) throws ServiceException {
        Project project = projectService.getByName(name);
        initProjectUsers(project);
        return project.toString();
    }

    public void setTasksOnUser(String taskTitle, String userName) throws ServiceException {
        User user = userService.getByName(userName);
        Task task = taskService.getByName(taskTitle);
        taskService.setTaskOnUser(task, user);
    }

    public void setUsersOnProject(String userName, String projectName) throws ServiceException {
        Project project = projectService.getByName(projectName);
        User user = userService.getByName(userName);
        userService.setUserOnProject(user, project);
    }

    public ArrayList<String> getAllUserNames() throws ServiceException {
        ArrayList<String> names = new ArrayList<>();
        LinkedList<User> allUsers = userService.getAll();
        for(User user: allUsers){
            names.add(user.getName());
        }
        return names;
    }

    public ArrayList<String> getAllTaskTitles() throws ServiceException {
        ArrayList<String> titles = new ArrayList<>();
        LinkedList<Task> allTasks = taskService.getAll();
        for(Task task: allTasks){
            titles.add(task.getName());
        }
        return titles;
    }

    public ArrayList<String> getAllProjectNames() throws ServiceException {
        ArrayList<String> names = new ArrayList<>();
        LinkedList<Project> allProjects = projectService.getAll();
        for(Project project: allProjects){
            names.add(project.getName());
        }
        return names;
    }

    public void addTask(String title) throws ServiceException {
        taskService.add(title);
    }

    public void addUser(String name) throws ServiceException {
        userService.add(name);
    }

    public void addProject(String name) throws ServiceException {
        projectService.add(name);
    }

    public void deleteTask(String title) throws ServiceException {
        taskService.delete(taskService.getByName(title).getId());
    }

    public void deleteUser(String name) throws ServiceException {
        userService.delete(userService.getByName(name).getId());
    }

    public void deleteProject(String name) throws ServiceException {
        projectService.delete(projectService.getByName(name).getId());
    }

    private void initUserTasks(User user) throws ServiceException {
        user.setTasks(taskService.getUserTasks(user));
    }

    private void initProjectUsers(Project project) throws ServiceException {
        project.setUsers(userService.getProjectUsers(project));
    }

    private void populateAll() {
        try {
            if(userService.getAll().isEmpty())
                populateUsers();
            if(taskService.getAll().isEmpty())
                populateTask();
            if(projectService.getAll().isEmpty())
                populateProjects();
        } catch(ServiceException serviceEx){
            System.out.println(serviceEx.message());
            System.out.println(serviceEx.sysMessage());
        }
    }

    // хардкод ¯\(ツ)/–

    private void populateUsers() throws ServiceException {
        userService.add("Egor");
        userService.add("Artem");
        userService.add("Alex");
        userService.add("Pavel");
        userService.add("Andrew");
        userService.add("Tony");
        userService.add("Stark");
    }

    private void populateTask() throws ServiceException {
        taskService.add("Bring some coffee");
        taskService.add("Raise the server");
        taskService.add("Re-design the menu");
        taskService.add("Develop a personal account");
        taskService.add("Develop an api");
        taskService.add("Optimize sys search");
        taskService.add("Order food");
        taskService.add("Rewrite the kernel");
        taskService.add("Develop a mobile app design");
        taskService.add("Assign tasks to employees");
    }

    private void populateProjects() throws ServiceException {
        projectService.add("BackEnd");
        projectService.add("FrontEnd");
        projectService.add("SystemDev");
        projectService.add("Management");
    }
}
