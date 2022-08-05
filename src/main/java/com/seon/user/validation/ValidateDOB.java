package com.seon.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidatorLocalDate.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateDOB {
    String message() default "Invalid date of birth number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
