package com.github.me3s3eks.unionfind.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class FileUtils {

    private static final String GZIP_FILE_EXTENSION = ".gz";

    private static final String TEXT_FILE_EXTENSION = ".txt";

    public static Path validatePath(String pathStr) {
        if (pathStr == null) {
            throw new IllegalArgumentException("Path to file must not be null");
        }

        Path path;
        try {
            path = Paths.get(pathStr);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException("Invalid path: " + pathStr, e);
        }

        if (!path.isAbsolute()) {
            throw new IllegalArgumentException("Path must be absolute: " + pathStr);
        }

        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Path not contain file: " + pathStr);
        }

        return path;
    }

    public static void validateFileReadable(Path filePath) {
        if (!Files.isReadable(filePath)) {
            throw new IllegalArgumentException("Access to file denied: " + filePath);
        }
    }

    public static boolean isGzipFile(Path filePath) {
        return filePath.getFileName().toString().toLowerCase().endsWith(GZIP_FILE_EXTENSION);
    }

    public static boolean isTextFile(Path filePath) {
        return filePath.getFileName().toString().toLowerCase().endsWith(TEXT_FILE_EXTENSION);
    }

    public static BufferedReader getReaderForGzip(Path filePath) throws IOException {
        InputStream inStream = Files.newInputStream(filePath);
        GZIPInputStream gzipInStream = new GZIPInputStream(inStream);
        return new BufferedReader(new InputStreamReader(gzipInStream));
    }

    public static BufferedReader getReaderForText(Path filePath) throws IOException {
        return Files.newBufferedReader(filePath);
    }

    public static void writeGroupsToFile(Path filePath, List<Set<Integer>> groups, List<String> lines) {
        int countGroupsWithMoreThanOneElement = GroupsUtils.countGroupsWithMoreThanOneElement(groups);
        // Sort groups by size in descending order
        groups.sort((a, b) -> Integer.compare(b.size(), a.size()));

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write("Count of groups with more than one element: " + countGroupsWithMoreThanOneElement);
            writer.newLine();
            writer.newLine();

            for (int i = 0; i < groups.size(); i++) {
                writer.write("Group " + (i + 1));
                writer.newLine();
                for (int idx : groups.get(i)) {
                    writer.write(lines.get(idx));
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write to file: " + filePath, e);
        }
    }
}
