package DBService.Entities;

import lombok.Data;

import java.util.LinkedList;


@Data
public class User {
    private int id;
    private final String name;
    private int projectHolder;
    private LinkedList<Task> tasks;
}
