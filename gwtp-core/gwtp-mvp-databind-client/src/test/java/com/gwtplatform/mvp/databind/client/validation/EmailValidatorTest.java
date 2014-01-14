package com.gwtplatform.mvp.databind.client.validation;

import junit.framework.TestCase;

/**
 * @author Danilo Reinert
 */
public class EmailValidatorTest extends TestCase {

    public void testValidation() {
        final Object model = new Object();
        final String message = "email invalid";
        EmailValidator<Object> validator = new EmailValidator<Object>(message);

        // Test malformed email
        final String invalidEmail = "abc.def";
        final Validation invalidValidation = validator.validate(model, invalidEmail);
        assertFalse(invalidValidation.isValid());
        assertEquals(invalidValidation.getValidationMessage().getMessage(), message);

        // Test valid email
        final String validEmail = "abc@def.com";
        final Validation validValidation = validator.validate(model, validEmail);
        assertTrue(validValidation.isValid());

        // Test not required
        final Validation nullInvalidValidation = validator.validate(model, null);
        assertTrue(nullInvalidValidation.isValid());
    }

    public void testValidationRequired() {
        final Object model = new Object();
        final String message = "email invalid";
        EmailValidator<Object> validator = new EmailValidator<Object>(message, true);

        // Test required
        Validation nullInvalidValidation = validator.validate(model, null);
        assertFalse(nullInvalidValidation.isValid());
        assertEquals(nullInvalidValidation.getValidationMessage().getMessage(), message);

        // Test malformed email
        final String invalidEmail = "abc.def";
        final Validation invalidValidation = validator.validate(model, invalidEmail);
        assertFalse(invalidValidation.isValid());
        assertEquals(invalidValidation.getValidationMessage().getMessage(), message);

        // Different message for null value
        final String nullMessage = "email null";
        validator = new EmailValidator<Object>(message, true, nullMessage);

        // Test required
        nullInvalidValidation = validator.validate(model, null);
        assertFalse(nullInvalidValidation.isValid());
        assertEquals(nullInvalidValidation.getValidationMessage().getMessage(), nullMessage);
    }
}
