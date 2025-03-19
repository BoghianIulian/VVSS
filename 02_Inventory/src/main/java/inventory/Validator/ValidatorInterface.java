package inventory.Validator;

public interface ValidatorInterface<T> {
    void validate(T entity) throws ValidatorException;
}
