package com.gwtplatform.mvp.databind.client.validation;

/**
 * Object that holds a textual message for the user and a type for styling purposes.
 *
 * Usually this message will provide a feedback notifying you him of some occurrence
 * or enabling him to take some action in order to handle an error.
 *
 * @author Danilo Reinert
 */
public class ValidationMessage {

    /**
     * The message type is useful for styling user message.
     *
     * Your {@link ValidationHandler} can present the message in different ways
     * depending on the type.
     */
    public static enum Type {ERROR, INFO, NONE, SUCCESS, WARNING}

    private final String message;
    private final Type type;

    public ValidationMessage(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    /**
     * Retrieves the user message.
     *
     * @return message for the user.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the type of the Validation.
     * It can be useful for decorating message's container in a enlightening way.
     *
     * @return validation type
     *
     * @see ValidationMessage.Type
     */
    public Type getType() {
        return type;
    }
}
