package com.github.me3s3eks.unionfind;

import com.github.me3s3eks.unionfind.datastructures.impl.DefaultDisjointSetUnion;
import com.github.me3s3eks.unionfind.services.FileProcessorService;
import com.github.me3s3eks.unionfind.services.LineGroupingService;
import com.github.me3s3eks.unionfind.services.impl.DefaultFileProcessorService;
import com.github.me3s3eks.unionfind.services.impl.DsuLineGroupingService;
import com.github.me3s3eks.unionfind.utils.ConsoleWriter;
import com.github.me3s3eks.unionfind.utils.FileUtils;
import com.github.me3s3eks.unionfind.utils.GroupsUtils;

import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class Main {
    private static final String OUTPUT_FILE_NAME = "out.txt";

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        if (args.length != 1) {
            ConsoleWriter.writeError("Usage: java -jar union-find.jar <absolute-path-to-file>");
            return;
        }

        String inputFilePathStr = args[0];

        Path intputFilePath;
        try {
            intputFilePath = FileUtils.validatePath(inputFilePathStr);
            FileUtils.validateFileReadable(intputFilePath);
        } catch (IllegalArgumentException e) {
            ConsoleWriter.writeError(e.getMessage());
            return;
        }

        List<String> lines;
        try {
            FileProcessorService fileProcessor = new DefaultFileProcessorService();
            ConsoleWriter.writeInfo("Start input file processing...");
            lines = fileProcessor.process(intputFilePath);
            ConsoleWriter.writeInfo("Finished input file processing.");
        } catch (UncheckedIOException | IllegalArgumentException e) {
            ConsoleWriter.writeError(e.getMessage());
            return;
        }

        LineGroupingService groupingService = new DsuLineGroupingService(new DefaultDisjointSetUnion());
        ConsoleWriter.writeInfo("Start line grouping...");
        List<Set<Integer>> groups = groupingService.groupLines(lines);
        ConsoleWriter.writeInfo("Finished line grouping.");

        int numberGroupsWithMoreThanOneElement = GroupsUtils.countGroupsWithMoreThanOneElement(groups);
        ConsoleWriter.writeSuccess("Number of groups with more than one element: " +
            numberGroupsWithMoreThanOneElement);
        ConsoleWriter.writeSuccess("Total number of groups: " + groups.size());
        ConsoleWriter.writeSuccess("Total number of unique valid lines in input file: " + lines.size());

        Path outputFilePath = intputFilePath.getParent().resolve(OUTPUT_FILE_NAME);
        ConsoleWriter.writeInfo("Start writing groups to file: " + outputFilePath);
        FileUtils.writeGroupsToFile(outputFilePath, groups, lines);
        ConsoleWriter.writeInfo("Finished writing groups to file: " + outputFilePath);
        ConsoleWriter.writeSuccess("Program result saved to: " + outputFilePath);

        long endTime = System.nanoTime();
        ConsoleWriter.writeSuccess("Execution time: " + (endTime - startTime) / 1_000_000 + " ms");
    }
}