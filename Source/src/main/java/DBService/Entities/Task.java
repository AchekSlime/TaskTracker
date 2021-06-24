package DBService.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task extends AbstractEntity{
    private int userId;
    private User userHolder;

    public Task(String title) {
        this.name = title;
    }

    public Task(int id, String name, int userId){
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    @Override
    public String toString() {
        String str = "—>   Task_" + id + "\n";
        String leftMargin = "\t      ";
        str += leftMargin + "< title >         =   \" " + name + " \"\n";
        str += leftMargin + "< holder >   =    User  :  " + ((userHolder != null) ? "\" " + userHolder.getName() + " \"" : "null")  + "\n";
        return str;
    }
}
