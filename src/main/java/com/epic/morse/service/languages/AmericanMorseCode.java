package com.epic.morse.service.languages;

public enum AmericanMorseCode implements MorseCodeLanguage {
    A(". -", "A"),
    B("- . . .", "B"),
    C(". .  .", "C"),
    D("- . .", "D"),
    E(".", "E"),
    F(". - .", "F"),
    G("- - .", "G"),
    H(". . . .", "H"),
    I(". .", "I"),
    J("- . - .", "J"),
    K("- . -", "K"),
    L("⸺", "L"),
    M("- -", "M"),
    N("- .", "N"),
    O(".  .", "O"),
    P(". . . . .", "P"),
    Q(". . - .", "Q"),
    R(".  . .", "R"),
    S(". . .", "S"),
    T("-", "T"),
    U(". . -", "U"),
    V(". . . -", "V"),
    W(". - -", "W"),
    X(". - . . ", "X"),
    Y(". .  . .", "Y"),
    Z(". . .  .", "Z"),
    _1(". - - .", "1"),
    _2(". . - . .", "2"),
    _3(". . . - .", "3"),
    _4(". . . . -", "4"),
    _5("- - -", "5"),
    _6(". . . . . .", "6"),
    _7("- - . .", "7"),
    _8("- . . . .", "8"),
    _9("- . . -", "9"),
    _0("⸻", "0"),
    SPACE(" ", " "),
    PERIOD(". . - - . .", "."),
    EXCLAMATION_POINT("- - - .", "!"),
    COMMA(". - . -", ","),
    APOSTROPHE(". . - .  . - . .", "'"),
    SLASH(". . -  -", "/"),
    OPEN_PARENTHESIS(". . . . .  - .", "("),
    CLOSE_PARENTHESIS(". . . . .  . .  . .", ")"),
    QUESTION_MARK("- . . - .", "?"),
    AMPERSAND(".  . . .", "&"),
    COLON("- . -  .  .", ":"),
    SEMI_COLON(". . .  . .", ";"),
    EQUAL_SIGN("", "="),
    PLUS_SIGN("", "+"),
    HYPHEN(". . . .  . - . .", "-"),
    UNDERSCORE("", "_"),
    QUOTATION_MARK(". - . . - .", "\""),
    DOLLAR_SIGN("", "$"),
    AT_SIGN("", "@");

    private final String morseCodeCharacter;
    private final String textCharacter;

    AmericanMorseCode(String morseCodeCharacter, String textCharacter) {
        this.morseCodeCharacter = morseCodeCharacter;
        this.textCharacter = textCharacter;
    }

    @Override
    public final String getMorseCodeCharacter() {
        return morseCodeCharacter;
    }

    @Override
    public final String getTextCharacter() {
        return textCharacter;
    }
}
