package com.epic.morse.exception;

public final class ExceptionMessages {
    public static final String VALIDATION_ERROR_1 = "Validation failed: morseCode String is null";
    public static final String VALIDATION_ERROR_2 = "Validation failed: morseCode String is empty";
    public static final String VALIDATION_ERROR_3 = "Validation failed: morseCode contains alphanumeric value";

    public static final String INVALID_WORD_SEPARATOR_1 = "Validation failed: word separator cannot be null";
    public static final String INVALID_WORD_SEPARATOR_2 = "Validation failed: word separator cannot be empty";
    public static final String INVALID_WORD_SEPARATOR_3 = "Validation failed: word separator cannot be equal to letter separator";
    public static final String INVALID_WORD_SEPARATOR_4 = "Validation failed: word separator cannot contain '-'";
    public static final String INVALID_WORD_SEPARATOR_5 = "Validation failed: word separator cannot contain '.'";

    public static final String INVALID_LETTER_SEPARATOR_1 = "Validation failed: letter separator cannot be null";
    public static final String INVALID_LETTER_SEPARATOR_2 = "Validation failed: letter separator cannot be empty";
    public static final String INVALID_LETTER_SEPARATOR_3 = "Validation failed: letter separator cannot be equal to word separator";
    public static final String INVALID_LETTER_SEPARATOR_4 = "Validation failed: letter separator contain '-'";
    public static final String INVALID_LETTER_SEPARATOR_5 = "Validation failed: letter separator contain '.'";

    private ExceptionMessages() {
    }
}
