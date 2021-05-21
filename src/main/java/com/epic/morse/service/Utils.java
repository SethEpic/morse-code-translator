package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

import java.util.List;

final class Utils {
    private static final List<String> escapeChars = List.of("<", "(", "[", "{", "\\", "^", "=", "$", "!", "|", "]", "}", ")", "?", "*", "+", ">");
    static final String multiSpaceRegex = "\\s+";

    static String createWordSeparatorRegex() {
        return createSeparatorRegex(MorseCodeConfig.getInstance().getWordSeparator());
    }

    static String createLetterSeparatorRegex() {
        return createSeparatorRegex(MorseCodeConfig.getInstance().getLetterSeparator());
    }

    static String createSeparatorRegex(String separator) {
        final String spaceRegexTemplate = "(?<=\\.|-)%s(?=\\.|-)";

        if (separator.isBlank()) {
            String separatorRegex = String.format("(\\s){%d}", separator.length());
            return String.format(spaceRegexTemplate, separatorRegex);
        }

        if (escapeChars.stream().anyMatch(separator::contains)) {
            StringBuilder sb = new StringBuilder();
            for (char c : separator.toCharArray()) {
                if (escapeChars.contains(String.valueOf(c))) {
                    sb.append("\\").append(c);
                }
            }

            if (!sb.isEmpty()) {
                return String.format("(?<=\\.|-)([%s])(?=\\.|-)", sb);
            }
        }

        return String.format("(?<=\\.|-)([%s])(?=\\.|-)", separator);
    }

    private static int numberOfSpaces(String var1) {
        long countOfSpaces = var1.chars().filter(c -> c == (int) ' ').count();
        return (int) (countOfSpaces);
    }
}
