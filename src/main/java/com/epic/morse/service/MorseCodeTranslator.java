package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

public final class MorseCodeTranslator {
    private static final ValidationService validationService = new ValidationServiceImpl();
    private static final char SPACE = ' ';

    public static void main(String[] args) {
        String x = convertToMorseCode("hello w o rld");
        System.out.println(x);
        System.out.println("\n" + convertToText(x));
    }

    public static String convertToMorseCode(String message) {
        validationService.validateTextToMorseCode(message);
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        final String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
        StringBuilder morseCode = new StringBuilder();

        for (char character : message.trim().replaceAll("\\s+", " ").toCharArray()) {
            if (Character.isWhitespace(character)) {
                removeLastSpace(morseCode);
                morseCode.append(wordSeparator);
                continue;
            }

            String morseLetter = MorseCode.valueOfCharacter(String.valueOf(character));
            morseCode.append(morseLetter).append(letterSeparator);
        }

        return morseCode.toString().trim();
    }

    public static String convertToText(String morseCode) {
        validationService.validateMorseCodeToText(morseCode);
        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        final String wordSeparatorRegex = Utils.createSeparatorRegex(morseCodeConfig.getWordSeparator());
        final String letterSeparatorRegex = Utils.createSeparatorRegex(morseCodeConfig.getLetterSeparator());
        StringBuilder convertedToText = new StringBuilder();

        for (String word : morseCode.trim().split(wordSeparatorRegex)) {
            for (String letter : word.split(letterSeparatorRegex)) {
                convertedToText.append(MorseCode.getCharacterByMorseCodeLetter(letter));
            }
            convertedToText.append(SPACE);
        }

        return convertedToText.toString().trim();
    }

    private static void removeLastSpace(StringBuilder morseCode) {
        morseCode.replace(morseCode.length() - 1, morseCode.length(), "");
    }
}
