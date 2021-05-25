package com.epic.morse.service;

public enum MorseCodeType {
    INTERNATIONAL(".", "-", null), AMERICAN(".", "-", "⸺");

    MorseCodeType(String dot, String dash, String longDash) {

    }
}
