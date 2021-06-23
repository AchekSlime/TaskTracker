package DBService.Utils;

public class ServiceException extends Exception{
    private final String message;
    private final String sysMessage;

    public ServiceException(String message, String sysMessage){
        this.message = message;
        this.sysMessage = sysMessage;
    }

    public String message(){
        return message;
    }

    public String sysMessage(){
        return sysMessage;
    }

}
