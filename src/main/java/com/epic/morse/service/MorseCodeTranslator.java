package com.epic.morse.service;

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
        convertToMorseCode("Hello World");
    }

    public static String convertToMorseCode(String message) {
        validationService.validateTextToMorseCode(message);
        StringBuilder morseCode = new StringBuilder();
        message = message.toUpperCase();

        for (char character : message.toCharArray()) {
            if (character == (' ')) {
                removeLastSpace(morseCode);
            }
            morseCode.append(MorseCode.valueOfCharacter(character));
        }

        removeLastSpace(morseCode);
        System.out.println(morseCode);
        return morseCode.toString();
    }

    public String convertToText(String morseCode) {
        validationService.validateMorseCodeToText(morseCode);
        return null;
    }

    private static void removeLastSpace(StringBuilder morseCode) {
        morseCode.replace(morseCode.length() - 1, morseCode.length(), "");
    }
}
