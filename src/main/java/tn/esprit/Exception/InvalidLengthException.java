package tn.esprit.Exception;

public class InvalidLengthException extends RuntimeException{
    public InvalidLengthException(String content) {
        super(content);
    }
}
