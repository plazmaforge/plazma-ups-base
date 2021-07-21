package com.ohapon.file;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileAnalyzerTest {

    private String RESOURCES_DIR;

    @Before
    public void init() {
        RESOURCES_DIR = System.getProperty("user.dir") + "/src/test/resources";
    }

    private String getFileName(String name) {
        return RESOURCES_DIR + "/" + name;
    }

    @Test
    public void testAnalyze() {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        FileAnalyzer.Result result = analyzer.analyze(fileName, "text");

        assertNotNull(result);
        assertEquals(3, result.getTotal());

        assertNotNull(result.getSentences());
        assertEquals(3, result.getSentences().length);

        assertEquals("file1:text", result.getSentences()[0]);
        assertEquals("Hello text", result.getSentences()[1]);
        assertEquals("I am text", result.getSentences()[2]);

    }

    @Test
    public void testAnalyzeWordNotFound() {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        FileAnalyzer.Result result = analyzer.analyze(fileName, "zero");

        assertNotNull(result);
        assertEquals(0, result.getTotal());

    }

    @Test
    public void testAnalyzeFileNotFound() {
        String fileName = getFileName("TestFolder/file1000000.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        assertThrows(RuntimeException.class, () -> {
            analyzer.analyze(fileName, "text");
        });
    }

    @Test
    public void testAnalyzeWithEmptyFileName() {
        FileAnalyzer analyzer = new FileAnalyzer();
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(null, "text");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze("", "text");
        });

    }

    @Test
    public void testAnalyzeWithEmptyWord() {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(fileName, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze(fileName, "");
        });

    }

    @Test
    public void testSplitSentences() {
        FileAnalyzer analyzer = new FileAnalyzer();
        String content = "text1. text2!text3?\ntext4";
        String[] sentences = analyzer.splitSentences(content);

        assertNotNull(sentences);
        assertEquals(4, sentences.length);

        assertEquals("text1", sentences[0]);
        assertEquals("text2", sentences[1]);
        assertEquals("text3", sentences[2]);
        assertEquals("text4", sentences[3]);

    }

    @Test
    public void testSplit() {
        FileAnalyzer analyzer = new FileAnalyzer();
        String content = "text1. text2!text3?\ntext4";
        String[] sentences = analyzer.split(content, "\r\n.!?");

        assertNotNull(sentences);
        assertEquals(4, sentences.length);

        assertEquals("text1", sentences[0]);
        assertEquals("text2", sentences[1]);
        assertEquals("text3", sentences[2]);
        assertEquals("text4", sentences[3]);

    }

    @Test
    public void testReadContent() throws Exception {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        String content = analyzer.readContent(fileName);

        assertEquals("file1:text\nWhat is your name? My name is 'Tiko'\nHello text. I am text!", content);

    }

}
