package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;
import com.epic.morse.exception.ExceptionMessages;
import com.epic.morse.exception.MorseCodeException;

import java.util.regex.Pattern;

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

        if (createMorseCodeRegex().matcher(morseCode).find()) {
            System.out.println("BAD");
        } else System.out.println("GOOD");
    }

    private Pattern createMorseCodeRegex() {
        MorseCodeConfig.getInstance().setLetterSeparator("'");
        MorseCodeConfig.getInstance().setWordSeparator(" ");
        String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();

        String morseCodeRegex = "[^.-]+";
        if (letterSeparator.isBlank() || wordSeparator.isBlank()) {
            morseCodeRegex = morseCodeRegex.replace("[^", "[^\\s");
        }

        if (!letterSeparator.isBlank()) {
            morseCodeRegex = morseCodeRegex.replace("-]+", letterSeparator + "-]+");
        }

        if (!wordSeparator.isBlank()) {
            morseCodeRegex = morseCodeRegex.replace("-]+", wordSeparator + "-]+");
        }

        return Pattern.compile(morseCodeRegex);
    }

    public static void main(String[] args) {
        new ValidationServiceImpl().validateMorseCodeToText("...---...");
    }

    @Override
    public void validateSeparator(String var1, String separatorName) {
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

    private boolean isAnyAlphaNumeric(String var1) {
        return var1 != null && !var1.isEmpty() && var1.chars().anyMatch(Character::isLetterOrDigit);
    }
}
