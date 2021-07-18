package com.epic.morse.service;

import com.epic.morse.exception.ExceptionMessages;
import com.epic.morse.exception.MorseCodeException;

import java.util.regex.Pattern;

final class ValidationService {
    private static String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
    private static String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
    private static Pattern wordLetterRegex;
//    private static final Pattern alphaNumericRegex = Pattern.compile("[A-z0-9]");
//    private static final Pattern invalidChars = Pattern.compile("(#)");

    private ValidationService() {
    }

    static {
        wordLetterRegex = Pattern.compile("[" + wordSeparator + letterSeparator + "]");
    }

    public static void validateTextToMorseCode(String text) {
        if (text == null) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_1);
        }

        if (text.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_2);
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

    public static void validateWordSeparator(String var1) {
        if (wordSeparator.equals(var1)) {
            return;
        }

        if (var1 == null) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_1);
        }

        if (var1.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_2);
        }

        if (var1.equals(letterSeparator)) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_3);
        }

        if (var1.contains("-")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_4);
        }

        if (var1.contains(".")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_5);
        }

        wordSeparator = var1;
        wordLetterRegex = Pattern.compile("[" + wordSeparator + letterSeparator + "]");
    }

    public static void validateLetterSeparator(String var1) {
        if (letterSeparator.equals(var1)) {
            return;
        }

        if (var1 == null) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_1);
        }

        if (var1.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_2);
        }

        if (var1.equals(wordSeparator)) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_3);
        }

        if (var1.contains("-")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_4);
        }

        if (var1.contains(".")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_5);
        }

        letterSeparator = var1;
        wordLetterRegex = Pattern.compile("[" + wordSeparator + letterSeparator + "]");
    }

    private static boolean isAnyAlphaNumeric(String var1) {
        return wordLetterRegex.matcher(var1).replaceAll("").chars().anyMatch(Character::isLetterOrDigit);
    }
}
