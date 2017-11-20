package vault.validation;

import vault.validation.annotations.UUID;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UuidValidator implements ConstraintValidator<UUID, String> {

    private static final Pattern pattern =
            Pattern.compile("^[0-9a-f]{8}-?[0-9a-f]{4}-?[1-5][0-9a-f]{3}-?[89ab][0-9a-f]{3}-?[0-9a-f]{12}$");

    @Override
    public void initialize(UUID constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return pattern.matcher(value).matches();
    }
}
