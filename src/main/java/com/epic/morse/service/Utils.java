package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

import java.util.List;

public final class Utils {
    private static final List<String> escapeChars = List.of("<", "(", "[", "{", "\\", "^", "=", "$", "!", "|", "]", "}", ")", "?", "*", "+", ">");
    private static final String spaceRegexTemplate = "(?<=\\.|-|\\s*)%s(?=\\.|-|\\s*)";
    static final String multiSpaceRegex = "\\s+";
    static final String SPACE = " ";

    public static String createWordSeparatorRegex() {
        return createSeparatorRegex(MorseCodeConfig.getInstance().getWordSeparator());
    }

    public static String createLetterSeparatorRegex() {
        return createSeparatorRegex(MorseCodeConfig.getInstance().getLetterSeparator());
    }

    private static String createSeparatorRegex(String separator) {
        if (MorseCodeConfig.THIN_SPACE.equals(separator)) {
            return MorseCodeConfig.THIN_SPACE;
        } else if (MorseCodeConfig.HAIR_SPACE.equals(separator)) {
            return MorseCodeConfig.HAIR_SPACE;
        }

        if (separator.isBlank()) {
            String separatorRegex = String.format("(\\s){%d}", separator.length());
            return String.format(spaceRegexTemplate, separatorRegex);
        }

        if (escapeChars.stream().anyMatch(separator::contains)) {
            StringBuilder sb = new StringBuilder();
            char[] separatorChars = separator.toCharArray();
            for (char character : separatorChars) {
                if (escapeChars.contains(String.valueOf(character))) {
                    sb.append("\\").append(character);
                }
            }

            if (!sb.isEmpty()) {
                return String.format("(?<=\\.|-|\\s*)([%s])(?=\\.|-|\\s*)", sb);
            }
        }

        return String.format("(?<=\\.|-|\\s*)([%s])(?=\\.|-|\\s*)", separator);
    }
}
