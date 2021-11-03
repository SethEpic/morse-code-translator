package com.epic.morse.service;

import com.epic.morse.exception.MorseCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.epic.morse.exception.ExceptionMessages.VALIDATION_ERROR_1;
import static com.epic.morse.exception.ExceptionMessages.VALIDATION_ERROR_3;
import static org.junit.jupiter.api.Assertions.*;

public class AmericanMorseCodeTest {
    private static final String test = "Hello World, my name is Seth. I created this morse code translator!";
    private static final String testMorseCode = ". . . ././⸺/⸺/.  .|. - -/.  ./.  . ./⸺/- . ./. - . -|- -/. .  . .|- ./. -/- -/.|. ./. . .|. . ././-/. . . ./. . - - . .|. .|. .  ./.  . ././. -/-/./- . .|-/. . . ./. ./. . .|- -/.  ./.  . ./. . ./.|. .  ./.  ./- . ./.|-/.  . ./. -/- ./. . ./⸺/. -/-/.  ./.  . ./- - - .";

    @BeforeEach
    public void init() {
        MorseCodeConfig.getInstance().setMorseCodeType(MorseCodeType.AMERICAN);
        MorseCodeConfig.getInstance().resetWordSeparator();
        MorseCodeConfig.getInstance().resetLetterSeparator();
    }

    @Test
    public void convertAllLetters() {
        final String ascii_letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String expectedMorseCode = ". -/- . . ./. .  ./- . ././. - ./- - ./. . . ./. ./- . - ./- . -/⸺/- -/- ./.  ./. . . . ./. . - ./.  . ./. . ./-/. . -/. . . -/. - -/. - . ./. .  . ./. . .  ./. -/- . . ./. .  ./- . ././. - ./- - ./. . . ./. ./- . - ./- . -/⸺/- -/- ./.  ./. . . . ./. . - ./.  . ./. . ./-/. . -/. . . -/. - -/. - . ./. .  . ./. . .  .";
        execute(ascii_letters, expectedMorseCode);
    }

    @Test
    public void convertAllLowerCaseLetters() {
        final String ascii_lowercase = "abcdefghijklmnopqrstuvwxyz";
        final String expectedLettersMorseCode = ". -/- . . ./. .  ./- . ././. - ./- - ./. . . ./. ./- . - ./- . -/⸺/- -/- ./.  ./. . . . ./. . - ./.  . ./. . ./-/. . -/. . . -/. - -/. - . ./. .  . ./. . .  .";
        execute(ascii_lowercase, expectedLettersMorseCode);
    }

    @Test
    public void convertAllUpperCaseLetters() {
        final String ascii_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String expectedLettersMorseCode = ". -/- . . ./. .  ./- . ././. - ./- - ./. . . ./. ./- . - ./- . -/⸺/- -/- ./.  ./. . . . ./. . - ./.  . ./. . ./-/. . -/. . . -/. - -/. - . ./. .  . ./. . .  .";
        execute(ascii_uppercase, expectedLettersMorseCode);
    }

    @Test
    public void convertAllDigits() {
        final String digits = "0123456789";
        final String expectedDigitsMorseCode = "⸻/. - - ./. . - . ./. . . - ./. . . . -/- - -/. . . . . ./- - . ./- . . . ./- . . -";
        execute(digits, expectedDigitsMorseCode);
    }

    @Test
    public void convertAllPunctuation() {
        final String punctuation = "!'$&\"()+,-./:;=?@_";
        final String expectedPunctuationMorseCode = "- - - ./. . - .  . - . ./. . . - . . -/.  . . ./. - . . - ./. . . . .  - ./. . . . .  . .  . ./. - . - ./. - . -/. . . .  . - . ./. . - - . ./. . -  -/- . -  .  ./. . .  . ./- . . . -/- . . - ./. - - . - ./. . - - . -";
        execute(punctuation, expectedPunctuationMorseCode);
    }

    @Test
    public void convertAllChars() {
        final String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!'$&\"()+,-./:;=?@_";
        final String expectedCharsMorseCode = "⸻/. - - ./. . - . ./. . . - ./. . . . -/- - -/. . . . . ./- - . ./- . . . ./- . . -/. -/- . . ./. .  ./- . ././. - ./- - ./. . . ./. ./- . - ./- . -/⸺/- -/- ./.  ./. . . . ./. . - ./.  . ./. . ./-/. . -/. . . -/. - -/. - . ./. .  . ./. . .  ./. -/- . . ./. .  ./- . ././. - ./- - ./. . . ./. ./- . - ./- . -/⸺/- -/- ./.  ./. . . . ./. . - ./.  . ./. . ./-/. . -/. . . -/. - -/. - . ./. .  . ./. . .  ./- - - ./. . - .  . - . ./. . . - . . -/.  . . ./. - . . - ./. . . . .  - ./. . . . .  . .  . ./. - . - ./. - . -/. . . .  . - . ./. . - - . ./. . -  -/- . -  .  ./. . .  . ./- . . . -/- . . - ./. - - . - ./. . - - . -";
        System.out.println(MorseCode.encode(chars));
        execute(chars, expectedCharsMorseCode);
    }

