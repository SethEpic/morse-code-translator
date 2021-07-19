package com.epic.morse.service;

import com.epic.morse.exception.MorseCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.epic.morse.exception.ExceptionMessages.VALIDATION_ERROR_3;
import static org.junit.jupiter.api.Assertions.*;

public class MorseCodeTest {
    private static final String test = "Hello World, my name is Seth. I created this morse code translator!";
    private static final String testMorseCode = ".... . .-.. .-.. ---  .-- --- .-. .-.. -.. --..--  -- -.--  -. .- --" +
        " .  .. ...  ... . - .... .-.-.-  ..  -.-. .-. . .- - . -..  - .... .. ...  -- --- .-. ... .  -.-. --- -.. ." +
        "  - .-. .- -. ... .-.. .- - --- .-. -.-.--";
    private static final String testAmericanMorseCode = ". . . ././⸺/⸺/.  .|. - -/.  ./.  . ./⸺/- . ./. - . -|-" +
        " -/. .  . .|- ./. -/- -/.|. ./. . .|. . ././-/. . . ./. . - - . .|. .|. .  ./.  . ././. -/-/./- . .|-/. . ." +
        " ./. ./. . .|- -/.  ./.  . ./. . ./.|. .  ./.  ./- . ./.|-/.  . ./. -/- ./. . ./⸺/. -/-/.  ./.  . ./- - - .";


    @BeforeEach
    public void init() {
        MorseCodeConfig.getInstance().reset();
    }

    @Test
    public void convertAllLetters() {
        final String ascii_letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String expectedMorseCode = ".- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ..." +
            " - ..- ...- .-- -..- -.-- --.. .- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-." +
            " ... - ..- ...- .-- -..- -.-- --..";
        execute(ascii_letters, expectedMorseCode);
    }

    @Test
    public void convertAllLowerCaseLetters() {
        final String ascii_lowercase = "abcdefghijklmnopqrstuvwxyz";
        final String expectedLettersMorseCode = ".- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --..";
        execute(ascii_lowercase, expectedLettersMorseCode);
    }

    @Test
    public void convertAllUpperCaseLetters() {
        final String ascii_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String expectedLettersMorseCode = ".- -... -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --..";
        execute(ascii_uppercase, expectedLettersMorseCode);
    }

    @Test
    public void convertAllDigits() {
        final String digits = "0123456789";
        final String expectedDigitsMorseCode = "----- .---- ..--- ...-- ....- ..... -.... --... ---.. ----.";
        execute(digits, expectedDigitsMorseCode);
    }

    @Test
    public void convertAllPunctuation() {
        final String punctuation = "!'$&\"()+,-./:;=?@_";
        final String expectedPunctuationMorseCode = "-.-.-- .----. ...-..- .-... .-..-. -.--. -.--.- .-.-. --..-- -....- .-.-.-" +
            " -..-. ---... -.-.-. -...- ..--.. .--.-. ..--.-";
        execute(punctuation, expectedPunctuationMorseCode);
    }

    @Test
    public void convertAllChars() {
        final String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!'$&\"()+,-./:;=?@_";
        final String expectedCharsMorseCode = "----- .---- ..--- ...-- ....- ..... -.... --... ---.. ----. .- -... -.-." +
            " -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --.. .- -..." +
            " -.-. -.. . ..-. --. .... .. .--- -.- .-.. -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --.." +
            " -.-.-- .----. ...-..- .-... .-..-. -.--. -.--.- .-.-. --..-- -....- .-.-.- -..-. ---... -.-.-. -...-" +
            " ..--.. .--.-. ..--.-";
        execute(chars, expectedCharsMorseCode);
    }

    @Test
    public void convert_testStr_defaults() {
        execute(test, testMorseCode);
    }

    @Test
    public void convert_CustomWordSep() {
        MorseCodeConfig.getInstance().setWordSeparator("=");
        final var expectedMorseCode = ".... . .-.. .-.. ---=.-- --- .-. .-.. -.. --..--=-- -.--=-. .- -- .=.. ...=..." +
            " . - .... .-.-.-=..=-.-. .-. . .- - . -..=- .... .. ...=-- --- .-. ... .=-.-. --- -.. .=- .-. .- -. ..." +
            " .-.. .- - --- .-. -.-.--";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_CustomLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        final String expectedMorseCode = "....=.=.-..=.-..=---  .--=---=.-.=.-..=-..=--..--  --=-.--  -.=.-=--=.  " +
            "..=...  ...=.=-=....=.-.-.-  ..  -.-.=.-.=.=.-=-=.=-..  -=....=..=...  --=---=.-.=...=.  -.-.=---=-..=." +
            "  -=.-.=.-=-.=...=.-..=.-=-=---=.-.=-.-.--";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_CustomerLetterAndWordSeps() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        MorseCodeConfig.getInstance().setWordSeparator("/");
        final String expectedMorseCode = "....=.=.-..=.-..=---/.--=---=.-.=.-..=-..=--..--/--=-.--/-.=.-=--=./..=.../" +
            "...=.=-=....=.-.-.-/../-.-.=.-.=.=.-=-=.=-../-=....=..=.../--=---=.-.=...=./-.-.=---=-..=./-=.-.=.-=-.=" +
            "...=.-..=.-=-=---=.-.=-.-.--";
        // Adding a space at the end to test the convert method to remove spaces from start and end
        execute(test, expectedMorseCode, " ");
    }

