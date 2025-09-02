package exceptions;

public class SameLoginException extends Exception {
    public SameLoginException(String message){
        super(message);
    }
}
