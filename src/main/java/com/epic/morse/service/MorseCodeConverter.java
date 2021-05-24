package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

import static com.epic.morse.service.Utils.multiSpaceRegex;

public final class MorseCodeConverter {
    private static final ValidationService validationService = new ValidationServiceImpl();
    private static final String SPACE = " ";

    private MorseCodeConverter() {
    }

    public static void main(String[] args) {
        MorseCodeConfig.getInstance().setLetterSeparator("   ");
        String test = convertToMorseCode("Hello world");
        System.out.println(convertToText(test));
    }

    public static String convertToMorseCode(String message) {
        validationService.validateTextToMorseCode(message);
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        final String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
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

            String morseCodeCharacter = MorseCode.getMorseCodeCharacter(String.valueOf(character));
            morseCodeBuilder.append(morseCodeCharacter).append(letterSeparator);
        }

        removeSeparatorsAtEnd(morseCodeBuilder, wordSeparator, letterSeparator);
        return morseCodeBuilder.toString().trim();
    }

    private static String convertMorseCodeToText(String morseCode, String wordSeparatorRegex, String letterSeparatorRegex) {
        StringBuilder convertedToText = new StringBuilder();

        String[] words = morseCode.trim().split(wordSeparatorRegex);
        for (String word : words) {
            String[] letters = word.split(letterSeparatorRegex);
            for (String letter : letters) {
                convertedToText.append(MorseCode.getTextCharacter(letter));
            }
            convertedToText.append(SPACE);
        }

        return convertedToText.toString().trim();
    }

    private static void appendSpace(StringBuilder morseCodeBuilder, String wordSeparator, String letterSeparator) {
        if (morseCodeBuilder.lastIndexOf(letterSeparator) == (morseCodeBuilder.length() - letterSeparator.length())) {
            morseCodeBuilder.replace(morseCodeBuilder.length() - letterSeparator.length(), morseCodeBuilder.length(), "");
        }
        morseCodeBuilder.append(wordSeparator);
    }

    private static void removeSeparatorsAtEnd(StringBuilder morseCodeBuilder, String wordSeparator, String letterSeparator) {
        if (morseCodeBuilder.lastIndexOf(letterSeparator) == (morseCodeBuilder.length() - letterSeparator.length())) {
            morseCodeBuilder.replace(morseCodeBuilder.length() - letterSeparator.length(), morseCodeBuilder.length(), "");
        }

        if (morseCodeBuilder.lastIndexOf(wordSeparator) == (morseCodeBuilder.length() - wordSeparator.length())) {
            morseCodeBuilder.replace(morseCodeBuilder.length() - wordSeparator.length(), morseCodeBuilder.length(), "");
        }
    }
}
