package com.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityCardNumberValidator implements ConstraintValidator<IdentityCardNumber,Object> {
    @Override
    public void initialize(IdentityCardNumber identityCardNumber) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        //简单校验身份证
        return o.toString().length() == 18;
    }
}
