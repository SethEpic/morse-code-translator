package com.epic.morse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class Translator {

    static String encodeText(String message, String wordSeparator, String letterSeparator) {
        final StringBuilder morseCodeBuilder = new StringBuilder();
        final char[] characters = RegexUtils.multiSpaceRegex.matcher(message.toUpperCase().trim()).replaceAll(" ").toCharArray();
        final int letterSeparatorLength = letterSeparator.length();

        for (char character : characters) {
            if (Character.isWhitespace(character)) {
                morseCodeBuilder.setLength(morseCodeBuilder.length() - letterSeparatorLength);
                morseCodeBuilder.append(wordSeparator);
                continue;
            }
            morseCodeBuilder.append(MorseCodeSearcher.charToMorse(character)).append(letterSeparator);
        }

        morseCodeBuilder.setLength(morseCodeBuilder.length() - letterSeparatorLength);
        return morseCodeBuilder.toString();
    }

    static String[] encodeTextArray(String[] messages, String wordSeparator, String letterSeparator) {
        final String[] encodedMessages = new String[messages.length];

        for (int i = 0; i < messages.length; i++) {
            if (!messages[i].isEmpty() && messages[i].isBlank()) {
                encodedMessages[i] = wordSeparator;
                continue;
            }

            encodedMessages[i] = encodeText(messages[i], wordSeparator, letterSeparator);
        }
        return encodedMessages;
    }

    static String[] encodeCharArray(char[] chars) {
        String[] encodedMessages = new String[chars.length];

        for (int i = 0; i < chars.length; ++i) {
            ValidationService.validateEncode(chars[i]);
            encodedMessages[i] = MorseCodeSearcher.charToMorse(Character.toUpperCase(chars[i]));
        }

        return encodedMessages;
    }

    static List<String> encodeTextList(List<String> messages, String wordSeparator, String letterSeparator) {
        final List<String> encodedMessages = new ArrayList<>();
        messages.forEach(message -> encodedMessages.add(encodeText(message, wordSeparator, letterSeparator)));
        return encodedMessages;
    }

    static String decodeMorseCode(String morseCode, Pattern wordSepRegex, Pattern letterSepRegex) {
        final StringBuilder convertedToText = new StringBuilder();
        final String[] words = wordSepRegex.split(morseCode.trim());

        for (String word : words) {
            String[] letters = letterSepRegex.split(word);
            for (String letter : letters) {
                convertedToText.append(MorseCodeSearcher.morseToChar(letter));
            }
            convertedToText.append(' ');
        }

        return convertedToText.toString().trim();
    }

    static String[] decodeMorseCode(String[] morseCode, Pattern wordSepRegex, Pattern letterSepRegex) {
        final String[] decodedMessages = new String[morseCode.length];

        for (int i = morseCode.length; --i >= 0; ) {
            if (!wordSepRegex.matcher(morseCode[i]).find() && !letterSepRegex.matcher(morseCode[i]).find()) {
                decodedMessages[i] = MorseCodeSearcher.morseToChar(morseCode[i]);
            } else {
                ValidationService.validateMorseCodeToText(morseCode[i]);
                decodedMessages[i] = decodeMorseCode(morseCode[i], wordSepRegex, letterSepRegex);
            }
        }
        return decodedMessages;
    }

    static List<String> decodeMorseCodeList(List<String> morseCode, final Pattern wordSepRegex, final Pattern letterSepRegex) {
        return morseCode.stream().map(morse -> (!wordSepRegex.matcher(morse).find() && !letterSepRegex.matcher(morse).find()) ?
                MorseCodeSearcher.morseToChar(morse) :
                decodeMorseCode(morse, wordSepRegex, letterSepRegex)).collect(Collectors.toList());
    }
}