    @Test
    public void convert_TestStr_defaults() {
        execute(test, testMorseCode);
    }

    @Test
    public void convert_CustomWordSep() {
        MorseCodeConfig.getInstance().setWordSeparator("=");
        final var expectedMorseCode = ". . . ././⸺/⸺/.  .=. - -/.  ./.  . ./⸺/- . ./. - . -=- -/. .  . .=- ./. -/- -/.=. ./. . .=. . ././-/. . . ./. . - - . .=. .=. .  ./.  . ././. -/-/./- . .=-/. . . ./. ./. . .=- -/.  ./.  . ./. . ./.=. .  ./.  ./- . ./.=-/.  . ./. -/- ./. . ./⸺/. -/-/.  ./.  . ./- - - .";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_CustomLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        final String expectedMorseCode = ". . . .=.=⸺=⸺=.  .|. - -=.  .=.  . .=⸺=- . .=. - . -|- -=. .  . .|- .=. -=- -=.|. .=. . .|. . .=.=-=. . . .=. . - - . .|. .|. .  .=.  . .=.=. -=-=.=- . .|-=. . . .=. .=. . .|- -=.  .=.  . .=. . .=.|. .  .=.  .=- . .=.|-=.  . .=. -=- .=. . .=⸺=. -=-=.  .=.  . .=- - - .";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_CustomerLetterAndWordSeps() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        MorseCodeConfig.getInstance().setWordSeparator("/");
        final String expectedMorseCode = ". . . .=.=⸺=⸺=.  ./. - -=.  .=.  . .=⸺=- . .=. - . -/- -=. .  . ./" +
            "- .=. -=- -=./. .=. . ./. . .=.=-=. . . .=. . - - . ./. ./. .  .=.  . .=.=. -=-=.=- . ./-=. . . .=. ." +
            "=. . ./- -=.  .=.  . .=. . .=./. .  .=.  .=- . .=./-=.  . .=. -=- .=. . .=⸺=. -=-=.  .=.  . .=- - - .";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_LetterSep2Chars() {
        MorseCodeConfig.getInstance().setLetterSeparator("=_");
        final String expectedMorseCode = ". . . .=_.=_⸺=_⸺=_.  .|. - -=_.  .=_.  . .=_⸺=_- . .=_. - . -|- -=_. .  . ." +
            "|- .=_. -=_- -=_.|. .=_. . .|. . .=_.=_-=_. . . .=_. . - - . .|. .|. .  .=_.  . .=_.=_. -=_-=_.=_- . .|" +
            "-=_. . . .=_. .=_. . .|- -=_.  .=_.  . .=_. . .=_.|. .  .=_.  .=_- . .=_.|-=_.  . .=_. -=_- .=_. . .=_⸺" +
            "=_. -=_-=_.  .=_.  . .=_- - - .";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_American_Default() {
        MorseCodeConfig.getInstance().setMorseCodeType(MorseCodeType.AMERICAN);
        execute(test, testMorseCode);
    }

    @Test
    public void convert_American_CustomLetterSep() {
        MorseCodeConfig.getInstance().setMorseCodeType(MorseCodeType.AMERICAN);
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        final String expectedMorseCode = ". . . .=.=⸺=⸺=.  .|. - -=.  .=.  . .=⸺=- . .=. - . -|- -=. .  . .|- .=." +
            " -=- -=.|. .=. . .|. . .=.=-=. . . .=. . - - . .|. .|. .  .=.  . .=.=. -=-=.=- . .|-=. . . .=. .=. . .|- -=." +
            "  .=.  . .=. . .=.|. .  .=.  .=- . .=.|-=.  . .=. -=- .=. . .=⸺=. -=-=.  .=.  . .=- - - .";
        execute(test, expectedMorseCode);
    }

    @Test
    public void convert_AlphaLetterSep_ShouldFail_AlphaCharsThatAreNotEqToLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("G");
        MorseCodeException thrown = assertThrows(MorseCodeException.class, () -> MorseCode.decode(test.concat("adsf")));
        assertEquals(VALIDATION_ERROR_3, thrown.getErrorMessage());
    }

    @Test
    public void encodeNullStr_ShouldThrowValidationError() {
        final MorseCodeException expectedException = new MorseCodeException(VALIDATION_ERROR_1);
        MorseCodeException thrown = assertThrows(MorseCodeException.class, () -> MorseCode.encode((String) null));
        assertEquals(expectedException.getErrorMessage(), thrown.getErrorMessage());
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
}
