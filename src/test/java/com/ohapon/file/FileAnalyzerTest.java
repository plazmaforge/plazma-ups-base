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
    public void testAnalyze() throws Exception {
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
    public void testAnalyzeNotFound() throws Exception {
        String fileName = getFileName("TestFolder/file1.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        FileAnalyzer.Result result = analyzer.analyze(fileName, "zero");

        assertNotNull(result);
        assertEquals(0, result.getTotal());

    }


    @Test
    public void testAnalyzeFileNotFound() throws Exception {
        String fileName = getFileName("TestFolder/file1000000.txt");
        FileAnalyzer analyzer = new FileAnalyzer();
        assertThrows(RuntimeException.class, () -> {
            analyzer.analyze(fileName, "text");
        });
    }

}
