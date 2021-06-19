package DBService.Entities;

import lombok.Data;

import java.util.LinkedList;

@Data
public class Project {
    private int id;
    private final String name;
    private LinkedList<User> users;
}
