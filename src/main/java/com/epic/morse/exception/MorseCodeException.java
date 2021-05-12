package com.epic.morse.exception;

public final class MorseCodeException extends RuntimeException {
    private final String errorMessage;

    public MorseCodeException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
