package plazma.ups.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileAnalyzer {

    private static final String SENTENCE_SEPARATORS = ".!?\r\n";

    public static void main(String[] args) {

        if (args.length < 2) {
            printError("Invalid arguments");
            printUsage();
            return;
        }

        FileAnalyzer analyzer = new FileAnalyzer();
        String fileName = args[0];
        String word = args[1];

        Result result = analyzer.analyze(fileName, word);
        analyzer.printResult(result);

    }

    public Result analyze(String fileName, String word) {
        try {
            String content = readContent(fileName);
            String[] sentences = splitSentences(content);
            Result result = analyzeSentences(sentences, word);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Result analyzeSentences(String[] sentences, String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("File name is empty");
        }

        Result result = new Result();
        int total = 0;
        int count = 0;
        List<String> resultSentences = new ArrayList<>();

        for (String sentence: sentences) {
            count = countWord(sentence, word);
            if (count > 0) {
                resultSentences.add(sentence.trim());
                total += count;
            }
        }

        result.setSentences(resultSentences.toArray(new String[0]));
        result.setTotal(total);
        return result;
    }

    protected String readContent(String fileName) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is empty");
        }
        InputStream is = new FileInputStream(fileName);
        return readContent(is);
    }

    protected String readContent(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        int ch;
        StringBuilder sb = new StringBuilder();
        while((ch = bis.read()) != -1) {
            sb.append((char) ch);
        }
        bis.close();
        is.close();
        return sb.toString();
    }

    protected void printResult(Result result) {
        if (result.getSentences() != null) {
            for (String sentence: result.getSentences()) {
                printMessage(sentence);
            }
        }
        printMessage("Total: " + result.getTotal());
    }

    protected int countWord(String sentence, String word) {
        int index = -word.length();
        int count = 0;
        while ((index = sentence.indexOf(word, (index + word.length()))) != -1) {
            count++;
        }
        return count;
    }

    protected String[] splitSentences(String str) {
        return split(str, SENTENCE_SEPARATORS);
    }

    protected String[] split(String str, String separators) {
        StringTokenizer tokens = new StringTokenizer(str, separators, false);
        String[] result = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            result[i++] = tokens.nextToken().trim();
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
        private String[] sentences;
        private int total;

        public Result() {
        }

        public String[] getSentences() {
            return sentences;
        }

        public void setSentences(String[] sentences) {
            this.sentences = sentences;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

}
