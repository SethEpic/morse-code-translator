package com.epic.morse.service;

import java.util.regex.Pattern;

public final class MorseCode {
    private static final StringBuilder SPACE = new StringBuilder(" ");
    private static final String SPACE_STR = " ";

    private MorseCode() {
    }

    public static String convertToMorseCode(String message) {
        ValidationService.validateTextToMorseCode(message);
        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        return covertTextToMorseCode(message, morseCodeConfig.getWordSeparator(), morseCodeConfig.getLetterSeparator());
    }

    public static String convertToText(String morseCode) {
        ValidationService.validateMorseCodeToText(morseCode);
        return convertMorseCodeToText(morseCode, Utils.getWordSeparatorRegex(), Utils.getLetterSeparatorRegex());
    }

    private static String covertTextToMorseCode(String message, String wordSeparator, String letterSeparator) {
        final StringBuilder morseCodeBuilder = new StringBuilder();
        final char[] characters = Utils.multiSpaceRegex.matcher(message.toUpperCase().trim()).replaceAll(SPACE_STR).toCharArray();
        final int letterSeparatorLength = letterSeparator.length();

        for (char character : characters) {
            if (Character.isWhitespace(character)) {
                morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - letterSeparatorLength).append(wordSeparator);
                continue;
            }

            morseCodeBuilder.append(MorseCodeSearcher.getMorseCodeCharacter(character)).append(letterSeparator);
        }

        return morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - letterSeparatorLength).toString();
    }

    private static String convertMorseCodeToText(String morseCode, Pattern wordSepRegex, Pattern letterSepRegex) {
        final StringBuilder convertedToText = new StringBuilder();
        final String[] words = wordSepRegex.split(morseCode.trim());

        for (String word : words) {
            String[] letters = letterSepRegex.split(word);
            for (String letter : letters) {
                convertedToText.append(MorseCodeSearcher.getTextCharacter(letter));
            }
            convertedToText.append(SPACE);
        }

        return convertedToText.toString().trim();
    }
}
