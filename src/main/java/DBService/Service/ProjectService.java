package DBService.Service;

import DBService.Entities.Project;
import DBService.Repository.ProjectRepository;
import DBService.Utils.ServiceException;
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

    public Project addProject(String name) throws ServiceException {
        Project newProject = new Project(name);

        try {
            projectRepository.addProject(newProject);
        } catch (SQLException sqlEX) {
           throw new ServiceException("Project  with  this  name  already  exists", sqlEX.getMessage());
        }

        return newProject;
    }

    public LinkedList<Project> getAllProjects() throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getAll();
            return getProjectsFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  getting  all  projects", sqlEX.getMessage());
        }
    }

    public Project getProjectByName(String name) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getByName(name);
            return getProjectsFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  the  project  with  this  name",  sqlEX.getMessage());
        }

        //return null;
    }

    public Project getProjectById(int id) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getById(id);
            return getProjectsFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  the  project",  sqlEX.getMessage());
        }

        //return null;
    }

    public void deleteProject(Project project) throws ServiceException {
        try {
            projectRepository.deleteById(project.getId());
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  delete  a  project  with  this  name",  sqlEX.getMessage());
        }
    }

    private LinkedList<Project> getProjectsFromResponse(ResultSet sqlResponse) throws SQLException {
        LinkedList<Project> result = new LinkedList<>();
        while (sqlResponse.next()) {
            Project project = new Project(sqlResponse.getString(2));
            project.setId(sqlResponse.getInt(1));

            result.add(project);
        }
        return result;
    }
}
