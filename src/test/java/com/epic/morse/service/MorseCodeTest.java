package com.epic.morse.service;

import com.epic.morse.exception.ExceptionMessages;
import com.epic.morse.exception.MorseCodeException;

import java.lang.reflect.Method;
import java.util.*;


@SuppressWarnings({"unused"})
public class MorseCodeTest {
    private static final String test = "Hello World, my name is Seth. I created this morse code translator!";
    private static final String testMorseCode = ".... . .-.. .-.. ---  .-- --- .-. .-.. -.. --..--  -- -.--  -. .- -- .  .. ...  ... . - .... .-.-.-  ..  -.-. .-. . .- - . -..  - .... .. ...  -- --- .-. ... .  -.-. --- -.. .  - .-. .- -. ... .-.. .- - --- .-. -.-.--";
    private static final Map<String, Boolean> responseMap = new HashMap<>();
//    # is unsupported

    public static void main(String[] args) throws NoSuchMethodException {
        run("all");
    }

    private static void run(String test) throws NoSuchMethodException {
        if (test == null || test.equalsIgnoreCase("all")) {
            var methods = MorseCodeTest.class.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().contains("convert")) continue;

                try {
                    resetConfig();
                    method.invoke(MorseCodeTest.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            var method = MorseCodeTest.class.getDeclaredMethod("convert1_defaults");
            try {
                resetConfig();
                method.invoke(MorseCodeTest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logResponses();
    }

    public static void convertAllChars() {
        final String ascii_letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String ascii_lowercase = "abcdefghijklmnopqrstuvwxyz";
        final String ascii_uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String digits = "0123456789";
        final String printable = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!'$&\"()+,-./:;=?@_";
        final String punctuation = "!'$&\"()+,-./:;=?@_";

        List<String> strings = List.of(ascii_letters, ascii_lowercase, ascii_uppercase, digits, printable, punctuation);
        List<Boolean> bool = new ArrayList<>();

        for (String test : strings) {
            String morseCode = MorseCode.convertToMorseCode(test);
            String text = MorseCode.convertToText(morseCode);
            bool.add(test.equalsIgnoreCase(text));
        }

        responseMap.put("convertAllChars", !bool.contains(false));
    }

    public static void convert1_defaults() {
        String morseCode = MorseCode.convertToMorseCode(test);
        String text = MorseCode.convertToText(morseCode);
        responseMap.put("convert1", test.equalsIgnoreCase(text));
    }

    public static void convert2_CustomWordSep() {
        MorseCodeConfig.getInstance().setWordSeparator("=");

        String morseCode = MorseCode.convertToMorseCode(test);
        String text = MorseCode.convertToText(morseCode);
        responseMap.put("convert2", test.equalsIgnoreCase(text));
    }

    public static void convert3_CustomLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");

        String morseCode = MorseCode.convertToMorseCode(test);
        String text = MorseCode.convertToText(morseCode);
        responseMap.put("convert3", test.equalsIgnoreCase(text));
    }

    public static void convert4_CustomerLetterAndWordSeps() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        MorseCodeConfig.getInstance().setWordSeparator("/");

        String morseCode = MorseCode.convertToMorseCode(test);
        String text = MorseCode.convertToText(morseCode.concat(" "));
        responseMap.put("convert4", test.equalsIgnoreCase(text));
    }

    public static void convert5_LetterSep2Chars() {
        MorseCodeConfig.getInstance().setLetterSeparator("=_");

        String morseCode = MorseCode.convertToMorseCode(test);
        String text = MorseCode.convertToText(morseCode);
        System.out.println(text);
        responseMap.put("convert5", test.equalsIgnoreCase(text));
    }

    public static void convert6_American_Default() {
        MorseCodeConfig.getInstance().setMorseCodeType(MorseCodeType.AMERICAN);

        String morseCode = MorseCode.convertToMorseCode(test);
        String text = MorseCode.convertToText(morseCode);
        responseMap.put("convert6", test.equalsIgnoreCase(text));
    }

    public static void convert7_American_CustomLetterSep() {
        MorseCodeConfig.getInstance().setLetterSeparator("=");
        MorseCodeConfig.getInstance().setMorseCodeType(MorseCodeType.AMERICAN);

        String morseCode = MorseCode.convertToMorseCode(test);

        try {
            String text = MorseCode.convertToText(morseCode);
            responseMap.put("convert7", test.equalsIgnoreCase(text));
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("convert7", false);
        }
    }

    public static void convert8_AlphaLetterSep_ShouldFail() {
        MorseCodeConfig.getInstance().setLetterSeparator("G");
        String morseCode = MorseCode.convertToMorseCode(test);

        try {
            MorseCode.convertToText(morseCode + "adsf");
        } catch (MorseCodeException morseCodeException) {
            if (morseCodeException.getErrorMessage().equals(ExceptionMessages.VALIDATION_ERROR_3)) {
                responseMap.put("convert8", true);
            }
        } finally {
            if (!responseMap.containsKey("convert8")) {
                responseMap.put("convert8", false);
            }
        }
    }

    private static void resetConfig() {
        MorseCodeConfig morseCodeConfig = MorseCodeConfig.getInstance();
        morseCodeConfig.setMorseCodeType(MorseCodeType.INTERNATIONAL);
        morseCodeConfig.setWordSeparator(morseCodeConfig.getInternationalDefaultWordSeparator());
        morseCodeConfig.setLetterSeparator(morseCodeConfig.getInternationalDefaultLetterSeparator());
    }

    private static void log(String methodName, boolean wasSuccessful) {
        String message = wasSuccessful ?
            (Console.CYAN_BRIGHT + methodName + " - Success!") : (Console.RED_BRIGHT + methodName + " - FAILED!");
        System.out.println(Console.RESET + message + Console.RESET);
    }

    private static void logResponses() {
        SortedSet<String> keys = new TreeSet<>(responseMap.keySet());
        for (String key : keys) {
            log(key, responseMap.get(key));
        }
    }

    private static class Error {
        private final String methodName;
        private final String expectedMorseCode;
        private final String morseCode;
        private final String text;
        private final String expectedText;

        public Error(String methodName, String expectedMorseCode, String morseCode, String text, String expectedText) {
            this.methodName = methodName;
            this.expectedMorseCode = expectedMorseCode;
            this.morseCode = morseCode;
            this.text = text;
            this.expectedText = expectedText;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getExpectedMorseCode() {
            return expectedMorseCode;
        }

        public String getMorseCode() {
            return morseCode;
        }

        public String getText() {
            return text;
        }

        public String getExpectedText() {
            return expectedText;
        }

        @Override
        public String toString() {
            return "Error{" +
                "methodName='" + methodName + '\'' +
                ", expectedMorseCode='" + expectedMorseCode + '\'' +
                ", morseCode='" + morseCode + '\'' +
                ", text='" + text + '\'' +
                ", expectedText='" + expectedText + '\'' +
                '}';
        }
    }

    private static final class Console {
        // Reset
        public static final String RESET = "\033[0m";
        // Regular Colors
        public static final String BLACK = "\033[0;30m";
        public static final String RED = "\033[0;31m";
        public static final String GREEN = "\033[0;32m";
        public static final String YELLOW = "\033[0;33m";
        public static final String BLUE = "\033[0;34m";
        public static final String PURPLE = "\033[0;35m";
        public static final String CYAN = "\033[0;36m";
        public static final String WHITE = "\033[0;37m";
        // Bold
        public static final String BLACK_BOLD = "\033[1;30m";
        public static final String RED_BOLD = "\033[1;31m";
        public static final String GREEN_BOLD = "\033[1;32m";
        public static final String YELLOW_BOLD = "\033[1;33m";
        public static final String BLUE_BOLD = "\033[1;34m";
        public static final String PURPLE_BOLD = "\033[1;35m";
        public static final String CYAN_BOLD = "\033[1;36m";
        public static final String WHITE_BOLD = "\033[1;37m";
        // Underline
        public static final String BLACK_UNDERLINED = "\033[4;30m";
        public static final String RED_UNDERLINED = "\033[4;31m";
        public static final String GREEN_UNDERLINED = "\033[4;32m";
        public static final String YELLOW_UNDERLINED = "\033[4;33m";
        public static final String BLUE_UNDERLINED = "\033[4;34m";
        public static final String PURPLE_UNDERLINED = "\033[4;35m";
        public static final String CYAN_UNDERLINED = "\033[4;36m";
        public static final String WHITE_UNDERLINED = "\033[4;37m";
        // High Intensity
        public static final String BLACK_BRIGHT = "\033[0;90m";
        public static final String RED_BRIGHT = "\033[0;91m";
        public static final String GREEN_BRIGHT = "\033[0;92m";
        public static final String YELLOW_BRIGHT = "\033[0;93m";
        public static final String BLUE_BRIGHT = "\033[0;94m";
        public static final String PURPLE_BRIGHT = "\033[0;95m";
        public static final String CYAN_BRIGHT = "\033[0;96m";
        public static final String WHITE_BRIGHT = "\033[0;97m";
        // Bold High Intensity
        public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";
        public static final String RED_BOLD_BRIGHT = "\033[1;91m";
        public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
        public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
        public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";
        public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
        public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
        public static final String WHITE_BOLD_BRIGHT = "\033[1;97m";

        private static final String ANSI_RESET = "\u001B[0m";
        private static final String ANSI_BLACK = "\u001B[30m";
        private static final String ANSI_RED = "\u001B[31m";
        private static final String ANSI_GREEN = "\u001B[32m";
        private static final String ANSI_YELLOW = "\u001B[33m";
        private static final String ANSI_BLUE = "\u001B[34m";
        private static final String ANSI_PURPLE = "\u001B[35m";
        private static final String ANSI_CYAN = "\u001B[36m";
        private static final String ANSI_WHITE = "\u001B[37m";
    }

}
