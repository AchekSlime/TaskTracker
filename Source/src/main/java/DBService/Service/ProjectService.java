package DBService.Service;

import DBService.Entities.AbstractEntity;
import DBService.Entities.Project;
import DBService.Repository.ProjectRepository;
import DBService.Utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Component
public class ProjectService implements EntityService{

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public void initialize() {
        projectRepository.initialize();
    }

    @Override
    public Project add(String name) throws ServiceException {
        Project newProject = new Project(name);

        try {
            projectRepository.addProject(newProject);
        } catch (SQLException sqlEX) {
           throw new ServiceException("Project  with  this  name  already  exists", sqlEX.getMessage());
        }

        return newProject;
    }

    @Override
    public LinkedList<Project> getAll() throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getAll();
            return getProjectsFromResponse(sqlResponse);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Exception  while  getting  all  projects", sqlEX.getMessage());
        }
    }

    @Override
    public Project getByName(String name) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getByName(name);
            return getProjectsFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  the  project  with  this  name",  sqlEX.getMessage());
        }

        //return null;
    }

    @Override
    public Project getById(int id) throws ServiceException {
        ResultSet sqlResponse;
        try {
            sqlResponse = projectRepository.getById(id);
            return getProjectsFromResponse(sqlResponse).get(0);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  find  the  project",  sqlEX.getMessage());
        }
        //return null;
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            projectRepository.deleteById(id);
        } catch (SQLException sqlEX) {
            throw new ServiceException("Couldn't  delete  a  project  with  this  name",  sqlEX.getMessage());
        }
    }

    private LinkedList<Project> getProjectsFromResponse(ResultSet sqlResponse) throws SQLException {
        LinkedList<Project> result = new LinkedList<>();
        while (sqlResponse.next()) {
            Project project = new Project(sqlResponse.getInt(1), sqlResponse.getString(2));

            result.add(project);
        }
        return result;
    }
}
