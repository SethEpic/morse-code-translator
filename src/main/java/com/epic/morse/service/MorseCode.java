package com.epic.morse.service;

import static com.epic.morse.service.Utils.SPACE;
import static com.epic.morse.service.Utils.multiSpaceRegex;

public final class MorseCode {
    private static final ValidationService validationService = new ValidationServiceImpl();
    private static final String empty = "";

    private MorseCode() {
    }

    public static String convertToMorseCode(String message) {
        validationService.validateTextToMorseCode(message);
        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        final String wordSeparator = morseCodeConfig.getWordSeparator();
        final String letterSeparator = morseCodeConfig.getLetterSeparator();
        return covertTextToMorseCode(message, wordSeparator, letterSeparator);
    }

    public static String convertToText(String morseCode) {
        validationService.validateMorseCodeToText(morseCode);
        final String wordSeparatorRegex = Utils.createWordSeparatorRegex();
        final String letterSeparatorRegex = Utils.createLetterSeparatorRegex();
        return convertMorseCodeToText(morseCode, wordSeparatorRegex, letterSeparatorRegex);
    }

    private static String covertTextToMorseCode(String message, String wordSeparator, String letterSeparator) {
        StringBuilder morseCodeBuilder = new StringBuilder();
        char[] characters = message.toUpperCase().trim().replaceAll(multiSpaceRegex, SPACE).toCharArray();

        for (char character : characters) {
            if (Character.isWhitespace(character)) {
                appendSpace(morseCodeBuilder, wordSeparator, letterSeparator);
                continue;
            }

            String morseCodeCharacter = MorseCodeSearcher.getMorseCodeCharacter(String.valueOf(character));
            morseCodeBuilder.append(morseCodeCharacter).append(letterSeparator);
        }

        removeSeparatorsAtEnd(morseCodeBuilder, wordSeparator, letterSeparator);
        return morseCodeBuilder.toString().trim();
    }

    private static String convertMorseCodeToText(String morseCode, String wordSepRegex, String letterSepRegex) {
        StringBuilder convertedToText = new StringBuilder();
        String[] words = morseCode.trim().split(wordSepRegex);

        for (String word : words) {
            String[] letters = word.split(letterSepRegex);
            for (String letter : letters) {
                convertedToText.append(MorseCodeSearcher.getTextCharacter(letter));
            }
            convertedToText.append(SPACE);
        }

        return convertedToText.toString().trim();
    }

    private static void appendSpace(StringBuilder morseCode, String wordSeparator, String letterSeparator) {
        if (morseCode.lastIndexOf(letterSeparator) == (morseCode.length() - letterSeparator.length())) {
            morseCode.replace(morseCode.length() - letterSeparator.length(), morseCode.length(), empty);
        }
        morseCode.append(wordSeparator);
    }

    private static void removeSeparatorsAtEnd(StringBuilder morseCode, String wordSeparator, String letterSeparator) {
        if (morseCode.lastIndexOf(letterSeparator) == (morseCode.length() - letterSeparator.length())) {
            morseCode.replace(morseCode.length() - letterSeparator.length(), morseCode.length(), empty);
        }

        if (morseCode.lastIndexOf(wordSeparator) == (morseCode.length() - wordSeparator.length())) {
            morseCode.replace(morseCode.length() - wordSeparator.length(), morseCode.length(), empty);
        }
    }
}
