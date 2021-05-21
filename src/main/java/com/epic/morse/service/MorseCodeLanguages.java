package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

import java.util.HashMap;
import java.util.Map;

public final class MorseCodeLanguages {
    private static final Map<String, String> textCache;
    private static final Map<String, String> morseCodeCache;

    static {
        textCache = new HashMap<>();
        morseCodeCache = new HashMap<>();

        if (MorseCodeType.INTERNATIONAL.equals(MorseCodeConfig.getInstance().getMorseCodeType())) {
            for (International international : International.values()) {
                textCache.put(international.getTextCharacter(), international.getMorseCodeCharacter());
                morseCodeCache.put(international.getMorseCodeCharacter(), international.getTextCharacter());
            }
        } else if (MorseCodeType.AMERICAN.equals(MorseCodeConfig.getInstance().getMorseCodeType())) {
            for (American american : American.values()) {
                textCache.put(american.getTextCharacter(), american.getMorseCodeCharacter());
                morseCodeCache.put(american.getMorseCodeCharacter(), american.getTextCharacter());
            }
        } else {

        }
    }

    enum International {
        A(".-", "A"),
        B("-...", "B"),
        C("-.-.", "C"),
        D("-..", "D"),
        E(".", "E"),
        F("..-.", "F"),
        G("--.", "G"),
        H("....", "H"),
        I("..", "I"),
        J(".---", "J"),
        K("-.-", "K"),
        L(".-..", "L"),
        M("--", "M"),
        N("-.", "N"),
        O("---", "O"),
        P(".--.", "P"),
        Q("--.-", "Q"),
        R(".-.", "R"),
        S("...", "S"),
        T("-", "T"),
        U("..-", "U"),
        V("...-", "V"),
        W(".--", "W"),
        X("-..-", "X"),
        Y("-.--", "Y"),
        Z("--..", "Z"),
        _1(".----", "1"),
        _2("..---", "2"),
        _3("...--", "3"),
        _4("....-", "4"),
        _5(".....", "5"),
        _6("-....", "6"),
        _7("--...", "7"),
        _8("---..", "8"),
        _9("----.", "9"),
        _0("-----", "0"),
        SPACE(" ", " "),
        PERIOD(".-.-.-", "."),
        EXCLAMATION_POINT("-.-.--", "!"),
        COMMA("--..--", ","),
        APOSTROPHE(".----.", "'"),
        SLASH("-..-.", "/"),
        OPEN_PARENTHESIS("-.--.", "("),
        CLOSE_PARENTHESIS("-.--.-", ")"),
        QUESTION_MARK("..--..", "?"),
        AMPERSAND(".-...", "&"),
        COLON("---...", ":"),
        SEMI_COLON("-.-.-.", ";"),
        EQUAL_SIGN("-...-", "="),
        PLUS_SIGN(".-.-.", "+"),
        HYPHEN("-....-", "-"),
        UNDERSCORE("..--.-", "_"),
        QUOTATION_MARK(".-..-.", "\""),
        DOLLAR_SIGN("...-..-", "$"),
        AT_SIGN(".--.-.", "@");

        private final String morseCodeCharacter;
        private final String textCharacter;

        International(String morseCodeCharacter, String textCharacter) {
            this.morseCodeCharacter = morseCodeCharacter;
            this.textCharacter = textCharacter;
        }

        public final String getMorseCodeCharacter() {
            return morseCodeCharacter;
        }

        public final String getTextCharacter() {
            return textCharacter;
        }
    }

    enum American {
        A(".-", "A"),
        B("-...", "B"),
        C("-.-.", "C"),
        D("-..", "D"),
        E(".", "E"),
        F("..-.", "F"),
        G("--.", "G"),
        H("....", "H"),
        I("..", "I"),
        J(".---", "J"),
        K("-.-", "K"),
        L(".-..", "L"),
        M("--", "M"),
        N("-.", "N"),
        O("---", "O"),
        P(".--.", "P"),
        Q("--.-", "Q"),
        R(".-.", "R"),
        S("...", "S"),
        T("-", "T"),
        U("..-", "U"),
        V("...-", "V"),
        W(".--", "W"),
        X("-..-", "X"),
        Y("-.--", "Y"),
        Z("--..", "Z"),
        _1(".----", "1"),
        _2("..---", "2"),
        _3("...--", "3"),
        _4("....-", "4"),
        _5(".....", "5"),
        _6("-....", "6"),
        _7("--...", "7"),
        _8("---..", "8"),
        _9("----.", "9"),
        _0("-----", "0"),
        SPACE(" ", " "),
        PERIOD(".-.-.-", "."),
        EXCLAMATION_POINT("-.-.--", "!"),
        COMMA("--..--", ","),
        APOSTROPHE(".----.", "'"),
        SLASH("-..-.", "/"),
        OPEN_PARENTHESIS("-.--.", "("),
        CLOSE_PARENTHESIS("-.--.-", ")"),
        QUESTION_MARK("..--..", "?"),
        AMPERSAND(".-...", "&"),
        COLON("---...", ":"),
        SEMI_COLON("-.-.-.", ";"),
        EQUAL_SIGN("-...-", "="),
        PLUS_SIGN(".-.-.", "+"),
        HYPHEN("-....-", "-"),
        UNDERSCORE("..--.-", "_"),
        QUOTATION_MARK(".-..-.", "\""),
        DOLLAR_SIGN("...-..-", "$"),
        AT_SIGN(".--.-.", "@");

        private final String morseCodeCharacter;
        private final String textCharacter;

        American(String morseCodeCharacter, String textCharacter) {
            this.morseCodeCharacter = morseCodeCharacter;
            this.textCharacter = textCharacter;
        }

        public final String getMorseCodeCharacter() {
            return morseCodeCharacter;
        }

        public final String getTextCharacter() {
            return textCharacter;
        }
    }

    public static String getMorseCodeCharacter(MorseCodeType type, String character) {
        if (MorseCodeType.INTERNATIONAL.equals(type) && textCache.containsKey(character)) {
            return textCache.get(character);
        }

        if (MorseCodeType.AMERICAN.equals(type) && textCache.containsKey(character)) {
            return textCache.get(character);
        }

        return "";
    }

    public static String getTextCharacter(MorseCodeType type, String morseCodeCharacter) {
        if (MorseCodeType.INTERNATIONAL.equals(type) && morseCodeCache.containsKey(morseCodeCharacter)) {
            return morseCodeCache.get(morseCodeCharacter);
        }

        if (MorseCodeType.AMERICAN.equals(type) && morseCodeCache.containsKey(morseCodeCharacter)) {
            return morseCodeCache.get(morseCodeCharacter);
        }

        return "";
    }
}
