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

    @Override
    public void validateMorseCodeType(MorseCodeType type, boolean isUsingDefaultWordSeparator, boolean isUsingDefaultLetterSeparator) {
        if (type.equals(MorseCodeType.INTERNATIONAL)) {
            return;
        }

//        if (type.equals(MorseCodeType.AMERICAN)) {
//            if (MorseCodeConfig.getInstance().isUsingDefaultWordSeparator()) {
//             throw new MorseCodeException("Due to the spacing for the letter gaps in american morse code please set the word separator, if you use spaces please set 3 or more or any number of any other char");
//            }
//        }
    }

    private boolean isAnyAlphaNumeric(String var1) {
        String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
        return var1.replaceAll(wordSeparator, "").replaceAll(letterSeparator, "").chars().anyMatch(Character::isLetterOrDigit);
    }
}
