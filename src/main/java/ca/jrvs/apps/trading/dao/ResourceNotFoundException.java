package ca.jrvs.apps.trading.dao;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String e) {
        super(e);
    }

    public ResourceNotFoundException(Exception e) {
        super(e.getMessage());
    }


}
