package com.gwtplatform.mvp.databind.client.validation;

/**
 * @author Danilo Reinert
 */
public class ValidationMessage {

    public static enum Type {SUCCESS, ERROR, WARNING, INFO, NONE}

    private final String message;
    private final Type type;

    public ValidationMessage(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }
}
