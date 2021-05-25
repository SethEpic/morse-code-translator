package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;
import com.epic.morse.exception.ExceptionMessages;
import com.epic.morse.exception.MorseCodeException;

public final class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateTextToMorseCode(String text) {
        if (text == null) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_1);
        }

        if (text.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_2);
        }
    }

    @Override
    public void validateMorseCodeToText(String morseCode) {
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

    @Override
    public void validateWordSeparator(String var1) {
        if (var1 == null) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_1);
        }

        if (var1.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_2);
        }

        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        if (var1.equals(morseCodeConfig.getLetterSeparator())) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_3);
        }

        if (var1.contains("-")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_4);
        }

        if (var1.contains(".")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_5);
        }
    }

    @Override
    public void validateLetterSeparator(String var1) {
        if (var1 == null) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_1);
        }

        if (var1.isEmpty()) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_2);
        }

        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        if (var1.equals(morseCodeConfig.getWordSeparator())) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_3);
        }

        if (var1.contains("-")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_4);
        }

        if (var1.contains(".")) {
            throw new MorseCodeException(ExceptionMessages.INVALID_LETTER_SEPARATOR_5);
        }
    }

    private boolean isAnyAlphaNumeric(String var1) {
        String wordSeparatorRegex = Utils.createWordSeparatorRegex();
        String letterSeparatorRegex = Utils.createLetterSeparatorRegex();
        var1 = var1.replaceAll(wordSeparatorRegex, "").replaceAll(letterSeparatorRegex, "");
        return var1.chars().anyMatch(Character::isLetterOrDigit);
    }
}