    @Test
    public void convert_LetterSep2Chars() {
        MorseCodeConfig.getInstance().setLetterSeparator("=_");
        final String expectedMorseCode = "....=_.=_.-..=_.-..=_---_  .--=_---=_.-.=_.-..=_-..=_--..--_  --=_-.--_  " +
            "-.=_.-=_--=_._  ..=_..._  ...=_.=_-=_....=_.-.-.-_  .._  -.-.=_.-.=_.=_.-=_-=_.=_-.._  -=_....=_..=_..._" +
            "  --=_---=_.-.=_...=_._  -.-.=_---=_-..=_._  -=_.-.=_.-=_-.=_...=_.-..=_.-=_-=_---=_.-.=_-.-.--_";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_American_Default() {
        MorseCodeConfig.getInstance().setMorseCodeType(MorseCodeType.AMERICAN);
        execute(test, testAmericanMorseCode);
    }

    @Test
    public void convert_American_CustomLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        MorseCodeConfig.getInstance().setMorseCodeType(MorseCodeType.AMERICAN);
        final String expectedMorseCode = ". . . .=.=⸺=⸺=.  .|. - -=.  .=.  . .=⸺=- . .=. - . -|- -=. .  . .|- .=." +
            " -=- -=.|. .=. . .|. . .=.=-=. . . .=. . - - . .|. .|. .  .=.  . .=.=. -=-=.=- . .|-=. . . .=. .=. . .|- -=." +
            "  .=.  . .=. . .=.|. .  .=.  .=- . .=.|-=.  . .=. -=- .=. . .=⸺=. -=-=.  .=.  . .=- - - .";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_AlphaLetterSep_ShouldFail_AlphaCharsThatAreNotEqToLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("G");
        final String expectedMorseCode = "....G.G.-..G.-..G---  .--G---G.-.G.-..G-..G--..--  --G-.--  -.G.-G--G." +
            "  ..G...  ...G.G-G....G.-.-.-  ..  -.-.G.-.G.G.-G-G.G-..  -G....G..G...  --G---G.-.G...G.  -.-.G---G-..G." +
            "  -G.-.G.-G-.G...G.-..G.-G-G---G.-.G-.-.--";
        final MorseCodeException expectedException = new MorseCodeException(VALIDATION_ERROR_3);
        executeThrows(expectedException, false, true, test, expectedMorseCode, "adsf");
    }

    private void execute(String testValue, String expectedMorseCode) {
        var morseCode = MorseCode.convertToMorseCode(testValue);
        assertNotNull(morseCode, "convertToMorseCode Should NEVER return null");
        assertEquals(expectedMorseCode, morseCode);

        var text = MorseCode.convertToText(morseCode);
        assertNotNull(text, "convertToText Should NEVER return null");
        assertFalse(text.chars().anyMatch(Character::isLowerCase), "convertToText Should return a str of all upper case chars");
        assertEquals(testValue.toUpperCase(), text);
    }

    private void execute(String testValue, String expectedMorseCode, String addToMorseCode) {
        var morseCode = MorseCode.convertToMorseCode(testValue);
        assertNotNull(morseCode, "convertToMorseCode Should NEVER return null");
        assertEquals(expectedMorseCode, morseCode);

        var text = MorseCode.convertToText(addToMorseCode == null ? morseCode : morseCode.concat(addToMorseCode));
        assertNotNull(text, "convertToText Should NEVER return null");
        assertFalse(text.chars().anyMatch(Character::isLowerCase), "convertToText Should return a str of all upper case chars");
        assertEquals(testValue.toUpperCase(), text);
    }

    private void executeThrows(MorseCodeException expectedException, boolean toMorse, boolean toText,
                               String testValue, String expectedMorseCode, String addToMorseCode) {

        if (((!toMorse && !toText) || (toMorse && toText))) {
            fail();
        }
        String morseCode = "";

        if (toMorse) {
            MorseCodeException thrown = assertThrows(MorseCodeException.class, () -> MorseCode.convertToMorseCode(testValue));
            assertEquals(expectedException.getErrorMessage(), thrown.getErrorMessage());
        } else {
            morseCode = MorseCode.convertToMorseCode(testValue);
            assertNotNull(morseCode, "convertToMorseCode Should NEVER return null");
            assertEquals(expectedMorseCode, morseCode);
        }

        if (toText) {
            String finalMorseCode = addToMorseCode == null ? morseCode : morseCode.concat(addToMorseCode);
            MorseCodeException thrown = assertThrows(MorseCodeException.class, () -> MorseCode.convertToText(finalMorseCode));
            assertEquals(expectedException.getErrorMessage(), thrown.getErrorMessage());
        } else {
            final String text = MorseCode.convertToText(morseCode);
            assertNotNull(morseCode, "convertToText Should NEVER return null");
            assertFalse(text.chars().anyMatch(Character::isLowerCase), "convertToText Should return a str of all upper case chars");
            assertEquals(testValue.toUpperCase(), text);
        }
    }
}
