package store.lib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstPassword;
    private String secondPassword;
    private String message;

    public void initialize(FieldMatch constraintAnnotation) {
        this.firstPassword = constraintAnnotation.first();
        this.secondPassword = constraintAnnotation.second();
        this.message = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object firstObject = new BeanWrapperImpl(value).getPropertyValue(firstPassword);
        Object secondObject = new BeanWrapperImpl(value).getPropertyValue(secondPassword);

        boolean valid = firstObject != null && firstObject.equals(secondObject);

        if (!valid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return valid;
    }
}
