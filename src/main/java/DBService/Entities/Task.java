package DBService.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
    private int id;
    private final String title;
    private int userId;
    private User userHolder;

    public Task(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        String str = "—>   Task_" + id + "\n";
        String leftMargin = "\t      ";
        str += leftMargin + "< title >         =   \" " + title + " \"\n";
        str += leftMargin + "< holder >   =    User  :  " + ((userHolder != null) ? "\" " + userHolder.getName() + " \"" : "null")  + "\n";
        return str;
    }
}
