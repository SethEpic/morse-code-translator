package com.epic.morse.service.languages;

public final class MorseCodeLanguage {

    public enum International implements MorseCodeLetter {
        A("A", ".-"),
        B("B", "-..."),
        C("C", "-.-."),
        D("D", "-.."),
        E("E", "."),
        F("F", "..-."),
        G("G", "--."),
        H("H", "...."),
        I("I", ".."),
        J("J", ".---"),
        K("K", "-.-"),
        L("L", ".-.."),
        M("M", "--"),
        N("N", "-."),
        O("O", "---"),
        P("P", ".--."),
        Q("Q", "--.-"),
        R("R", ".-."),
        S("S", "..."),
        T("T", "-"),
        U("U", "..-"),
        V("V", "...-"),
        W("W", ".--"),
        X("X", "-..-"),
        Y("Y", "-.--"),
        Z("Z", "--.."),
        _1("1", ".----"),
        _2("2", "..---"),
        _3("3", "...--"),
        _4("4", "....-"),
        _5("5", "....."),
        _6("6", "-...."),
        _7("7", "--..."),
        _8("8", "---.."),
        _9("9", "----."),
        _0("0", "-----"),
        SPACE(" ", " "),
        PERIOD(".", ".-.-.-"),
        EXCLAMATION_POINT("!", "-.-.--"),
        COMMA(",", "--..--"),
        APOSTROPHE("'", ".----."),
        SLASH("/", "-..-."),
        OPEN_PARENTHESIS("(", "-.--."),
        CLOSE_PARENTHESIS(")", "-.--.-"),
        QUESTION_MARK("?", "..--.."),
        AMPERSAND("&", ".-..."),
        COLON(":", "---..."),
        SEMI_COLON(";", "-.-.-."),
        EQUAL_SIGN("=", "-...-"),
        PLUS_SIGN("+", ".-.-."),
        HYPHEN("-", "-....-"),
        UNDERSCORE("_", "..--.-"),
        QUOTATION_MARK("\"", ".-..-."),
        DOLLAR_SIGN("$", "...-..-"),
        AT_SIGN("@", ".--.-."),
        NONE(" ", "");

        private final String alphanumeric;
        private final String morseChar;

        International(String alphanumeric, String morseChar) {
            this.alphanumeric = alphanumeric;
            this.morseChar = morseChar;
        }

        @Override
        public String alphanumeric() {
            return alphanumeric;
        }

        @Override
        public String morseChar() {
            return morseChar;
        }

        @Override
        public String language() {
            return "International Morse Code";
        }

        @Override
        public String toString() {
            return "{" +
                "alphanumeric='" + alphanumeric + '\'' +
                ", morseChar='" + morseChar + '\'' +
                '}';
        }
    }

    public enum American implements MorseCodeLetter {
        A("A", ". -"),
        B("B", "- . . ."),
        C("C", ". .  ."),
        D("D", "- . ."),
        E("E", "."),
        F("F", ". - ."),
        G("G", "- - ."),
        H("H", ". . . ."),
        I("I", ". ."),
        J("J", "- . - ."),
        K("K", "- . -"),
        L("L", "⸺"),
        M("M", "- -"),
        N("N", "- ."),
        O("O", ".  ."),
        P("P", ". . . . ."),
        Q("Q", ". . - ."),
        R("R", ".  . ."),
        S("S", ". . ."),
        T("T", "-"),
        U("U", ". . -"),
        V("V", ". . . -"),
        W("W", ". - -"),
        X("X", ". - . ."),
        Y("Y", ". .  . ."),
        Z("Z", ". . .  ."),
        _1("1", ". - - ."),
        _2("2", ". . - . ."),
        _3("3", ". . . - ."),
        _4("4", ". . . . -"),
        _5("5", "- - -"),
        _6("6", ". . . . . ."),
        _7("7", "- - . ."),
        _8("8", "- . . . ."),
        _9("9", "- . . -"),
        _0("0", "⸻"),
        SPACE(" ", " "),
        PERIOD(".", ". . - - . ."),
        EXCLAMATION_POINT("!", "- - - ."),
        COMMA(",", ". - . -"),
        APOSTROPHE("'", ". . - .  . - . ."),
        SLASH("/", ". . -  -"),
        OPEN_PARENTHESIS("(", ". . . . .  - ."),
        CLOSE_PARENTHESIS(")", ". . . . .  . .  . ."),
        QUESTION_MARK("?", "- . . - ."),
        AMPERSAND("&", ".  . . ."),
        COLON(":", "- . -  .  ."),
        SEMI_COLON(";", ". . .  . ."),
        //TODO: CHECK UNDERSCORE, PLUS_SIGN, EQUAL_SIGN,DOLLAR_SIGN and AT_SIGN it may not be correct copied from INTERNATIONAL
        EQUAL_SIGN("=", "- . . . -"),
        PLUS_SIGN("+", ". - . - ."),
        HYPHEN("-", ". . . .  . - . ."),
        UNDERSCORE("_", ". . - - . -"),
        QUOTATION_MARK("\"", ". - . . - ."),
        DOLLAR_SIGN("$", ". . . - . . -"),
        AT_SIGN("@", ". - - . - .");

        private final String alphanumeric;
        private final String morseChar;

        American(String alphanumeric, String morseChar) {
            this.alphanumeric = alphanumeric;
            this.morseChar = morseChar;
        }

        @Override
        public String alphanumeric() {
            return alphanumeric;
        }

        @Override
        public String morseChar() {
            return morseChar;
        }

        @Override
        public String language() {
            return "American Morse Code";
        }

        @Override
        public String toString() {
            return "{" +
                "alphanumeric='" + alphanumeric + '\'' +
                ", morseChar='" + morseChar + '\'' +
                '}';
        }
    }
}
