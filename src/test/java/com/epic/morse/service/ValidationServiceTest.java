package com.epic.morse.service;

import com.epic.morse.exception.MorseCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.epic.morse.exception.ExceptionMessages.*;
import static com.epic.morse.exception.ExceptionMessages.INVALID_WORD_SEPARATOR_1;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {
    final String validText = "Hello World";

    @BeforeEach
    public void init() {
        MorseCodeConfig.getInstance().reset();
    }

    @Test
    public void textValidation_ShouldPassValidText() {
        assertDoesNotThrow(() -> ValidationService.validateEncode(validText));
    }

    @Test
    public void textValidation_ShouldFailWithNullText() {
       MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateEncode((String) null));
       assertEquals(VALIDATION_ERROR_1, result.getErrorMessage());
    }

    @Test
    public void textValidation_ShouldFailWithEmptyText() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateEncode(""));
        assertEquals(VALIDATION_ERROR_2, result.getErrorMessage());
    }

    @Test
    public void morseCodeValidation_ShouldFailWithNullText() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateMorseCodeToText(null));
        assertEquals(VALIDATION_ERROR_1, result.getErrorMessage());
    }

    @Test
    public void morseCodeValidation_ShouldFailWithEmptyText() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateMorseCodeToText(""));
        assertEquals(VALIDATION_ERROR_2, result.getErrorMessage());
    }

    @Test
    public void morseCodeValidation_ShouldFailWithAlphaNumericChars() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateMorseCodeToText("... ---5..."));
        assertEquals(VALIDATION_ERROR_3, result.getErrorMessage());
    }

    @Test
    public void letterSeparatorValidation_ShouldFailWithNullStr() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateLetterSeparator(null));
        assertEquals(INVALID_LETTER_SEPARATOR_1, result.getErrorMessage());
    }

    @Test
    public void letterSeparatorValidation_ShouldFailWithEmptyStr() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateLetterSeparator(""));
        assertEquals(INVALID_LETTER_SEPARATOR_2, result.getErrorMessage());
    }

    @Test
    public void letterSeparatorValidation_ShouldFailWhenMatchesWordSeparator() {
        MorseCodeConfig.getInstance().setWordSeparator("+");
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateLetterSeparator("+"));
        assertEquals(INVALID_LETTER_SEPARATOR_3, result.getErrorMessage());
    }

    @Test
    public void letterSeparatorValidation_ShouldFail_Dash() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateLetterSeparator("-"));
        assertEquals(INVALID_LETTER_SEPARATOR_4, result.getErrorMessage());
    }

    @Test
    public void letterSeparatorValidation_ShouldFail_Dot() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateLetterSeparator("."));
        assertEquals(INVALID_LETTER_SEPARATOR_5, result.getErrorMessage());
    }

    @Test
    public void wordSeparatorValidation_ShouldFailWithNullStr() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateWordSeparator(null));
        assertEquals(INVALID_WORD_SEPARATOR_1, result.getErrorMessage());
    }

    @Test
    public void wordSeparatorValidation_ShouldFailWithEmptyStr() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateWordSeparator(""));
        assertEquals(INVALID_WORD_SEPARATOR_2, result.getErrorMessage());
    }

    @Test
    public void wordSeparatorValidation_ShouldFailWhenMatchesWordSeparator() {
        MorseCodeConfig.getInstance().setLetterSeparator("+");
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateWordSeparator("+"));
        assertEquals(INVALID_WORD_SEPARATOR_3, result.getErrorMessage());
    }

    @Test
    public void wordSeparatorValidation_ShouldFail_Dash() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateWordSeparator("-"));
        assertEquals(INVALID_WORD_SEPARATOR_4, result.getErrorMessage());
    }

    @Test
    public void wordSeparatorValidation_ShouldFail_Dot() {
        MorseCodeException result = assertThrows(MorseCodeException.class, () -> ValidationService.validateWordSeparator("."));
        assertEquals(INVALID_WORD_SEPARATOR_5, result.getErrorMessage());
    }

}
