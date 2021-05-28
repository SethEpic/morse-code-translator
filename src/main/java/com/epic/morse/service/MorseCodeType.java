package com.epic.morse.service;

public enum MorseCodeType {
    INTERNATIONAL(".", "-", null, null), AMERICAN(".", "-", "--", "---");

    private String dot;
    private String dash;
    private String longDash;
    private String longerDash;

    MorseCodeType(String dot, String dash, String longDash, String longerDash) {
        this.dot = dot;
        this.dash = dash;
        this.longDash = longDash;
        this.longerDash = longerDash;
    }

    public String getDot() {
        return dot;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public String getDash() {
        return dash;
    }

    public void setDash(String dash) {
        this.dash = dash;
    }

    public String getLongDash() {
        return longDash;
    }

    public void setLongDash(String longDash) {
        this.longDash = longDash;
    }

    public String getLongerDash() {
        return longerDash;
    }

    public void setLongerDash(String longerDash) {
        this.longerDash = longerDash;
    }
}
