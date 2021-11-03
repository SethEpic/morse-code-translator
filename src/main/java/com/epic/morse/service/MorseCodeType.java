package com.epic.morse.service;

public enum MorseCodeType {
    INTERNATIONAL(".", "-", null, null),
    AMERICAN(".", "-", "⸺", "⸻");

    private final String dot;
    private final String dash;
    private final String longDash;
    private final String longerDash;

    MorseCodeType(String dot, String dash, String longDash, String longerDash) {
        this.dot = dot;
        this.dash = dash;
        this.longDash = longDash;
        this.longerDash = longerDash;
    }

    public String getDot() {
        return dot;
    }

    public String getDash() {
        return dash;
    }

    public String getLongDash() {
        return longDash;
    }

    public String getLongerDash() {
        return longerDash;
    }

    public boolean isInternational() {
        return this == INTERNATIONAL;
    }

    public boolean isAmerican() {
        return this == AMERICAN;
    }
}
