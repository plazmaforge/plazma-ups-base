package com.ohapon.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileAnalyzer {

    private static final String SENTENCE_SEPARATORS = ".!?";

    public static void main(String[] args) {

        if (args.length < 2) {
            printError("Invalid arguments");
            printUsage();
            return;
        }

        FileAnalyzer analyzer = new FileAnalyzer();
        String fileName = args[0];
        String word = args[1];

        analyzer.analyze(fileName, word);

    }

    protected void analyze(String fileName, String word) {
        try {
            Result result = analyzeText(fileName, word);
            processResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected Result analyzeText(String fileName, String word) throws IOException {
        Result result = new Result();
        if (fileName == null || fileName.isEmpty()) {
            result.error = "File name is empty";
            return result;
        }
        if (word == null || word.isEmpty()) {
            result.error = "Word is empty";
            return result;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            result.error = "File not found: " + fileName;
            return result;
        }

        BufferedReader reader = null;
        List<String> resultSentences = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            int total = 0;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] sentences = splitSentences(line);
                for (String sentence: sentences) {
                    count = countWord(sentence, word);
                    if (count > 0) {
                        resultSentences.add(sentence.trim());
                        total += count;
                    }
                }
            }
            result.sentences = resultSentences.toArray(new String[0]);
            result.total = total;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    protected void processResult(Result result) {
        if (result.error != null) {
            printError(result.error);
            return;
        }
        if (result.sentences != null) {
            for (String sentence: result.sentences) {
                printMessage(sentence);
            }
        }
        printMessage("Total: " + result.total);
    }

    public int countWord(String sentence, String word) {
        int index = -word.length();
        int count = 0;
        while ((index = sentence.indexOf(word, (index + word.length()))) != -1) {
            count++;
        }
        return count;
    }

    private String[] splitSentences(String str) {
        return split(str, SENTENCE_SEPARATORS);
    }

    private String[] split(String str, String separators) {
        StringTokenizer tokens = new StringTokenizer(str, separators, false);
        String[] result = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            result[i++] = tokens.nextToken();
        }
        return result;
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printError(String message) {
        System.out.println("ERROR: " + message);
    }

    private static void printUsage() {
        printMessage("Usage:");
        printMessage("java FileAnalyzer <file> <word>");
    }

    public static class Result {
        String[] sentences;
        int total;
        String error;
    }

}
