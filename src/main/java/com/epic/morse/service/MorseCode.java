package com.epic.morse.service;

import com.epic.morse.exception.MorseCodeException;
import com.epic.morse.service.languages.MorseCodeLetter;

import java.util.List;
import java.util.Objects;

public final class MorseCode {
    private final String text;
    private final String morse;
    private final List<MorseCodeLetter> characters;

    public MorseCode(String message) {
        text = message;
        morse = encode(message);
        characters = MorseCodeSearcher.stringToMorseCodeLetters(morse);
    }

    public String text() {
        return text;
    }

    public String morse() {
        return morse;
    }

    public List<MorseCodeLetter> characters() {
        return characters;
    }

    public static MorseCode of(String message) {
        return new MorseCode(message);
    }

    public static MorseCode of(CharSequence message) {
        return new MorseCode(message.toString());
    }

    public static void main(String[] args) {
// System.out.println(encode('a'));
// System.out.println(decode(encode('a')));
        String[] s = {"Hee", "e", "l", "l", "o", " ", "W", "o", "r", "l", "d"};

        var strs = List.of(".... . .-.. .-.. ---  .-- --- .-. .-.. -..  .... --- .--  .- .-. .  -.-- --- ..-", "....");
        String h = "Hello World How are you";
        System.out.println(encode(h));
        System.out.println(decode(strs));
//        var encoded = encode(s);
//        System.out.println(Arrays.toString(encoded));
//        System.out.println(Arrays.toString(decode(encoded)));
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

    public static List<String> encode(List<String> textList) throws MorseCodeException {
        final MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
//        textList.forEach(ValidationService::validateEncode);
        ValidationService.validateEncode(textList);
        return Translator.encodeTextList(textList, morseCodeConfig.getWordSeparator(), morseCodeConfig.getLetterSeparator());
    }

    public static CharSequence encode(CharSequence charSequence) throws MorseCodeException {
//        Optional<CharSequence> morse = Optional.of(encode('1'));
        return encode(charSequence.toString());
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MorseCode morseCode = (MorseCode) o;
        return Objects.equals(text, morseCode.text) && Objects.equals(morse, morseCode.morse) && Objects.equals(characters, morseCode.characters);
    }

    @Override
    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((morse == null) ? 0 : morse.hashCode());
//        result = prime * result + ((text == null) ? 0 : text.hashCode());
//        result = prime * result + ((characters == null) ? 0 : characters.hashCode());
//        return result;
        return Objects.hash(morse, text, characters);
    }

    @Override
    public String toString() {
        return "MorseCode{" +
            "text='" + text + '\'' +
            ", morse='" + morse + '\'' +
            ", characters=" + characters +
            '}';
    }
}
