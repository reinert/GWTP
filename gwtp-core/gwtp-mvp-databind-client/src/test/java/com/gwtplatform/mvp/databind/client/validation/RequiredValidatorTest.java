package com.gwtplatform.mvp.databind.client.validation;

import junit.framework.TestCase;

/**
 * @author Danilo Reinert
 */
public class RequiredValidatorTest extends TestCase {

    public void testValidation() {
        final Object model = new Object();
        final String message = "value is required";
        RequiredValidator<Object, Object> validator = new RequiredValidator<Object, Object>(message);

        final Validation invalidValidation = validator.validate(model, null);
        assertFalse(invalidValidation.isValid());
        assertEquals(invalidValidation.getValidationMessage().getMessage(), message);

        final Validation validValidation = validator.validate(model, new Object());
        assertTrue(validValidation.isValid());
    }
}
