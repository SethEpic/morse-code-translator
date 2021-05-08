package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

/**
 * <h1>MorseCodeConverter</h1>
 * This class is used to convert text into morse code or morse code into text
 *
 * @author SethEpic
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MorseCodeTranslator {
    private static final ValidationService validationService = new ValidationServiceImpl();

    public static void main(String[] args) {
//        MorseCodeConfig.getInstance().setWordSeparator("/");
        String morseCode = convertToMorseCode("Hello    World");
        System.out.println(morseCode);
        System.out.println(convertToText(morseCode));


        MorseCodeConfig.getInstance().setWordSeparator("\\");
        morseCode = convertToMorseCode("Hello    World");
        System.out.println(morseCode);
        System.out.println(convertToText(morseCode));
    }

    public static String convertToMorseCode(String message) {
        message = message.toUpperCase().replaceAll("\\s+", " ");
        validationService.validateTextToMorseCode(message);

        final String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        StringBuilder morseCode = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (isSpace(character)) {
                removeLastSpace(morseCode);
                morseCode.append(wordSeparator);
            } else {
                morseCode.append(MorseCode.valueOfCharacter(String.valueOf(character)));
                morseCode.append(letterSeparator);
            }
        }

        removeLastSpace(morseCode);
        return morseCode.toString();
    }

    public static String convertToText(String morseCode) {
        validationService.validateMorseCodeToText(morseCode);
        StringBuilder text = new StringBuilder();
        String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();

        for (String word : morseCode.split(Utils.createWordSeparatorRegex())) {
            for (String letter : word.split(Utils.createSpaceRegex(letterSeparator))) {
                text.append(MorseCode.valueOfMorseCode(letter.trim()));
            }
            text.append(" ");
        }
        return text.toString();
    }

    private static char lastChar(StringBuilder morseCode) {
        if (morseCode.length() > 0) {
            return morseCode.charAt(morseCode.length() - 1);
        } else {
            return '-';
        }
    }

    private static boolean isSpace(char c) {
        return MorseCode.SPACE.getCharacter().equalsIgnoreCase(String.valueOf(c));
    }

    private static boolean isWordSeparator(char c) {
        return MorseCodeConfig.getInstance().getWordSeparator().equalsIgnoreCase(String.valueOf(c));
    }

    private static void removeLastSpace(StringBuilder morseCode) {
        morseCode.replace(morseCode.length() - 1, morseCode.length(), "");
    }
}
