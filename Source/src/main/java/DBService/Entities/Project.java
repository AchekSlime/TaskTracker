package DBService.Entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class Project extends AbstractEntity{
    private LinkedList<User> users;

    public Project(String name) {
        this.name = name;
    }

    public Project(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "—>   Project_" + id + "\n";
        String leftMargin = "\t\t\t";
        str += leftMargin + "< name >    =   \" " + name + " \"\n";
        str += leftMargin + "< users >    =    ";
        if(!users.isEmpty())
            str += getUsers(leftMargin);
        else
            str += "[ null ]";
        return str + "\n";
    }

    public String getUsers(String margin) {
        StringBuilder str = new StringBuilder();
        String leftMargin = "\n" + margin + "\t\t\t";
        String arrow = "|—> ";
        for (User user : users) {
            str.append(leftMargin).append(arrow);
            str.append("\" ").append(user.getName()).append(" \"");
        }
        return str.toString();
    }
}
