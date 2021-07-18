package com.epic.morse.service;

import java.util.List;
import java.util.regex.Pattern;

public final class Utils {
    private static final List<String> escapeChars = List.of("<", "(", "[", "{", "\\", "^", "=", "$", "!", "|", "]", "}", ")", "?", "*", "+", ">");
    //    private static final Pattern backSlashRegex = Pattern.compile("([\\\\])");
    static final Pattern multiSpaceRegex = Pattern.compile("\\s+");
    private static final StringBuilder separatorRegexStart = new StringBuilder("(?<=\\.|-|\\s*)");
    private static final StringBuilder separatorRegexEnd = new StringBuilder("(?=\\.|-|\\s*)");
    private static Pattern wordSeparatorRegex;
    private static Pattern letterSeparatorRegex;
    private static String wordSeparatorCache;
    private static String letterSeparatorCache;

    private Utils() {
    }

    static {
        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        wordSeparatorCache = morseCodeConfig.getWordSeparator();
        wordSeparatorRegex = Pattern.compile(createSeparatorRegex(wordSeparatorCache));
        letterSeparatorCache = morseCodeConfig.getLetterSeparator();
        letterSeparatorRegex = Pattern.compile(createSeparatorRegex(letterSeparatorCache));
    }

    public static Pattern getWordSeparatorRegex() {
        return wordSeparatorRegex;
    }

    public static Pattern getLetterSeparatorRegex() {
        return letterSeparatorRegex;
    }

    static void updateWordRegexCache(String wordSeparator) {
        if (!wordSeparator.equalsIgnoreCase(wordSeparatorCache)) {
            wordSeparatorCache = wordSeparator;
            wordSeparatorRegex = Pattern.compile(createSeparatorRegex(wordSeparator));
        }
    }

    static void updateLetterRegexCache(String letterSeparator) {
        if (!letterSeparator.equalsIgnoreCase(letterSeparatorCache)) {
            letterSeparatorCache = letterSeparator;
            letterSeparatorRegex = Pattern.compile(createSeparatorRegex(letterSeparator));
        }
    }

    private static String createSeparatorRegex(String separator) {
        if (separator.isBlank()) {
            return "(?<=\\.|-|\\s*)(\\s){" + separator.length() + "}(?=\\.|-|\\s*)";
        } else if (shouldEscapeSeparator(separator)) {
            return createEscSeparatorRegex(separator);
        }

        return "(?<=\\.|-|\\s*)([" + separator + "])(?=\\.|-|\\s*)";
    }

    private static boolean shouldEscapeSeparator(String separator) {
        for (var c : escapeChars) {
            if (separator.contains(c)) {
                return true;
            }
        }
        return false;
    }

    private static String createEscSeparatorRegex(String separator) {
        StringBuilder builder = new StringBuilder(separatorRegexStart).append("([");
        for (char escChar : separator.toCharArray()) {
            builder.append('\\').append(escChar);
        }

        return builder.append("])").append(separatorRegexEnd).toString();
    }
}
