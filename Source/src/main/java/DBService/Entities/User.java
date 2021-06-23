package DBService.Entities;

import lombok.*;

import java.util.LinkedList;


@Getter
@Setter
public class User {
    private int id;
    private final String name;
    private Project projectHolder;
    private int projectId;
    private LinkedList<Task> tasks;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "—>   User_" + id + "\n";
        String leftMargin = "\t\t\t";
        str += leftMargin + "< name >    =  \" " + name + " \"\n";
        str += leftMargin + "< holder >  =   Project  :  " + ((projectHolder != null) ? "\" " + projectHolder.getName() + " \"" : "null") + "\n";
        str += leftMargin + "< tasks >    =   ";
        if(!tasks.isEmpty())
            str+=getTasks(leftMargin);
        else
            str += "[ null ]";
        return str + "\n";
    }

    public String getTasks(String margin) {
            StringBuilder str = new StringBuilder();
            String leftMargin = "\n" + margin + "\t\t\t";
            String arrow = "|—> ";
            for (Task task : tasks) {
                str.append(leftMargin).append(arrow);
                str.append("\" ").append(task.getTitle()).append(" \"");
            }
            return str.toString();
    }
}
