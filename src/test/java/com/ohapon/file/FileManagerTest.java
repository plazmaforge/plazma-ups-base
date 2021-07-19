package com.ohapon.file;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileManagerTest {

    private String RESOURCES_DIR;

    @Before
    public void init() {
        RESOURCES_DIR = System.getProperty("user.dir") + "/src/test/resources";
    }

    @Test
    public void testCountFiles() {
        String fileName = getFileName("TestFolder");
        assertEquals(4, FileManager.countFiles(fileName));

        fileName = getFileName("TestFolder/Folder1/");
        assertEquals(2, FileManager.countFiles(fileName));

        fileName = getFileName("TestFolder/Folder2/");
        assertEquals(0, FileManager.countFiles(fileName));

        fileName = getFileName("TestFolder/Folder1/Folder11");
        assertEquals(1, FileManager.countFiles(fileName));

    }

    @Test
    public void testCountDirs() {
        String fileName = getFileName("TestFolder");
        assertEquals(6, FileManager.countDirs(fileName));

        fileName = getFileName("TestFolder/Folder1/");
        assertEquals(3, FileManager.countDirs(fileName));

        fileName = getFileName("TestFolder/Folder2/");
        assertEquals(0, FileManager.countDirs(fileName));

    }

    private String getFileName(String name) {
        return RESOURCES_DIR + "/" + name;
    }

}
