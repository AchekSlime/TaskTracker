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
        LinkedList<User> result = userService.getAllUsers();
        for (User user : result) {
            initUserTasks(user);
            if(user.getProjectId() != 0)
                user.setProjectHolder(projectService.getProjectById(user.getProjectId()));
            ans.append(user).append("\n");
        }
        return ans.toString();
    }

    public String getAllTasksAsString() throws ServiceException {
        StringBuilder ans = new StringBuilder();
        ans.append("\t\t\t\t\t. . . ALL TASKS . . .\n\n");
        LinkedList<Task> result = taskService.getAllTasks();
        for (Task task : result) {
            if(task.getUserId() != 0)
                task.setUserHolder(userService.getUserById(task.getUserId()));
            ans.append(task.toString()).append("\n");
        }
        return ans.toString();
    }

    public String getAllProjectsAsString() throws ServiceException {
        StringBuilder ans = new StringBuilder();
        ans.append("\t\t\t\t\t. . . ALL PROJECTS . . .\n\n");
        LinkedList<Project> result = projectService.getAllProjects();
        for (Project project : result) {
            initProjectUsers(project);
            ans.append(project.toString()).append("\n");
        }
        return ans.toString();
    }

    public String getUserByNameAsString(String name) throws ServiceException{
        User user = userService.getUsersByName(name);
        initUserTasks(user);
        if(user.getProjectId() != 0)
            user.setProjectHolder(projectService.getProjectById(user.getProjectId()));
        return user.toString();
    }

    public String getTaskByTitleAsString(String title) throws ServiceException {
        Task task = taskService.getTaskByTitle(title);
        System.out.println(task.getUserId());

        if(task.getUserId() != 0)
            task.setUserHolder(userService.getUserById(task.getUserId()));
        return task.toString();
    }

    public String getProjectByNameAsString(String name) throws ServiceException {
        Project project = projectService.getProjectByName(name);
        initProjectUsers(project);
        return project.toString();
    }

    public void setTasksOnUser(String taskTitle, String userName) throws ServiceException {
        User user = userService.getUsersByName(userName);
        Task task = taskService.getTaskByTitle(taskTitle);
        taskService.setTaskOnUser(task, user);
    }

    public void setUsersOnProject(String userName, String projectName) throws ServiceException {
        Project project = projectService.getProjectByName(projectName);
        User user = userService.getUsersByName(userName);
        userService.setUserOnProject(user, project);
    }

    public ArrayList<String> getAllUserNames() throws ServiceException {
        ArrayList<String> names = new ArrayList<>();
        LinkedList<User> allUsers = userService.getAllUsers();
        for(User user: allUsers){
            names.add(user.getName());
        }
        return names;
    }

    public ArrayList<String> getAllTaskTitles() throws ServiceException {
        ArrayList<String> titles = new ArrayList<>();
        LinkedList<Task> allTasks = taskService.getAllTasks();
        for(Task task: allTasks){
            titles.add(task.getTitle());
        }
        return titles;
    }

    public ArrayList<String> getAllProjectNames() throws ServiceException {
        ArrayList<String> names = new ArrayList<>();
        LinkedList<Project> allProjects = projectService.getAllProjects();
        for(Project project: allProjects){
            names.add(project.getName());
        }
        return names;
    }

    public void addTask(String title) throws ServiceException {
        taskService.addTask(title);
    }

    public void addUser(String name) throws ServiceException {
        userService.addUser(name);
    }

    public void addProject(String name) throws ServiceException {
        projectService.addProject(name);
    }

    public void deleteTask(String title) throws ServiceException {
        taskService.deleteTask(taskService.getTaskByTitle(title));
    }

    public void deleteUser(String name) throws ServiceException {
        userService.deleteUser(userService.getUsersByName(name));
    }

    public void deleteProject(String name) throws ServiceException {
        projectService.deleteProject(projectService.getProjectByName(name));
    }

    private void initUserTasks(User user) throws ServiceException {
        user.setTasks(taskService.getUserTasks(user));
    }

    private void initProjectUsers(Project project) throws ServiceException {
        project.setUsers(userService.getProjectUsers(project));
    }

    private void populateAll() {
        try {
            if(userService.getAllUsers().isEmpty())
                populateUsers();
            if(taskService.getAllTasks().isEmpty())
                populateTask();
            if(projectService.getAllProjects().isEmpty())
                populateProjects();
        } catch(ServiceException serviceEx){
            System.out.println(serviceEx.message());
            System.out.println(serviceEx.sysMessage());
        }

    }

    // хардкод ¯\(ツ)/–

    private void populateUsers() throws ServiceException {
        userService.addUser("Egor");
        userService.addUser("Artem");
        userService.addUser("Alex");
        userService.addUser("Pavel");
        userService.addUser("Andrew");
        userService.addUser("Tony");
        userService.addUser("Stark");
    }

    private void populateTask() throws ServiceException {
        taskService.addTask("Bring some coffee");
        taskService.addTask("Raise the server");
        taskService.addTask("Re-design the menu");
        taskService.addTask("Develop a personal account");
        taskService.addTask("Develop an api");
        taskService.addTask("Optimize sys search");
        taskService.addTask("Order food");
        taskService.addTask("Rewrite the kernel");
        taskService.addTask("Develop a mobile app design");
        taskService.addTask("Assign tasks to employees");
    }

    private void populateProjects() throws ServiceException {
        projectService.addProject("BackEnd");
        projectService.addProject("FrontEnd");
        projectService.addProject("SystemDev");
        projectService.addProject("Management");
    }
}
