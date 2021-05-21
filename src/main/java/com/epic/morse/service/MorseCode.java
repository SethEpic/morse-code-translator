package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

import static com.epic.morse.service.Utils.multiSpaceRegex;

public final class MorseCode {
    private static final ValidationService validationService = new ValidationServiceImpl();
    private static final String SPACE = " ";

    public static String convertToMorseCode(String message) {
        validationService.validateTextToMorseCode(message);
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        final String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
        final MorseCodeType type = MorseCodeConfig.getInstance().getMorseCodeType();
        return covertCharsToMorseCode(message, type, wordSeparator, letterSeparator);
    }

    public static String convertToText(String morseCode) {
        validationService.validateMorseCodeToText(morseCode);
        final String wordSeparatorRegex = Utils.createWordSeparatorRegex();
        final String letterSeparatorRegex = Utils.createLetterSeparatorRegex();
        final MorseCodeType type = MorseCodeConfig.getInstance().getMorseCodeType();
        return convertMorseCodeToText(morseCode, type, wordSeparatorRegex, letterSeparatorRegex);
    }

    private static String covertCharsToMorseCode(String message, MorseCodeType type, String wordSeparator, String letterSeparator) {
        StringBuilder morseCodeBuilder = new StringBuilder();

        for (char character : message.toUpperCase().trim().replaceAll(multiSpaceRegex, SPACE).toCharArray()) {
            if (Character.isWhitespace(character)) {
                appendSpace(morseCodeBuilder, wordSeparator, letterSeparator);
                continue;
            }

            String morseCodeCharacter = MorseCodeLanguages.getMorseCodeCharacter(type, String.valueOf(character));
            morseCodeBuilder.append(morseCodeCharacter).append(letterSeparator);
        }

        removeSeparatorsAtEnd(morseCodeBuilder, wordSeparator, letterSeparator);
        return morseCodeBuilder.toString().trim();
    }

    private static String convertMorseCodeToText(String morseCode, MorseCodeType type, String wordSeparatorRegex, String letterSeparatorRegex) {
        StringBuilder convertedToText = new StringBuilder();

        for (String word : morseCode.trim().split(wordSeparatorRegex)) {
            for (String letter : word.split(letterSeparatorRegex)) {
                convertedToText.append(MorseCodeLanguages.getTextCharacter(type, letter));
            }
            convertedToText.append(SPACE);
        }

        return convertedToText.toString().trim();
    }

    private static void appendSpace(StringBuilder morseCodeBuilder, String wordSeparator, String letterSeparator) {
        final String morseCodeString = morseCodeBuilder.toString().trim();

        if (morseCodeString.endsWith(letterSeparator)) {
            morseCodeBuilder.replace(morseCodeBuilder.length() - letterSeparator.length(), morseCodeBuilder.length(), "").append(wordSeparator);
        }
    }

    private static void removeSeparatorsAtEnd(StringBuilder morseCodeBuilder, String wordSeparator, String letterSeparator) {
        final String morseCodeString = morseCodeBuilder.toString().trim();

        if (morseCodeString.endsWith(letterSeparator)) {
            morseCodeBuilder.replace(morseCodeBuilder.length() - letterSeparator.length(), morseCodeBuilder.length(), "");
        }

        if (morseCodeString.endsWith(wordSeparator)) {
            morseCodeBuilder.replace(morseCodeBuilder.length() - letterSeparator.length(), morseCodeBuilder.length(), "");
        }
    }
}
