package com.epic.morse.service;

import com.epic.morse.exception.MorseCodeException;
import com.epic.morse.service.languages.MorseCodeLetter;

import java.util.*;
import java.util.stream.Collectors;

public final class MorseCode {
    private final String text;
    private final String morse;
    private final List<MorseCodeLetter> characters;

    public MorseCode(String message) {
        ValidationService.validateEncode(message);
        text = message.toUpperCase();
        morse = encode(message);
        characters = MorseCodeSearcher.stringToMorseCodeLetters(morse);
    }

//    public static void main(String[] args) throws Exception {
//        new MorseCode("Hello World");
//        System.out.println("===========");
//        Field field = Unsafe.class.getDeclaredField("theUnsafe");
//        field.setAccessible(true);
//        Unsafe unsafe = (Unsafe) field.get(null);
//        unsafe.setMemory(4000L, 15L, (byte) 1);
//        System.out.println(encode('A'));
//        System.out.println(decode(encode('A')));
//       String[] s = {"Hee", "e", "l", "l", "o", " ", "W", "o", "r", "l", "d"};
//       System.out.println(Arrays.toString(decode(encode(s))));
//    }

    public String text() {
        return text;
    }

    public String morse() {
        return morse;
    }

    public List<MorseCodeLetter> characters() {
        return characters;
    }

    public String decode() {
        return text;
    }

    public static MorseCode of(String message) {
        return new MorseCode(message);
    }

    public static MorseCode of(CharSequence message) {
        return new MorseCode(message.toString());
    }

    /**
     * @deprecated Use MorseCode.encode(); For removal in 1.07
     */
    @Deprecated(since = "1.0.6", forRemoval = true)
    public static String convertToMorseCode(String message) {
        ValidationService.validateEncode(message);
        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        return Translator.encodeText(message, morseCodeConfig.getWordSeparator(), morseCodeConfig.getLetterSeparator());
    }

    /**
     * @deprecated Use MorseCode.decode(); For removal in 1.07
     */
    @Deprecated(since = "1.0.6", forRemoval = true)
    public static String convertToText(String morseCode) {
        ValidationService.validateMorseCodeToText(morseCode);
        return Translator.decodeMorseCode(morseCode, RegexUtils.getWordSeparatorRegex(), RegexUtils.getLetterSeparatorRegex());
    }

    /**
     * @throws com.epic.morse.exception.MorseCodeException - if input fails validation i.e. is null or empty
     * @see com.epic.morse.exception.ExceptionMessages - All error messages
     */
    public static String encode(String text) throws MorseCodeException {
        ValidationService.validateEncode(text);
        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        return Translator.encodeText(text, morseCodeConfig.getWordSeparator(), morseCodeConfig.getLetterSeparator());
    }

    public static String[] encode(String[] text) throws MorseCodeException {
        final MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        ValidationService.validateEncode(text);
        return Translator.encodeTextArray(text, morseCodeConfig.getWordSeparator(), morseCodeConfig.getLetterSeparator());
    }

    public static String encode(char character) throws MorseCodeException {
        ValidationService.validateEncode(character);
        return MorseCodeSearcher.charToMorse(Character.toUpperCase(character));
    }

    public static String[] encode(char[] characters) throws MorseCodeException {
        ValidationService.validateEncode(characters);
        return Translator.encodeCharArray(characters);
    }

    public static String encode(Number number) {
        final var text = number.toString();
        final MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        return Translator.encodeText(text, morseCodeConfig.getWordSeparator(), morseCodeConfig.getLetterSeparator());
    }

    public static List<String> encode(List<String> textList) throws MorseCodeException {
        final MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        ValidationService.validateEncode(textList);
        return Translator.encodeTextList(textList, morseCodeConfig.getWordSeparator(), morseCodeConfig.getLetterSeparator());
    }

    public static Set<String> encode(Set<String> textSet) throws MorseCodeException {
        return textSet.stream().map(MorseCode::encode).collect(Collectors.toSet());
    }

//    public static Set<String> encode(Map<Object, String> textSet) throws MorseCodeException {
  //      return null;
    //}

    public static CharSequence encode(CharSequence charSequence) throws MorseCodeException {
        return encode(charSequence.toString());
    }

    public static String[] encode(CharSequence[] charSequences) throws MorseCodeException {
        return Arrays.stream(charSequences).map(MorseCode::encode).map(CharSequence::toString).toArray(String[]::new);
    }

    /**
     * @throws com.epic.morse.exception.MorseCodeException - if inputed morse code is null, empty, or contains any alpha or numeric chars
     * @see com.epic.morse.exception.ExceptionMessages - All error messages
     */
    public static String decode(String morseCode) throws MorseCodeException {
        ValidationService.validateMorseCodeToText(morseCode);
        return Translator.decodeMorseCode(morseCode, RegexUtils.getWordSeparatorRegex(), RegexUtils.getLetterSeparatorRegex());
    }

    public static String[] decode(String[] morseCode) throws MorseCodeException {
        /*TODO: Validate*/
        return Translator.decodeMorseCode(morseCode, RegexUtils.getWordSeparatorRegex(), RegexUtils.getLetterSeparatorRegex());
    }

    public static List<String> decode(List<String> morseCode) throws MorseCodeException {
        /*TODO: Validate*/
        return Translator.decodeMorseCodeList(morseCode, RegexUtils.getWordSeparatorRegex(), RegexUtils.getLetterSeparatorRegex());
    }

    public static String decode(CharSequence morseCode) throws MorseCodeException {
        /*TODO: Validate*/
        return decode(morseCode.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(morse, text, characters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MorseCode morseCode = (MorseCode) o;
        return Objects.equals(text, morseCode.text) && Objects.equals(morse, morseCode.morse) && Objects.equals(characters, morseCode.characters);
    }

    @Override
    public String toString() {
        return "MorseCode: {" +
            "\n  text: '" + text +
            "',\n  morse: '" + morse +
            "',\n  characters: " + characters +
            "\n}";
    }
}
