package com.github.me3s3eks.unionfind.services.impl;

import com.github.me3s3eks.unionfind.services.FileProcessorService;
import com.github.me3s3eks.unionfind.utils.FileUtils;
import com.github.me3s3eks.unionfind.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class DefaultFileProcessorService implements FileProcessorService {

    private static final Pattern VALID_LINE_PATTERN =
        Pattern.compile("^(?=.*[^\\s\"]+.*)(?:\"[^\\s\"]*\"(?:;\"[^\\s\"]*\")*)+$");

    @Override
    public List<String> process(Path filePath) {
        try (BufferedReader reader = getFileReaderSupplier(filePath).get()) {
            // Use HashSet to ensure line uniqueness
            Set<String> uniqueValidLinesSet = new HashSet<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!StringUtils.isValidLine(line, VALID_LINE_PATTERN)) {
                    continue; // skip invalid
                }
                uniqueValidLinesSet.add(line); // skip duplicates
            }
            return new ArrayList<>(uniqueValidLinesSet);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file: " + filePath, e);
        }
    }

    private Supplier<BufferedReader> getFileReaderSupplier(Path filePath) {
        Supplier<BufferedReader> fileReaderSupplier;
        if (FileUtils.isGzipFile(filePath)) {
            fileReaderSupplier = () -> {
                try {
                    return FileUtils.getReaderForGzip(filePath);
                } catch (IOException e) {
                    throw new UncheckedIOException("Failed to open file for reading: " + filePath, e);
                }
            };
            return fileReaderSupplier;
        } else if (FileUtils.isTextFile(filePath)) {
            fileReaderSupplier = () -> {
                try {
                    return FileUtils.getReaderForText(filePath);
                } catch (IOException e) {
                    throw new UncheckedIOException("Failed to open file for reading: " + filePath, e);
                }
            };
            return fileReaderSupplier;
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filePath);
        }
    }
}
