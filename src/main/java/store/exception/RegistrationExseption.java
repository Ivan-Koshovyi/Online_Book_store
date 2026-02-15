package store.exception;

public class RegistrationExseption extends RuntimeException {
    public RegistrationExseption(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
