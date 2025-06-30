package com.github.me3s3eks.unionfind.utils;

import static com.github.me3s3eks.unionfind.utils.ConsoleWriter.ConsoleCode.BLUE;
import static com.github.me3s3eks.unionfind.utils.ConsoleWriter.ConsoleCode.GREEN;
import static com.github.me3s3eks.unionfind.utils.ConsoleWriter.ConsoleCode.RED;
import static com.github.me3s3eks.unionfind.utils.ConsoleWriter.ConsoleCode.RESET;
import static com.github.me3s3eks.unionfind.utils.ConsoleWriter.ConsoleCode.YELLOW;

public class ConsoleWriter {

    private static final String ERROR_TITLE = "ERROR";

    private static final String INFO_TITLE = "INFO";

    private static final String SUCCESS_TITLE = "SUCCESS";

    private static final String WARNING_TITLE = "WARNING";

    public static void write(String message) {
        System.out.println(message);
    }

    public static void writeWithTitle(String title, String message) {
        System.out.println("[" + title + "]: " + message);
    }

    public static void writeWithFormat(ConsoleCode code, String message) {
        System.out.println(code.getCode() + message + RESET.getCode());
    }

    public static void writeWithTitleAndFormat(ConsoleCode code, String title, String message) {
        System.out.println(code.getCode() + "[" + title + "]: " + message + RESET.getCode());
    }

    public static void writeError(String message) {
        writeWithTitleAndFormat(RED, ERROR_TITLE, message);
    }

    public static void writeInfo(String message) {
        writeWithTitleAndFormat(BLUE, INFO_TITLE, message);
    }

    public static void writeSuccess(String message) {
        writeWithTitleAndFormat(GREEN, SUCCESS_TITLE, message);
    }

    public static void writeWarning(String message) {
        writeWithTitleAndFormat(YELLOW, WARNING_TITLE, message);
    }

    public enum ConsoleCode {
        BLUE("\u001B[34m"),
        GREEN("\u001B[32m"),
        RED("\u001B[31m"),
        YELLOW("\u001B[33m"),
        RESET("\u001B[0m");

        private String code;

        ConsoleCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
