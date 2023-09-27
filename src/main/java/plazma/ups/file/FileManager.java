package plazma.ups.file;

import java.io.*;

public class FileManager {

    public static int countFiles(String path) {
        checkPath(path);
        File file = new File(path);
        checkFileExists(file);
        return countFiles(file, false);
    }

    private static int countFiles(File file, boolean isCountDirectory) {
        if (file.isFile()) {
            return 0;
        }
        File[] children = file.listFiles();
        if (children.length == 0) {
            return 0;
        }
        int count = 0;
        for (File child: children) {
            if ((isCountDirectory && child.isDirectory())
                    || (!isCountDirectory && child.isFile())) {
                count++;
            }
            count += countFiles(child, isCountDirectory);
        }
        return count;
    }

    public static int countDirs(String path) {
        checkPath(path);
        File file = new File(path);
        checkFileExists(file);
        return countFiles(file, true);
    }

    public static void copy(String from, String to) throws IOException {
        checkPathFromTo(from, to);
        File fileFrom = new File(from);
        checkFileExists(fileFrom);
        File fileTo = new File(to);
        copyFile(fileFrom, fileTo);
    }

    public static void move(String from, String to) throws IOException {
        checkPathFromTo(from, to);
        File fileFrom = new File(from);
        checkFileExists(fileFrom);
        File fileTo = new File(to);
        copyFile(fileFrom, fileTo);
        fileFrom.delete();
    }

    private static void copyFile(File fileFrom, File fileTo) throws IOException {
        try (
            InputStream is = new FileInputStream(fileFrom);
            OutputStream os = new FileOutputStream(fileTo)) {
            byte data[] = new byte[8192];
            int i = 0;
            while ((i = is.read(data)) != -1) {
                os.write(data, 0, i);
            }
        }
    }

    private static void checkPath(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path must be not empty");
        }
    }

    private static void checkPathFromTo(String from, String to) {
        if (from == null || from.isEmpty()) {
            throw new IllegalArgumentException("Path 'from' must be not empty");
        }
        if (to == null || to.isEmpty()) {
            throw new IllegalArgumentException("Path 'to' must be not empty");
        }
    }

    private static void checkFileExists(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
        }
    }

}
