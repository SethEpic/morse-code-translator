package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;
import com.epic.morse.exception.ExceptionMessages;
import com.epic.morse.exception.MorseCodeException;

import java.util.regex.Pattern;

public final class ValidationServiceImpl implements ValidationService {
    private static final Pattern invalidCharRegex = Pattern.compile("([\\\\])");

    public static void main(String[] args) {
        System.out.println(new ValidationServiceImpl().createMorseCodeRegex());
    }

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

        if (createMorseCodeRegex().matcher(morseCode).find()) {

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

        if (MorseCodeConfig.getInstance().getLetterSeparator().equalsIgnoreCase(var1)) {
            throw new MorseCodeException(ExceptionMessages.INVALID_WORD_SEPARATOR_3);
        }


        if (this.isAnyAlphaNumeric(var1)) {
//            throw new MorseCodeException(String.format(ExceptionMessages.INVALID_SEPARATOR_3, separatorName));
        }
    }

    @Override
    public void validateLetterSeparator(String var1) {
        if (var1 == null) {
            throw new MorseCodeException(ExceptionMessages.VALIDATION_ERROR_1);
        }

        if (var1.isEmpty()) {
            throw new MorseCodeException("morseCode String is null please provide a non null string");
        }

        if (this.isAnyAlphaNumeric(var1)) {
//            throw new MorseCodeException(String.format(ExceptionMessages.INVALID_SEPARATOR_3, separatorName));
        }
    }

    private Pattern createMorseCodeRegex() {
        final MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();

        if (morseCodeConfig.isUsingDefaultWordSeparator() && !morseCodeConfig.isUsingDefaultLetterSeparator()) {
            return Pattern.compile("[^\\s.-]".replace("]", morseCodeConfig.getLetterSeparator() + "]"));
        } else if (morseCodeConfig.isUsingDefaultLetterSeparator() && !morseCodeConfig.isUsingDefaultWordSeparator()) {
            return Pattern.compile("[^\\s.-]".replace("]", morseCodeConfig.getWordSeparator() + "]"));
        } else {
            return Pattern.compile("[^\\s.-]");
        }
    }

    private boolean isAnyAlphaNumeric(String var1) {
        return var1 != null && !var1.isEmpty() && var1.chars().anyMatch(Character::isLetterOrDigit);
    }
}
