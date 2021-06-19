package Application;

import Application.Entities.Project;
import Application.Entities.Task;
import Application.Entities.User;
import Application.Repository.ProjectRepository;
import Application.Service.ProjectService;
import Application.Service.TaskService;
import Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
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
    }

    public void test1() {
        populateAll();
        printAll();

        setAll();
        printAll();
    }

    public void printAll(){
        printAllUsers();
        printAllTasks();
        printAllProjects();
    }

    public void populateAll(){
        populateUsers();
        populateTask();
        populateProjects();
    }

    public void setAll(){
        setTasksOnUser();
        setUsersOnProject();
    }
    public void printAllUsers() {
        System.out.println("...Print All USERS...");
        LinkedList<User> result = userService.getAllUsers();
        for (User user : result) {
            initUserTasks(user);
            System.out.println(user.toString());
        }
    }

    public void printAllTasks() {
        System.out.println("...Print All TASKS...");
        LinkedList<Task> result = taskService.getAllTasks();
        for (Task task : result) {
            System.out.println(task.toString());
        }
    }

    public void printAllProjects() {
        System.out.println("...Print All Projects...");
        LinkedList<Project> result = projectService.getAll();
        for (Project project : result) {
            initProjectUsers(project);
            System.out.println(project.toString());
        }
    }

    public void setTasksOnUser(){
        User user = userService.getUsersByName("Achek");
        Task task = taskService.getTaskById(2);
        taskService.setTaskOnUser(task, user);
        task = taskService.getTaskById(3);
        taskService.setTaskOnUser(task, user);
        System.out.println("...SET task IS DONE...");
    }

    public void setUsersOnProject(){
        Project project = projectService.getProjectByName("Project");
        User user = userService.getUserById(2);
        userService.setUserOnProject(user, project);
        user = userService.getUserById(4);
        userService.setUserOnProject(user, project);
        System.out.println("...SET user IS DONE...");
    }

    public void initUserTasks(User user){
        user.setTasks(taskService.getUserTasks(user));
    }

    public void initProjectUsers(Project project){
        project.setUsers(userService.getProjectUsers(project));
    }

    public void populateUsers() {
        userService.addUser("Egor");
        userService.addUser("Egor_2");
        userService.addUser("Egor_3");
        userService.addUser("Achek");
        //service.addUser("Achek");
    }

    public void populateTask(){
        taskService.addTask("Create proj");
        taskService.addTask("Create proj_2");
        taskService.addTask("Create proj_3");
        taskService.addTask("Create proj_4");

    }

    public void populateProjects(){
        projectService.addProject("Project");
        projectService.addProject("Project_2");
        projectService.addProject("Project_3");
        projectService.addProject("Project_4");

    }
}
