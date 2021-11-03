package com.epic.morse.service;

import com.epic.morse.service.languages.MorseCodeLanguage;
import com.epic.morse.service.languages.MorseCodeLetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class MorseCodeSearcher {
    private static final HashMap<Character, String> textCache = new HashMap<>();
    private static final HashMap<String, String> morseCodeCache = new HashMap<>();
    private static final MorseCodeLetter[] internationalValues = MorseCodeLanguage.International.values();
    private static final MorseCodeLetter[] americanValues = MorseCodeLanguage.American.values();
    private static MorseCodeLetter[] morseCodeLetters = null;
//    TODO use emun map???

    private MorseCodeSearcher() {
    }

    static {
        setLanguageCaches(MorseCodeConfig.getInstance().getMorseCodeType());
    }

    static void setLanguageCaches(MorseCodeType morseCodeType) {
        textCache.clear();
        morseCodeCache.clear();

        if (morseCodeType.isInternational()) {
            morseCodeLetters = internationalValues;
            for (MorseCodeLetter international : internationalValues) {
                textCache.put(international.alphanumeric().charAt(0), international.morseChar());
                morseCodeCache.put(international.morseChar(), international.alphanumeric());
            }
        } else if (morseCodeType.isAmerican()) {
            morseCodeLetters = americanValues;
            for (MorseCodeLetter american : americanValues) {
                textCache.put(american.alphanumeric().charAt(0), american.morseChar());
                morseCodeCache.put(american.morseChar(), american.alphanumeric());
            }
        }
    }

    static String charToMorse(char character) {
        return textCache.getOrDefault(character, "");
    }

    static String morseToChar(String morseChar) {
        return morseCodeCache.getOrDefault(morseChar, "");
    }

    static List<MorseCodeLetter> stringToMorseCodeLetters(String morseCode) {
        final List<MorseCodeLetter> morseCodeLetters = new ArrayList<>();
        var wordSepRegex = RegexUtils.getWordSeparatorRegex();
        var letterSepRegex = RegexUtils.getLetterSeparatorRegex();

        for (String word : wordSepRegex.split(morseCode.trim())) {
            for (String letter : letterSepRegex.split(word)) {
                for (var morseLetter : morseCodeLetters) {
                    if (morseLetter.morseChar().equals(letter)) {
                        morseCodeLetters.add(morseLetter);
                    }
                }
            }
            morseCodeLetters.add(MorseCodeLanguage.International.SPACE);
        }
        morseCodeLetters.remove(morseCodeLetters.size() - 1);
        return morseCodeLetters;
    }
}
