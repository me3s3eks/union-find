package com.github.me3s3eks.unionfind.utils;

import java.util.List;
import java.util.Set;

public class GroupsUtils {

    public static int countGroupsWithMoreThanOneElement(List<Set<Integer>> groups) {
        long countGroupsWithMoreThanOneElement = groups.stream()
            .filter(group -> group.size() > 1)
            .count();
        return Math.toIntExact(countGroupsWithMoreThanOneElement);
    }
}
