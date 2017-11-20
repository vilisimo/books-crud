package vault.validation.annotations;

import vault.validation.UuidValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Constraint(validatedBy = UuidValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UUID {

    String message() default "is not a valid UUID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
