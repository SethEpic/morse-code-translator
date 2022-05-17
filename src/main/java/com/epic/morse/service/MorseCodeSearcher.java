package com.epic.morse.service;

import com.epic.morse.service.languages.American;
import com.epic.morse.service.languages.International;
import com.epic.morse.service.languages.MorseCodeLetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final class MorseCodeSearcher {
    private static final HashMap<Character, String> textCache = new HashMap<>();
    private static final HashMap<String, String> morseCodeCache = new HashMap<>();
    private static final MorseCodeLetter[] internationalValues = International.values();
    private static final MorseCodeLetter[] americanValues = American.values();

    private MorseCodeSearcher() {
    }

    static {
        setLanguageCaches(MorseCodeConfig.getInstance().getMorseCodeType());
    }

    static void setLanguageCaches(MorseCodeType morseCodeType) {
        textCache.clear();
        morseCodeCache.clear();

        for (MorseCodeLetter morseCodeLetter : morseCodeType.isInternational() ? internationalValues : americanValues) {
            textCache.put(morseCodeLetter.alphanumeric().charAt(0), morseCodeLetter.morseChar());
            morseCodeCache.put(morseCodeLetter.morseChar(), morseCodeLetter.alphanumeric());
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
        final var wordSepRegex = RegexUtils.getWordSeparatorRegex();
        final var letterSepRegex = RegexUtils.getLetterSeparatorRegex();

        for (String word : wordSepRegex.split(morseCode.trim())) {
            for (String letter : letterSepRegex.split(word)) {
                for (var morseLetter : International.values()) {
                    if (morseLetter.morseChar().equals(letter)) {
                        morseCodeLetters.add(morseLetter);
                    }
                }
            }
            morseCodeLetters.add(International.SPACE);
        }
        morseCodeLetters.remove(morseCodeLetters.size() - 1);
        return morseCodeLetters;
    }
}
