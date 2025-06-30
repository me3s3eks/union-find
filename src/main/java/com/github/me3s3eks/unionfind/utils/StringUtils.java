package com.github.me3s3eks.unionfind.utils;

import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern DOUBLE_QUOTE_PATTERN = Pattern.compile("\"");

    public static String deleteDoubleQuotes(String string) {
        return DOUBLE_QUOTE_PATTERN.matcher(string).replaceAll("");
    }

    public static boolean isValidLine(String line, Pattern pattern) {
        return pattern.matcher(line).matches();
    }
}
