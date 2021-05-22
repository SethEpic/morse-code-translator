package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

import java.util.HashMap;
import java.util.Map;

public final class MorseCode {
    private static final Map<String, String> textCache = new HashMap<>();
    private static final Map<String, String> morseCodeCache = new HashMap<>();
    private static final International[] internationalValues = International.values();
    private static final American[] americanValues = American.values();

    private MorseCode() {
    }

    static {
        setLanguageCaches(MorseCodeConfig.getInstance().getMorseCodeType());
    }

    public static void setLanguageCaches(MorseCodeType morseCodeType) {
        textCache.clear();
        morseCodeCache.clear();

        if (MorseCodeType.INTERNATIONAL.equals(morseCodeType)) {
            for (International international : internationalValues) {
                textCache.put(international.getTextCharacter(), international.getMorseCodeCharacter());
                morseCodeCache.put(international.getMorseCodeCharacter(), international.getTextCharacter());
            }
        } else if (MorseCodeType.AMERICAN.equals(morseCodeType)) {
            for (American american : americanValues) {
                textCache.put(american.getTextCharacter(), american.getMorseCodeCharacter());
                morseCodeCache.put(american.getMorseCodeCharacter(), american.getTextCharacter());
            }
        }
    }

    public static String getMorseCodeCharacter(String character) {
        return textCache.getOrDefault(character, "");
    }

    public static String getTextCharacter(String morseCodeCharacter) {
        return morseCodeCache.getOrDefault(morseCodeCharacter, "");
    }

    public enum International {
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

    public enum American {
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
}
