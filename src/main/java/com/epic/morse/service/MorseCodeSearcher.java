package com.epic.morse.service;

import com.epic.morse.service.languages.AmericanMorseCode;
import com.epic.morse.service.languages.InternationalMorseCode;
import com.epic.morse.service.languages.MorseCodeLanguage;

import java.util.HashMap;
import java.util.Map;

final class MorseCodeSearcher {
    private static final Map<Character, String> textCache = new HashMap<>();
    private static final Map<String, String> morseCodeCache = new HashMap<>();
    private static final MorseCodeLanguage[] internationalValues = InternationalMorseCode.values();
    private static final MorseCodeLanguage[] americanValues = AmericanMorseCode.values();

    private MorseCodeSearcher() {
    }

    static {
        setLanguageCaches(MorseCodeConfig.getInstance().getMorseCodeType());
    }

    static void setLanguageCaches(MorseCodeType morseCodeType) {
        textCache.clear();
        morseCodeCache.clear();

        if (MorseCodeType.INTERNATIONAL.equals(morseCodeType)) {
            for (MorseCodeLanguage international : internationalValues) {
                textCache.put(international.getTextCharacter().charAt(0), international.getMorseCodeCharacter());
                morseCodeCache.put(international.getMorseCodeCharacter(), international.getTextCharacter());
            }
        } else if (MorseCodeType.AMERICAN.equals(morseCodeType)) {
            for (MorseCodeLanguage american : americanValues) {
                textCache.put(american.getTextCharacter().charAt(0), american.getMorseCodeCharacter());
                morseCodeCache.put(american.getMorseCodeCharacter(), american.getTextCharacter());
            }
        }
    }

    static String getMorseCodeCharacter(char character) {
        return textCache.getOrDefault(character, "");
    }

    static String getTextCharacter(String morseCodeCharacter) {
        return morseCodeCache.getOrDefault(morseCodeCharacter, "");
    }
}
