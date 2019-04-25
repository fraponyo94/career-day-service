package task.sancom.restapi.careerdayservice.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InterviewEndTimeConstraint {
    String message() default "Interview End Time Cannot be before Interview Start time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}