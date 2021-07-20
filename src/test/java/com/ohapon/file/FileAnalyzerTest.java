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
    public void testAnalyzeText() throws Exception {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        FileAnalyzer.Result result = analyzer.analyzeText(fileName, "text");

        assertNotNull(result);
        assertNull(result.error);
        assertEquals(3, result.total);

        assertNotNull(result.sentences);
        assertEquals(3, result.sentences.length);

        assertEquals("file1:text", result.sentences[0]);
        assertEquals("Hello text", result.sentences[1]);
        assertEquals("I am text", result.sentences[2]);

    }

    @Test
    public void testAnalyzeTextNotFound() throws Exception {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        FileAnalyzer.Result result = analyzer.analyzeText(fileName, "zero");

        assertNotNull(result);
        assertNull(result.error);
        assertEquals(0, result.total);

    }


    @Test
    public void testAnalyzeFileNotFound() throws Exception {
        String fileName = getFileName("TestFolder/file1000000.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        FileAnalyzer.Result result = analyzer.analyzeText(fileName, "text");

        assertNotNull(result);
        assertNotNull(result.error);
        assertTrue(result.error.startsWith("File not found"));

    }

    @Test
    public void testAnalyze() {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        analyzer.analyze(fileName, "text");
    }

}
