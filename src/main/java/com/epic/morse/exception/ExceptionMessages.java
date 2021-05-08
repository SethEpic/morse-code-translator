package com.epic.morse.exception;

public final class ExceptionMessages {
    public static final String VALIDATION_ERROR_1 = "Validation failed: morseCode String is null";
    public static final String VALIDATION_ERROR_2 = "Validation failed: morseCode String is empty";

    public static final String INVALID_WORD_SEPARATOR_1 = "Validation failed: word Separator cannot be null";
    public static final String INVALID_WORD_SEPARATOR_2 = "Validation failed: word Separator cannot be empty";
    public static final String INVALID_WORD_SEPARATOR_3 = "Validation failed: word Separator cannot be equal to letter separator";

    public static final String INVALID_LETTER_SEPARATOR_1 = "Validation failed: letter Separator cannot be null";
    public static final String INVALID_LETTER_SEPARATOR_2 = "Validation failed: letter Separator cannot be empty";
    public static final String INVALID_LETTER_SEPARATOR_3 = "Validation failed: letter Separator cannot be equal to letter separator";

    public static final String INVALID_SEPARATOR_3 = "Validation failed: %s Separator cannot contain any alpha numeric chars";
    public static final String UNEXPECTED_ERROR = "Unexpected Error - please contact me at my GitHub: https://github.com/SethEpic";
    public static final String VALIDATION_FAILURE = "Validation failed";
}
