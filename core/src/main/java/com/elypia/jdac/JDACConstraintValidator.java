package com.elypia.jdac;

import com.elypia.commandler.validation.CommandConstraintValidator;

import java.lang.annotation.Annotation;

public abstract class JDACConstraintValidator<A extends Annotation, O> extends CommandConstraintValidator<JDACEvent, A, O> {

    public JDACConstraintValidator(JDACEvent event) {
        super(event);
    }
}
