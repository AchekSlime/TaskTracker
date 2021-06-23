package GUI.Controllers.Modal;

import GUI.Controllers.MainMenuController;

public abstract class AbstractModalController {
    protected String type;
    protected MainMenuController parent;

    public abstract void setType(String type);
    public abstract void setParentController(MainMenuController controller);
    public abstract void setConfig();
}
