package com.epic.morse.service;

import com.epic.morse.exception.ExceptionMessages;
import com.epic.morse.exception.MorseCodeException;

import java.util.List;

final class ValidationService {
    private static String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
    private static String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();

    private ValidationService() {
    }

    static void updateWordSeparator(String wordSeparator) {
        ValidationService.wordSeparator = wordSeparator;
    }

    static void updateLetterSeparator(String letterSeparator) {
        ValidationService.letterSeparator = letterSeparator;
    }

    public static void validateEncode(String text) {
        if (text == null) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_1);
        }

        if (text.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_2);
        }
    }

    public static void validateEncode(String[] texts) {
        for (int i = 0; i < texts.length; i++) {
            try {
                validateEncode(texts[i]);
            } catch (MorseCodeException morseCodeException) {
                throw new MorseCodeException(String.format(morseCodeException.getErrorMessage() + " | Occurred at array index(%s)", i));
            }
        }
    }

    public static void validateEncode(List<String> texts) {
        for (int i = 0; i < texts.size(); i++) {
            try {
                validateEncode(texts.get(i));
            } catch (MorseCodeException morseCodeException) {
                throw new MorseCodeException(String.format(morseCodeException.getErrorMessage() + " | Occurred at list index(%s)", i));
            }
        }
    }

    public static void validateEncode(char character) {
        if (character == '\u0000') {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_1);
        }
    }

    public static void validateEncode(char[] characters) {
        for (int i = 0; i < characters.length; i++) {
            try {
                validateEncode(characters[i]);
            } catch (MorseCodeException morseCodeException) {
                throw new MorseCodeException(String.format(morseCodeException.getErrorMessage() + " | Occurred at array index(%s)", i));
            }
        }
    }

    public static void validateMorseCodeToText(String morseCode) {
        if (morseCode == null) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_1);
        }

        if (morseCode.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_2);
        }

        if (isAnyAlphaNumeric(morseCode)) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_3);
        }
    }

    static void validateWordSeparator(String wordSeparator) {
        if (wordSeparator == null) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_1);
        }

        if (wordSeparator.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_2);
        }

        if (letterSeparator.equals(wordSeparator)) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_3);
        }

        if (wordSeparator.contains("-")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_4);
        }

        if (wordSeparator.contains(".")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_5);
        }

        ValidationService.wordSeparator = wordSeparator;
    }

    static void validateLetterSeparator(String letterSeparator) {
        if (letterSeparator == null) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_1);
        }

        if (letterSeparator.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_2);
        }

        if (wordSeparator.equals(letterSeparator)) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_3);
        }

        if (letterSeparator.contains("-")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_4);
        }

        if (letterSeparator.contains(".")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_5);
        }

        ValidationService.letterSeparator = letterSeparator;
    }

    private static boolean isAnyAlphaNumeric(String morseCode) {
        return RegexUtils.getInvalidMorseCodeRegex().matcher(morseCode).find();
    }
}
