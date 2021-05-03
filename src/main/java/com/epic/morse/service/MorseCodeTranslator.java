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
        String morseCode = convertToMorseCode("Hello World");
        System.out.println(morseCode);
        System.out.println(convertToText(morseCode));
    }

    public static String convertToMorseCode(String message) {
        message = message.toUpperCase();
        validationService.validateTextToMorseCode(message);

        String letterSeparator = MorseCodeConfig.getInstance().getLetterSeparator();
        String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();
        StringBuilder morseCode = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (MorseCode.SPACE.getCharacter().equalsIgnoreCase(String.valueOf(character))) {
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
        String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();

        for (String word : morseCode.split(Utils.createSpaceRegex(wordSeparator))) {
            for (String letter : word.split(Utils.createSpaceRegex(letterSeparator))) {
                text.append(MorseCode.valueOfMorseCode(letter.trim()));
            }
            text.append(" ");
        }
        return text.toString();
    }

    private static void removeLastSpace(StringBuilder morseCode) {
        morseCode.replace(morseCode.length() - 1, morseCode.length(), "");
    }
}
