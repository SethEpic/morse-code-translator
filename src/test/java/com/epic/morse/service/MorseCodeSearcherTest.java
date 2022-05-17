package com.epic.morse.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MorseCodeSearcherTest {
    private static final Map<Character, String> alphabet = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        alphabet.put('A', ".-");
        alphabet.put('B', "-...");
        alphabet.put('C', "-.-.");
        alphabet.put('D', "-..");
        alphabet.put('E', ".");
        alphabet.put('F', "..-.");
        alphabet.put('G', "--.");
        alphabet.put('H', "....");
        alphabet.put('I', "..");
        alphabet.put('J', ".---");
        alphabet.put('K', "-.-");
        alphabet.put('L', ".-..");
        alphabet.put('M', "--");
        alphabet.put('N', "-.");
        alphabet.put('O', "---");
        alphabet.put('P', ".--.");
        alphabet.put('Q', "--.-");
        alphabet.put('R', ".-.");
        alphabet.put('S', "...");
        alphabet.put('T', "-");
        alphabet.put('U', "..-");
        alphabet.put('V', "...-");
        alphabet.put('W', ".--");
        alphabet.put('X', "-..-");
        alphabet.put('Y', "-.--");
        alphabet.put('Z', "--..");
        alphabet.put('0', "-----");
        alphabet.put('1', ".----");
        alphabet.put('2', "..---");
        alphabet.put('3', "...--");
        alphabet.put('4', "....-");
        alphabet.put('5', ".....");
        alphabet.put('6', "-....");
        alphabet.put('7', "--...");
        alphabet.put('8', "---..");
        alphabet.put('9', "----.");
    }

    @BeforeEach
    public void init() {
        MorseCodeConfig.getInstance().reset();
    }

    @Test
    public void testAllSingleLetters() {
        final var logMsg = "Failed to %s '%s' expected(%s) but got this(%s)";

        alphabet.forEach((key, value) -> {
            final var encodedVar = MorseCode.encode(key);
            assertEquals(value, encodedVar, String.format(logMsg, "encode", key, value, encodedVar));

            final var decodedVar = MorseCode.decode(value);
            assertEquals(value, encodedVar, String.format(logMsg, "decode", value, key, decodedVar));
        });
    }
}
