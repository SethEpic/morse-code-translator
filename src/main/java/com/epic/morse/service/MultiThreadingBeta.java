//package com.epic.morse.service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//public final class MultiThreadingBeta {
//    private static final StringBuilder SPACE = new StringBuilder(" ");
////    static final String testStr = "Hello there, My name is seth and i love air. It's pretty nice to breath it but don't try to drink the air it don't works!";
//
//    private static volatile Map<Integer, String> morseMap = new ConcurrentHashMap<>();
//
//    public static void main(String[] args) {
//        var testStr = "Hello World".repeat(1500);
//        for (int i = 0; i < 5; i++) {
//            var systemStart = System.currentTimeMillis();
//            for (int j = 0; j < 1000; j++) {
//                convertToText(convertToMorseCode(testStr));
//            }
//            var systemEnd = System.currentTimeMillis();
//            System.out.println(systemEnd - systemStart + "ms");
//        }
//    }
//
//    static Pattern pattern = Pattern.compile(" ");
//    static Pattern pattern2 = Pattern.compile(MorseCodeConfig.getInstance().getWordSeparator());
//
//    private MultiThreadingBeta() {
//    }
//
//    public static String convertToMorseCode(String text) {
//        var s = pattern.split(text);
//        var sep = MorseCodeConfig.getInstance().getWordSeparator();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < s.length; i++) {
//            sb.append(MorseCode.encode(s[i])).append(sep);
//        }
//        sb.setLength(sb.length() - sep.length());
//        return sb.toString();
////        return pattern.splitAsStream(text).parallel().map(MorseCode::encode).collect(Collectors.joining(MorseCodeConfig.getInstance().getWordSeparator()));
////        return pattern.splitAsStream(text).map(MorseCode::encode).collect(Collectors.joining(MorseCodeConfig.getInstance().getWordSeparator()));
////        return convertToMorseCode(split(text, " "));
//    }
//
//    public static String convertToText(String morseCode) {
//        return pattern2.splitAsStream(morseCode).parallel().map(MorseCode::decode).collect(Collectors.joining(" "));
////        return pattern2.splitAsStream(morseCode).map(MorseCode::decode).collect(Collectors.joining(" "));
////        return convertToText(splitMorseCode(morseCode));
//    }
//
//    public static String[] split(String str, String delimiter) {
//        String[] splitMessage = str.split(delimiter);
//        System.out.println("SPLIT: " + splitMessage.length);
////        long chunk = 2; // chunk size to divide
//        var threads = Runtime.getRuntime().availableProcessors();
//        var chunk = roundUp(Long.parseLong(String.valueOf(splitMessage.length)), threads);
//        System.out.println(chunk);
////        String[] result = new String[splitMessage.length];
//        int index = 0;
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < splitMessage.length; i += chunk) {
//            list.add(Arrays.toString(Arrays.copyOfRange(splitMessage, i, Math.min(splitMessage.length, i + (int) chunk))));
////            result[index] = Arrays.toString(Arrays.copyOfRange(splitMessage, i, Math.min(splitMessage.length, i + (int) chunk)));
//            index++;
//        }
//        String[] r = new String[index];
//        for (int i = 0; i < index; i++) {
//            r[i] = list.get(i);
//        }
//        System.out.println(index);
//        return r;
//    }
//
//    public static String[] splitMorseCode(String str) {
//        final String[] words = RegexUtils.getWordSeparatorRegex().split(str.trim());
//        var threads = Runtime.getRuntime().availableProcessors();
//        System.out.println(threads);
//        var part_size = roundUp(Long.parseLong(String.valueOf(words.length)), threads);
//
//
//        String[] result = new String[(int) Math.ceil((double) str.length() / (double) part_size)];
//        for (int i = 0; i < result.length; i++)
//            result[i] = str.substring(i * (int) part_size, Math.min(str.length(), (i + 1) * (int) part_size));
//        return result;
//    }
//
//    private static long roundUp(long num, long divisor) {
//        return (num + divisor - 1) / divisor;
//    }
//
//    private static String convertToMorseCode(String[] strings) throws InterruptedException {
//        int threadCount = Runtime.getRuntime().availableProcessors();
//        final String wordSep = MorseCodeConfig.getInstance().getWordSeparator();
//        final String letterSep = MorseCodeConfig.getInstance().getLetterSeparator();
//        ExecutorService es = Executors.newFixedThreadPool(threadCount);
//
//        for (int i = 0; i < strings.length; i++) {
//            int j = i;
//            String s = strings[i];
//            System.out.println(s);
//            es.execute(() -> morseMap.put(j, covertTextToMorseCode2(s, wordSep, letterSep)));
//        }
//
//        es.shutdown();
//        boolean finished = es.awaitTermination(1, TimeUnit.DAYS);
//
//        while (!finished) {
//            finished = es.awaitTermination(1, TimeUnit.DAYS);
//        }
//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < threadCount; i++) {
//            builder.append(morseMap.getOrDefault(i, ""));
//        }
//        return builder.toString();
//    }
//
//    private static String convertToText(String[] strings) throws InterruptedException {
//        int threadCount = Runtime.getRuntime().availableProcessors();
//        final Pattern wordSep = RegexUtils.getWordSeparatorRegex();
//        final Pattern letterSep = RegexUtils.getLetterSeparatorRegex();
//        ExecutorService es = Executors.newFixedThreadPool(threadCount);
//
//        for (int i = 0; i < strings.length; i++) {
//            int j = i;
//            es.execute(() -> morseMap.put(j, convertMorseCodeToText2(strings[j], wordSep, letterSep)));
//        }
//
//        es.shutdown();
//        boolean finished = es.awaitTermination(1, TimeUnit.DAYS);
//
//        while (!finished) {
//            finished = es.awaitTermination(1, TimeUnit.DAYS);
//        }
//
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < threadCount; i++) {
//            builder.append(morseMap.get(i)).append(" ");
//        }
//        return builder.toString().trim();
//    }
//
//    private static String covertTextToMorseCode2(String message, String wordSeparator, String letterSeparator) {
////        message = message.replaceFirst("\\[", "").replaceFirst("]", "")
////                .replaceAll("(,{1})", "").replaceAll(",,", ",");
//        final StringBuilder morseCodeBuilder = new StringBuilder();
//        final char[] characters = message.toUpperCase().trim().toCharArray();
//        final int letterSeparatorLength = letterSeparator.length();
//
//        for (char character : characters) {
//            if (Character.isWhitespace(character)) {
//                morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - letterSeparatorLength).append(wordSeparator);
//                continue;
//            }
//            morseCodeBuilder.append(MorseCodeSearcher.charToMorse(character)).append(letterSeparator);
//        }
//        return morseCodeBuilder.toString().trim();
//    }
//
//    private static String convertMorseCodeToText2(String morseCode, Pattern wordSepRegex, Pattern letterSepRegex) {
//        final StringBuilder convertedToText = new StringBuilder();
////        final String[] words = wordSepRegex.split(morseCode.trim());
//
//        for (String word : wordSepRegex.split(morseCode.trim())) {
//            for (String letter : letterSepRegex.split(word)) {
//                convertedToText.append(MorseCodeSearcher.morseToChar(letter));
//            }
//            convertedToText.append(SPACE);
//        }
//
//        return convertedToText.toString().trim();
//    }
//}
