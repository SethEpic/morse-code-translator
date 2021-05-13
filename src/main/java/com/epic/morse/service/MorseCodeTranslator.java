package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

public final class MorseCodeTranslator {
    private static final ValidationService validationService = new ValidationServiceImpl();

    public static String convertToMorseCode(String message) {
        message = message.trim().toUpperCase().replaceAll("\\s+", " ");
        validationService.validateTextToMorseCode(message);

        final String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        StringBuilder morseCode = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (Character.isWhitespace(character)) {
                removeLastSpace(morseCode);
                morseCode.append(wordSeparator);
                continue;
            }
            morseCode.append(MorseCode.valueOfCharacter(String.valueOf(character)));
            morseCode.append(letterSeparator);
        }

        return morseCode.toString().trim();
    }

    public static String convertToText(String morseCode) {
        morseCode = morseCode.trim();
        validationService.validateMorseCodeToText(morseCode);
        StringBuilder convertedToText = new StringBuilder();
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        final String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();

        for (String word : morseCode.split(Utils.createSeparatorRegex(wordSeparator))) {
            for (String letter : word.split(Utils.createSeparatorRegex(letterSeparator))) {
                convertedToText.append(MorseCode.valueOfMorseCode(letter.trim()));
            }
            convertedToText.append(" ");
        }

        return convertedToText.toString().trim();
    }

    private static void removeLastSpace(StringBuilder morseCode) {
        morseCode.replace(morseCode.length() - 1, morseCode.length(), "");
    }
}
