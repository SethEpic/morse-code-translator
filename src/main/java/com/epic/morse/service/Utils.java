package com.epic.morse.service;

final class Utils {

    static String createSpaceRegex(String var1) {
        final String spaceRegexTemplate = "(?<=\\.|-)(\\s){%d}(?=\\.|-)";
        return String.format(spaceRegexTemplate, Utils.numberOfSpaces(var1));
    }

    static int numberOfSpaces(String var1) {
        if (var1 == null) {
            return 0;
        }
        long countOfSpaces = var1.chars().filter(c -> c == (int) ' ').count();
        return (int) (countOfSpaces + 1);
    }
}
