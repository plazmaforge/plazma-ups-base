package plazma.ups.file;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FileManagerTest {

    private String RESOURCES_DIR;
    private String TMP_DIR;

    @Before
    public void init() {
        RESOURCES_DIR = System.getProperty("user.dir") + "/src/test/resources";
        TMP_DIR = System.getProperty("java.io.tmpdir");
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

    @Test
    public void testCopy() throws IOException {
        String fileNameFrom = getFileName("TestFolder/file1.txt");
        String fileNameTo = getTmpFileName("tmp_file1.txt");
        FileManager.copy(fileNameFrom, fileNameTo);

        File fileTo = new File(fileNameTo);
        assertTrue(fileTo.exists());

        try (InputStream is = new FileInputStream(fileTo)) {
            String content = readContent(is);
            assertEquals("file1:text\nWhat is your name? My name is 'Tiko'\nHello text. I am text!", content);
        }
        fileTo.delete();
    }

    @Test
    public void testMove() throws IOException {
        String fileNameResource = getFileName("TestFolder/file1.txt");
        String fileNameFrom = getTmpFileName("tmp_file1.txt");
        String fileNameTo = getTmpFileName("tmp_file2.txt");

        // Prepare fileFrom in tmp dir
        FileManager.copy(fileNameResource, fileNameFrom);

        FileManager.move(fileNameFrom, fileNameTo);

        File fileFrom = new File(fileNameFrom);
        assertFalse(fileFrom.exists());

        File fileTo = new File(fileNameTo);
        assertTrue(fileTo.exists());

        try (InputStream is = new FileInputStream(fileTo)) {
            String content = readContent(is);
            assertEquals("file1:text\nWhat is your name? My name is 'Tiko'\nHello text. I am text!", content);
        }
        fileTo.delete();
    }

    private String getFileName(String name) {
        return RESOURCES_DIR + "/" + name;
    }

    private String getTmpFileName(String name) {
        return TMP_DIR + "/" + name;
    }

    private String readContent(InputStream is) throws IOException {
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

}
