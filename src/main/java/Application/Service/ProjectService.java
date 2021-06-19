package Application.Service;

import Application.Entities.Project;
import Application.Entities.User;
import Application.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Component
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public void initialize() {
        projectRepository.initialize();
    }

    public Project addProject(String name) {
        Project newProject = new Project(name);

        try {
            projectRepository.addProject(newProject);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return newProject;
    }

    public LinkedList<Project> getAll() {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getAll();
            return getProjectsFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public Project getProjectByName(String name) {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getByName(name);
            return getProjectsFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public Project getProjectById(int id) {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getById(id);
            return getProjectsFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }

        return null;
    }

    public void deleteProject(Project project) {
        try {
            projectRepository.deleteById(project.getId());
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX.getMessage());
        }
    }

    public LinkedList<Project> getProjectsFromResponse(ResultSet sqlResponse) throws SQLException {
        LinkedList<Project> result = new LinkedList<>();
        while (sqlResponse.next()) {
            Project project = new Project(sqlResponse.getString(2));
            project.setId(sqlResponse.getInt(1));

            result.add(project);
        }
        return result;
    }
}
