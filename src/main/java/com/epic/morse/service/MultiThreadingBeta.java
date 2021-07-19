package com.epic.morse.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class MultiThreadingBeta {
    private static final StringBuilder SPACE = new StringBuilder(" ");
    static final String testStr = "Hello there, My name is seth and i love air. It's pretty nice to breath it but don't try to drink the air it don't works!";

    private static volatile Map<Integer, String> morseMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
//        var testStr = "Hello World".repeat(1500000);
        var systemStart = System.currentTimeMillis();

        var result = convertToText(convertToMorseCode(testStr));

        var systemEnd = System.currentTimeMillis();
        System.out.println(result);
        System.out.println(systemEnd - systemStart + "ms");
        System.err.println(result.equalsIgnoreCase(testStr));
    }

    private MultiThreadingBeta() {
    }

    public static String convertToMorseCode(String text) throws InterruptedException {
        return convertToMorseCode(split(text, 12));
    }

    public static String convertToText(String morseCode) throws InterruptedException {
        return convertToText(split(morseCode, 12));
    }

    private static List<String[]> parseStringIntoList(String text, String separator) {
        int threadCount = Runtime.getRuntime().availableProcessors();
        String[] words = text.split(separator);
//        if (words.length >= threadCount) {
//            List<String[]> strs = new ArrayList<>();
//            for (int i = 0; i < words.length; i++) {
//                strs.add(Arrays.copyOfRange(words, i, (i + 1));
//            }
//        }
        int half = words.length / 2;
        return new ArrayList<>() {{
            add(Arrays.copyOfRange(words, 0, half));
            add(Arrays.copyOfRange(words, half, words.length));
        }};
    }

    public static String[] split(String str, int len) {
        String[] result = new String[(int) Math.ceil((double) str.length() / (double) len)];
        for (int i = 0; i < result.length; i++)
            result[i] = str.substring(i * len, Math.min(str.length(), (i + 1) * len));
        return result;
    }

    private static String convert(String[] strings, String wordSeparator) {
        final Map<Integer, String> morseMapLo = new HashMap<>();
        final Thread p1;// = new Thread(() -> morseMap.put(1, covertTextToMorseCode(strings.get(0), MorseCodeConfig.getInstance().getWordSeparator(), MorseCodeConfig.getInstance().getLetterSeparator())));
        final Thread p2;// = new Thread(() -> morseMap.put(2, covertTextToMorseCode(strings.get(1), MorseCodeConfig.getInstance().getWordSeparator(), MorseCodeConfig.getInstance().getLetterSeparator())));

        p1 = new Thread(() -> morseMapLo.put(1, convertMorseCodeToText2(strings[0], Utils.getWordSeparatorRegex(), Utils.getLetterSeparatorRegex())));
        p2 = new Thread(() -> morseMapLo.put(2, convertMorseCodeToText2(strings[1], Utils.getWordSeparatorRegex(), Utils.getLetterSeparatorRegex())));

        p1.start();
        p2.start();

        for (Thread thread : new Thread[]{p1, p2}) {
            try {
                thread.join();
            } catch (Exception ignored) {
            }
        }

        return morseMapLo.get(1).concat(wordSeparator).concat(morseMapLo.get(2));
    }

    private static String convertToMorseCode(String[] strings) throws InterruptedException {
        int threadCount = Runtime.getRuntime().availableProcessors() - 1;
        final String wordSep = MorseCodeConfig.getInstance().getWordSeparator();
        final String letterSep = MorseCodeConfig.getInstance().getLetterSeparator();
        ExecutorService es = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int j = i;
            es.execute(() -> morseMap.put(j, covertTextToMorseCode2(strings[j], wordSep, letterSep)));
        }

        es.shutdown();
        boolean finished = es.awaitTermination(1, TimeUnit.DAYS);

        while (!finished) {
            finished = es.awaitTermination(1, TimeUnit.DAYS);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < threadCount; i++) {
            builder.append(morseMap.getOrDefault(i, ""));
        }
        return builder.toString();
    }

    private static String convertToText(String[] strings) throws InterruptedException {
        int threadCount = Runtime.getRuntime().availableProcessors() - 1;
        final Pattern wordSep = Utils.getWordSeparatorRegex();
        final Pattern letterSep = Utils.getWordSeparatorRegex();
        ExecutorService es = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int j = i;
            es.execute(() -> morseMap.put(j, convertMorseCodeToText2(strings[j], wordSep, letterSep)));
        }

        es.shutdown();
        boolean finished = es.awaitTermination(1, TimeUnit.DAYS);

        while (!finished) {
            finished = es.awaitTermination(1, TimeUnit.DAYS);
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < threadCount; i++) {
            builder.append(morseMap.getOrDefault(i, "")).append(" ");
        }
        return builder.deleteCharAt(builder.length() - " ".length()).toString();
    }

    private static String covertTextToMorseCode(String words, String wordSeparator, String letterSeparator) {
        final StringBuilder morseCodeBuilder = new StringBuilder();
        final int letterSeparatorLength = letterSeparator.length();

        for (String word : words.split(" ")) {
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

    private static String covertTextToMorseCode2(String message, String wordSeparator, String letterSeparator) {
        final StringBuilder morseCodeBuilder = new StringBuilder();
        final char[] characters = Utils.multiSpaceRegex.matcher(message.toUpperCase().trim()).replaceAll(" ").toCharArray();
        final int letterSeparatorLength = letterSeparator.length();

        for (char character : characters) {
            if (Character.isWhitespace(character)) {
                morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - letterSeparatorLength).append(wordSeparator);
                continue;
            }

            morseCodeBuilder.append(MorseCodeSearcher.getMorseCodeCharacter(character)).append(letterSeparator);
        }

        return morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - letterSeparatorLength).toString();
    }

    private static String convertMorseCodeToText2(String morseCode, Pattern wordSepRegex, Pattern letterSepRegex) {
        final StringBuilder convertedToText = new StringBuilder();
        final String[] words = wordSepRegex.split(morseCode.trim());

        for (String word : words) {
            String[] letters = letterSepRegex.split(word);
            for (String letter : letters) {
                convertedToText.append(MorseCodeSearcher.getTextCharacter(letter));
            }
            convertedToText.append(SPACE);
        }

        return convertedToText.toString().trim();
    }
}
