package DBService.Entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractEntity {
    protected int id;
    protected String name;
}
