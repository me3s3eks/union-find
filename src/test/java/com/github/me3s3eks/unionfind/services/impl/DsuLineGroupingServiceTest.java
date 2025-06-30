package com.github.me3s3eks.unionfind.services.impl;

import com.github.me3s3eks.unionfind.datastructures.DisjointSetUnion;
import com.github.me3s3eks.unionfind.datastructures.impl.DefaultDisjointSetUnion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DsuLineGroupingServiceTest {
    private static DisjointSetUnion dsu;
    private static DsuLineGroupingService dsuLineGroupingService;

    @BeforeAll
    static void setUp() {
        dsu = new DefaultDisjointSetUnion();
        dsuLineGroupingService = new DsuLineGroupingService(dsu);
    }

    @Test
    void testGroupLines_sameGroup() {
        List<String> lines = List.of(
            ";B;A",
            "C;;A",
            "K;F;G",
            "K;I;A"
        );

        List<Set<Integer>> groups = dsuLineGroupingService.groupLines(lines);
        // We expect all lines belong to the same group
        assertEquals(1, groups.size());
        Set<Integer> group = groups.get(0);
        assertTrue(group.containsAll(List.of(0, 1, 2, 3)));
    }

    @Test
    void testGroupLines_differentGroups() {
        List<String> lines = List.of(
            "100;200;300",
            "200;300;100"
        );

        List<Set<Integer>> groups = dsuLineGroupingService.groupLines(lines);
        // We expect each line to be its own group because they don't have equal column values
        assertEquals(2, groups.size());

        Set<Integer> allIndices = new HashSet<>();
        for (Set<Integer> group : groups) {
            allIndices.addAll(group);
            // Each group should contain one member
            assertEquals(1, group.size());
        }

        assertTrue(allIndices.containsAll(List.of(0, 1)));
    }

    @Test
    void testGroupLines_mixedGroups() {
        List<String> lines = List.of(
            "111;123;222", // Line0: Group 1
            "300;;100",    // line1: Group 1
            "400;500;600", // Line2: Group 2
            "400;;",       // Line3: Group 2
            "200;123;100"  // line4: Group 1
        );

        List<Set<Integer>> groups = dsuLineGroupingService.groupLines(lines);
        // We expect two groups: one with first 3 lines, other with last 2
        assertEquals(2, groups.size());

        Set<Integer> group1 = groups.stream()
            .filter(g -> g.contains(0))
            .findFirst()
            .orElseThrow();
        Set<Integer> group2 = groups.stream()
            .filter(g -> g.contains(3))
            .findFirst()
            .orElseThrow();

        assertTrue(group1.containsAll(List.of(0, 1, 4)));
        assertTrue(group2.containsAll(List.of(2, 3)));
    }

    @Test
    void testGroupLines_emptyInput() {
        List<String> lines = Collections.emptyList();
        List<Set<Integer>> groups = dsuLineGroupingService.groupLines(lines);
        assertTrue(groups.isEmpty());
    }

    @Test
    void testGroupLines_singleLine() {
        List<String> lines = List.of("a;b;c");
        List<Set<Integer>> groups = dsuLineGroupingService.groupLines(lines);
        assertEquals(1, groups.size());
        assertTrue(groups.get(0).contains(0));
    }
}
