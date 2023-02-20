package com.mm.beauty.api.annotation;

import com.mm.beauty.api.validations.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface ValidPhone {

    String message() default "Invalid phone";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
