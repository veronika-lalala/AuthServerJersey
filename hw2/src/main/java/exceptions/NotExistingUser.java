package exceptions;

public class NotExistingUser extends Exception{
    public NotExistingUser(String message){
        super(message);
    }
}
