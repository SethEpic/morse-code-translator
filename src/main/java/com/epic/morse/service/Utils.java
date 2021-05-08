package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

final class Utils {

    static String createSpaceRegex(String var1) {
        final String spaceRegexTemplate = "(?<=\\.|-)(\\s){%d}(?=\\.|-)";
        return String.format(spaceRegexTemplate, numberOfSpaces(var1));
    }

    static String createWordSeparatorRegex() {
        final String spaceRegexTemplate = "(?<=\\.|-)%s(?=\\.|-)";
        final String wordSeparator = MorseCodeConfig.getInstance().getWordSeparator();

        if (wordSeparator.isBlank()) {
            String separatorRegex = String.format("(\\s){%d}", numberOfSpaces(wordSeparator));
            return String.format(spaceRegexTemplate, separatorRegex);
        }

        if (separatorIsEscapeChar(wordSeparator)) {
            return String.format("(?<=\\.|-)([\\%s])(?=\\.|-)", MorseCodeConfig.getInstance().getWordSeparator());
        }

        return String.format("(?<=\\.|-)([%s])(?=\\.|-)", MorseCodeConfig.getInstance().getWordSeparator());
    }

    static int numberOfSpaces(String var1) {
        if (var1 == null) {
            return 0;
        }

        long countOfSpaces = var1.chars().filter(c -> c == (int) ' ').count();
        return (int) (countOfSpaces);
    }

    private static boolean separatorIsEscapeChar(String separator) {
        return separator.equals("\\");
    }
}
