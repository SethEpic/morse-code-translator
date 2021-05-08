package com.epic.morse.config;

public enum Separators {
    SLASH("/", "/"),
    BACKSLASH("\\", "\\")

    ;

    private final String separator;
    private final String separatorRegex;

    Separators(String separator, String separatorRegex) {
        this.separator = separator;
        this.separatorRegex = String.format("(?<=\\.|-)([%s])(?=\\.|-)", separatorRegex);
    }

    public String getSeparator() {
        return separator;
    }

    public String getSeparatorRegex() {
        return separatorRegex;
    }
}
