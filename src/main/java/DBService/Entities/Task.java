package DBService.Entities;

import lombok.Data;

@Data
public class Task {
    private int id;
    private final String title;
    private int userHolder;
}
