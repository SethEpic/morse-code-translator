package com.epic.morse.service;

import com.epic.morse.exception.MorseCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.epic.morse.exception.ExceptionMessages.VALIDATION_ERROR_1;
import static com.epic.morse.exception.ExceptionMessages.VALIDATION_ERROR_3;
import static org.junit.jupiter.api.Assertions.*;

public class MorseCodeTest {
    private static final String test = "Hello World, my name is Seth. I created this morse code translator!";
    private static final String[] testArray = new String[]{"Hello World", "Name is Seth.", "I am very epic", "And you are not", "Ha", "Do", "not", "be", "Upset"};
//    private static final String[] expectedEncodedArray = new String[]{
//        ".... . .-.. .-.. ---  .-- --- .-. .-.. -.., -. .- -- .  .. ...  ... . - .... .-.-.-",
//        "..  .- --  ...- . .-. -.--  . .--. .. -.-.", ".- -. -..  -.-- --- ..-  .- .-. .  -. --- -",
//        ".... .-", "-.. ---", "-. --- -", "-... .", "..- .--. ... . -",
//    };

    private static final String[] expectedEncodedArray = {".... . .-.. .-.. ---  .-- --- .-. .-.. -..",
        "-. .- -- .  .. ...  ... . - .... .-.-.-", "..  .- --  ...- . .-. -.--  . .--. .. -.-.",
        ".- -. -..  -.-- --- ..-  .- .-. .  -. --- -", ".... .-", "-.. ---", "-. --- -", "-... .", "..- .--. ... . -"
    };

    private static final String testMorseCode = ".... . .-.. .-.. ---  .-- --- .-. .-.. -.. --..--  -- -.--  -. .- --" +
        " .  .. ...  ... . - .... .-.-.-  ..  -.-. .-. . .- - . -..  - .... .. ...  -- --- .-. ... .  -.-. --- -.. ." +
        "  - .-. .- -. ... .-.. .- - --- .-. -.-.--";

    @BeforeEach
    public void init() {
        MorseCodeConfig.getInstance().reset();
    }

    @Test
    public void testy() {
        var character = "[";
        final var expectedTest = test.toUpperCase();
        MorseCodeConfig.getInstance().setLetterSeparator(character);

        var customLetterSepMorseCode = MorseCode.encode(test);
        assertEquals(expectedTest, MorseCode.decode(customLetterSepMorseCode));

        MorseCodeConfig.getInstance().reset();
        MorseCodeConfig.getInstance().setWordSeparator(character);

        var customWordSepMorseCode = MorseCode.encode(test);
        assertEquals(expectedTest, MorseCode.decode(customWordSepMorseCode));
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
    public void convert_TestStr_defaults() {
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
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_LetterSep2Chars() {
        MorseCodeConfig.getInstance().setLetterSeparator("=_");
        final String expectedMorseCode = "....=_.=_.-..=_.-..=_---  .--=_---=_.-.=_.-..=_-..=_--..--  --=_-.--  -.=_.-=_--=_.  ..=_...  ...=_.=_-=_....=_.-.-.-  ..  -.-.=_.-.=_.=_.-=_-=_.=_-..  -=_....=_..=_...  --=_---=_.-.=_...=_.  -.-.=_---=_-..=_.  -=_.-.=_.-=_-.=_...=_.-..=_.-=_-=_---=_.-.=_-.-.--";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_AlphaLetterSep_ShouldFail_AlphaCharsThatAreNotEqToLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("G");
        final MorseCodeException expectedException = new MorseCodeException(VALIDATION_ERROR_3);
        MorseCodeException thrown = assertThrows(MorseCodeException.class, () -> MorseCode.decode(test.concat("adsf")));
        assertEquals(expectedException.getErrorMessage(), thrown.getErrorMessage());
    }

    @Test
    public void encodeNullStr_ShouldThrowValidationError() {
        final MorseCodeException expectedException = new MorseCodeException(VALIDATION_ERROR_1);
        MorseCodeException thrown = assertThrows(MorseCodeException.class, () -> MorseCode.encode((String) null));
        assertEquals(expectedException.getErrorMessage(), thrown.getErrorMessage());
    }

    @Test
    public void encodeCharArray_Defaults() {
        final char[] characters = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};
        final String[] expected = {"....", ".", ".-..", ".-..", "---", "", ".--", "---", ".-.", ".-..", "-.."};
        final String[] morseCode = MorseCode.encode(characters);
        final String[] decoded = MorseCode.decode(expected);

        assertArrayEquals(expected, morseCode);
        assertDecodeArrayEquals(decoded, characters);
    }

    @Test
    public void encodeStringArray_Defaults() {
        final String[] morseCode = MorseCode.encode(testArray);
        final String[] decoded = MorseCode.decode(morseCode);
        System.out.println(Arrays.toString(morseCode));
        assertArrayEquals(expectedEncodedArray, morseCode);
        assertDecodeArrayEquals(decoded);
    }

    private void execute(String testValue, String expectedMorseCode) {
        var morseCode = MorseCode.encode(testValue);
        assertNotNull(morseCode, "convertToMorseCode Should NEVER return null");
        assertEquals(expectedMorseCode, morseCode);

        var text = MorseCode.decode(morseCode);
        assertNotNull(text, "convertToText Should NEVER return null");
        assertFalse(text.chars().anyMatch(Character::isLowerCase), "convertToText Should return a str of all upper case chars");
        assertEquals(testValue.toUpperCase(), text);
    }

    private void assertDecodeArrayEquals(String[] actual, char[] expected) {
        for (int i = 0; i < actual.length; i++) {
            if (Character.toUpperCase(expected[i]) != actual[i].charAt(0)) {
                fail("Decode array is not equal!\nchar: " + expected[i] + "\nString: " + actual[i]);
            }
        }
    }

    private void assertDecodeArrayEquals(String[] actual) {
        for (int i = 0; i < actual.length; i++) {
            if (!testArray[i].equalsIgnoreCase(actual[i])) {
                fail("Decode array is not equal!\nstring: " + testArray[i] + "\nString: " + actual[i]);
            }
        }
    }
}
