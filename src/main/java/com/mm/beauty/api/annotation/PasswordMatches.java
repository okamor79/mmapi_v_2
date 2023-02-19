package com.mm.beauty.api.annotation;

import com.mm.beauty.api.validations.EmailValidator;
import com.mm.beauty.api.validations.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Password not matches";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
