package com.epic.morse.service;

import java.util.List;
import java.util.regex.Pattern;

final class RegexUtils {
    private static final List<String> escapeChars = List.of("^", "\\", "|", "[", "]", "+");
    static final Pattern multiSpaceRegex = Pattern.compile("\\s+");
    //    private static final StringBuilder separatorRegexStart = new StringBuilder("(?<=\\.|-|\\s*)");
//    private static final StringBuilder separatorRegexEnd = new StringBuilder("(?=\\.|-|\\s*)");
    private static Pattern wordSeparatorRegex = Pattern.compile("(?<=[.-])( {2})(?=[.-])");
    private static Pattern letterSeparatorRegex = Pattern.compile("(?<=[.-])( )(?=[.-])");
    private static Pattern invalidMorseCodeRegex = Pattern.compile("([^-.\\s]+)");
    private static String wordSeparatorCache = MorseCodeConfig.getInstance().getWordSeparator();
    private static String letterSeparatorCache = MorseCodeConfig.getInstance().getLetterSeparator();

    private RegexUtils() {
    }

    static Pattern getWordSeparatorRegex() {
        return wordSeparatorRegex;
    }

    static Pattern getLetterSeparatorRegex() {
        return letterSeparatorRegex;
    }

    static Pattern getInvalidMorseCodeRegex() {
        return invalidMorseCodeRegex;
    }

    static void updateWordRegexCache(String wordSeparator, MorseCodeType morseCodeType) {
        wordSeparatorCache = wordSeparator;
        invalidMorseCodeRegex = createInvalidMorseCodeRegex(wordSeparator, letterSeparatorCache, morseCodeType);
        wordSeparatorRegex = Pattern.compile(createSeparatorRegex(wordSeparator, morseCodeType));
    }

    static void updateLetterRegexCache(String letterSeparator, MorseCodeType morseCodeType) {
        letterSeparatorCache = letterSeparator;
        invalidMorseCodeRegex = createInvalidMorseCodeRegex(wordSeparatorCache, letterSeparator, morseCodeType);
        letterSeparatorRegex = Pattern.compile(createSeparatorRegex(letterSeparator, morseCodeType));
    }

    static void resetInvalidMorseCodeRegex(MorseCodeType morseCodeType) {
        if (morseCodeType.isInternational()) {
            invalidMorseCodeRegex = Pattern.compile("([^-.\\s]+)");
//            invalidMorseCodeRegex = Pattern.compile("([^-.|/\\s]+)");
        } else {
            invalidMorseCodeRegex = Pattern.compile("([^-⸺⸻.|/\\s]+)");
        }
    }

    private static String createSeparatorRegex(String separator, MorseCodeType morseCodeType) {
        StringBuilder separatorRegexBuilder = new StringBuilder("(?<=[");
        String regexEnd;

        if (morseCodeType.isInternational()) {
            /*TODO if fail try \\s after the .- */
            separatorRegexBuilder.append(".-])(");
            regexEnd = "(?=[.-])";
        } else {
            separatorRegexBuilder.append("⸺⸻.-])(");
            regexEnd = "(?=[⸺⸻.-])";
        }

        if (separator.isBlank()) {
            separatorRegexBuilder.append(" {").append(separator.length()).append("}");
        } else if (shouldEscapeSeparator(separator)) {
            separatorRegexBuilder.append('[').append(escChars(separator)).append("]");
        } else {
            separatorRegexBuilder.append(separator);
        }

        return separatorRegexBuilder.append(')').append(regexEnd).toString();
    }

    private static Pattern createInvalidMorseCodeRegex(String wordSeparator, String letterSeparator, MorseCodeType morseCodeType) {
        final StringBuilder regexBuilder = new StringBuilder("([^-.\\s");
        if (morseCodeType.isAmerican()) {
            regexBuilder.append("⸺⸻|/");
        }

        if (!wordSeparator.isBlank()) {
            regexBuilder.append(shouldEscapeSeparator(wordSeparator) ? escChars(wordSeparator) : wordSeparator);
        }

        if (!letterSeparator.isBlank()) {
            regexBuilder.append(shouldEscapeSeparator(letterSeparator) ? escChars(letterSeparator) : letterSeparator);
        }

        return Pattern.compile(regexBuilder.append("]+)").toString());
    }

    private static boolean shouldEscapeSeparator(String separator) {
        for (var c : escapeChars) {
            if (separator.contains(c)) return true;
        }
        return false;
    }

    private static StringBuilder escChars(String separator) {
        StringBuilder builder = new StringBuilder();
        for (char c : separator.toCharArray()) {
            builder.append('\\').append(c);
        }
        return builder;
    }
}
