package com.epic.morse.service;

import java.util.*;
import java.util.regex.Pattern;

public final class MultiThreadingBeta {
    private static final StringBuilder SPACE = new StringBuilder(" ");

    public static void main(String[] args) {
        var testStr = "Hello World".repeat(1500000);
        var systemStart = System.currentTimeMillis();
        var result = convertToText(convertToMorseCode(testStr));
        var systemEnd = System.currentTimeMillis();

        System.out.println(result);
        System.out.println(systemEnd - systemStart + "ms");
        System.out.println(result.equalsIgnoreCase(testStr));
    }

    private MultiThreadingBeta() {
    }

    public static String convertToMorseCode(String text) {
        return convert(parseStringIntoList(text, " "), MorseCodeConfig.getInstance().getWordSeparator());
    }

    public static String convertToText(String morseCode) {
        return convert(parseStringIntoList(morseCode, MorseCodeConfig.getInstance().getWordSeparator()), " ");
    }

    private static List<String[]> parseStringIntoList(String text, String separator) {
        String[] words = text.split(separator);
        int half = words.length / 2;
        return new ArrayList<>() {{
            add(Arrays.copyOfRange(words, 0, half));
            add(Arrays.copyOfRange(words, half, words.length));
        }};
    }

    private static String convert(List<String[]> strings, String wordSeparator) {
        final Map<Integer, String> morseMap = new HashMap<>();
        final Thread p1;// = new Thread(() -> morseMap.put(1, covertTextToMorseCode(strings.get(0), MorseCodeConfig.getInstance().getWordSeparator(), MorseCodeConfig.getInstance().getLetterSeparator())));
        final Thread p2;// = new Thread(() -> morseMap.put(2, covertTextToMorseCode(strings.get(1), MorseCodeConfig.getInstance().getWordSeparator(), MorseCodeConfig.getInstance().getLetterSeparator())));

        if (wordSeparator.equals(" ")) {
            p1 = new Thread(() -> morseMap.put(1, convertMorseCodeToText(strings.get(0), Utils.getLetterSeparatorRegex())));
            p2 = new Thread(() -> morseMap.put(2, convertMorseCodeToText(strings.get(1), Utils.getLetterSeparatorRegex())));
        } else {
            p1 = new Thread(() -> morseMap.put(1, covertTextToMorseCode(strings.get(0),
                MorseCodeConfig.getInstance().getWordSeparator(), MorseCodeConfig.getInstance().getLetterSeparator())));
            p2 = new Thread(() -> morseMap.put(2, covertTextToMorseCode(strings.get(1),
                MorseCodeConfig.getInstance().getWordSeparator(), MorseCodeConfig.getInstance().getLetterSeparator())));
        }

        p1.start();
        p2.start();

        for (Thread thread : new Thread[]{p1, p2}) {
            try {
                thread.join();
            } catch (Exception ignored) {
            }
        }

        return morseMap.get(1).concat(wordSeparator).concat(morseMap.get(2));
    }

//    public static String convertToText(List<String[]> words) {
//        final Map<Integer, String> morseMap = new HashMap<>();
//        final Thread p1 = new Thread(() -> morseMap.put(1, convertMorseCodeToText(words.get(0), Utils.createLetterSeparatorRegex())));
//        final Thread p2 = new Thread(() -> morseMap.put(2, convertMorseCodeToText(words.get(1), Utils.createLetterSeparatorRegex())));

//        p1.start();
//        p2.start();

//        for (Thread thread : new Thread[]{p1, p2}) {
//            try {
//                thread.join();
//            } catch (Exception ignored) {
//            }
//        }

//        return morseMap.get(1).concat(" ").concat(morseMap.get(2));
//    }

    private static String covertTextToMorseCode(String[] words, String wordSeparator, String letterSeparator) {
        final StringBuilder morseCodeBuilder = new StringBuilder();
        final int letterSeparatorLength = letterSeparator.length();

        for (String word : words) {
            char[] characters = word.toUpperCase().trim().toCharArray();
            for (char character : characters) {
                morseCodeBuilder.append(MorseCodeSearcher.getMorseCodeCharacter(character)).append(letterSeparator);
            }
            morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - letterSeparatorLength).append(wordSeparator);
        }

        return morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - letterSeparatorLength).toString();
    }

    private static String convertMorseCodeToText(String[] morseCodeWords, Pattern letterSepRegex) {
        final StringBuilder convertedToText = new StringBuilder();

        for (String word : morseCodeWords) {
            String[] letters = letterSepRegex.split(word);
            for (String letter : letters) {
                convertedToText.append(MorseCodeSearcher.getTextCharacter(letter));
            }
            convertedToText.append(SPACE);
        }

        return convertedToText.toString().trim();
    }
}
