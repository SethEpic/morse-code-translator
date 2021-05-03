package com.epic.morse.service;

import com.epic.morse.config.MorseCodeConfig;

public enum MorseCode {
    A(".-", 'A'),
    B("-...", 'B'),
    C("-.-.", 'C'),
    D("-..", 'D'),
    E(".", 'E'),
    F("..-.", 'F'),
    G("--.", 'G'),
    H("....", 'H'),
    I("..", 'I'),
    J(".---", 'J'),
    K("-.-", 'K'),
    L(".-..", 'L'),
    M("--", 'M'),
    N("-.", 'N'),
    O("---", 'O'),
    P(".--.", 'P'),
    Q("--.-", 'Q'),
    R(".-.", 'R'),
    S("...", 'S'),
    T("-", 'T'),
    U("..-", 'U'),
    V("...-", 'V'),
    W(".--", 'W'),
    X("-..-", 'X'),
    Y("-.--", 'Y'),
    Z("--..", 'Z'),
    _1(".----", '1'),
    _2("..---", '2'),
    _3("...--", '3'),
    _4("....-", '4'),
    _5(".....", '5'),
    _6("-....", '6'),
    _7("--...", '7'),
    _8("---..", '8'),
    _9("----.", '9'),
    _0("-----", '0'),
    SPACE(" ", ' '),
    PERIOD(".-.-.-", '.'),
    EXCLAMATION_POINT("-.-.--", '!'),
    //    INVERTED_EXCLAMATION_POINT("--...-", '¡'),
    COMMA("--..--", ','),
    APOSTROPHE(".----.", '\''),
    SLASH("-..-.", '/'),
    OPEN_PARENTHESIS("-.--.", '('),
    CLOSE_PARENTHESIS("-.--.-", ')'),
    QUESTION_MARK("..--..", '?'),
    //    INVERTED_QUESTION_MARK("..-.-", '¿'),
    AMPERSAND(".-...", '&'),
    COLON("---...", ':'),
    SEMI_COLON("-.-.-.", ';'),
    EQUAL_SIGN("-...-", '='),
    PLUS_SIGN(".-.-.", '+'),
    HYPHEN("-....-", '-'),
    UNDERSCORE("..--.-", '_'),
    QUOTATION_MARK(".-..-.", '"'),
    DOLLAR_SIGN("...-..-", '$'),
    AT_SIGN(".--.-.", '@');

    private final String morseValue;
    private final Character character;

    MorseCode(String morseValue, Character character) {
        this.morseValue = morseValue;
        this.character = character;
    }

    public final String getMorseValue() {
        return morseValue;
    }

    public final Character getCharacter() {
        return character;
    }

    public static String valueOfCharacter(Character character) {
        for (MorseCode morseCode : MorseCode.values()) {
            if (morseCode.getCharacter().equals(Character.toUpperCase(character))) {
                if (SPACE.equals(morseCode)) {
                    return MorseCodeConfig.getInstance().getWordSeparator();
                }

                return morseCode.getMorseValue() + MorseCodeConfig.getInstance().getLetterSeparator();
            }
        }
        return null;
    }

    public static MorseCode valueOfMorseCode(String morseValue) {
        for (MorseCode morseCode : MorseCode.values()) {
            if (morseCode.getMorseValue().equalsIgnoreCase(morseValue)) {
                return morseCode;
            }
        }
        return null;
    }
}
