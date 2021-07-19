package com.ohapon.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public static int countFiles(String path) {
        checkPath(path);
        File file = new File(path);
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
        return countFiles(file, true);
    }

    public static void copy(String from, String to) throws IOException {
        checkPathFromTo(from, to);
        Files.copy(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void move(String from, String to) throws IOException {
        checkPathFromTo(from, to);
        Files.move(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
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

}
