package com.epic.morse.service;

public class BenchmarkTest {
    public static void main(String[] args) {
        var testStr = "Hello World";
        for (int j = 0; j < 5; j++) {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                MorseCode.convertToText(MorseCode.convertToMorseCode(testStr.repeat(25)));
//                MorseCode.convertToText(MultiThreadingBeta.convertToMorseCode(testStr.repeat(25)));
            }
            long end = System.currentTimeMillis();
            System.err.println((end - startTime) + "ms");
        }
    }
}
