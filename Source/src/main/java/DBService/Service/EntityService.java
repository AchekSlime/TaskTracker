package DBService.Service;

import DBService.Entities.AbstractEntity;
import DBService.Entities.Project;
import DBService.Utils.ServiceException;

import javax.swing.text.html.parser.Entity;
import java.util.LinkedList;

public interface EntityService {
    void initialize();

    <T extends AbstractEntity> LinkedList<T> getAll() throws ServiceException;

    AbstractEntity getByName(String name) throws ServiceException;

    AbstractEntity getById(int id) throws ServiceException;

    AbstractEntity add(String name) throws ServiceException;

    void delete(int id) throws ServiceException;
}
