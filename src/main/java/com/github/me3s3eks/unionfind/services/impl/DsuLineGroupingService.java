package com.github.me3s3eks.unionfind.services.impl;

import com.github.me3s3eks.unionfind.datastructures.DisjointSetUnion;
import com.github.me3s3eks.unionfind.services.LineGroupingService;
import com.github.me3s3eks.unionfind.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DsuLineGroupingService implements LineGroupingService {

    private final DisjointSetUnion disjointSetUnion;

    public DsuLineGroupingService(DisjointSetUnion disjointSetUnion) {
        this.disjointSetUnion = disjointSetUnion;
    }

    // Groups lines by common column values and returns map from group index to line indices in that group
    @Override
    public List<Set<Integer>> groupLines(List<String> lines) {
        disjointSetUnion.reset(lines.size());

        // This is used to map each unique value in column to the line index where it was last time found
        List<Map<String, Integer>> columnValueToLastLineIdxList = new ArrayList<>();

        for (int lineIdx = 0; lineIdx < lines.size(); lineIdx++) {
            // Remove double quotes to reduce column value storage size
            String cleanedLine = StringUtils.deleteDoubleQuotes(lines.get(lineIdx));

            String[] columns = cleanedLine.split(";");
            for (int colIdx = 0; colIdx < columns.length; colIdx++) {
                String columnValue = columns[colIdx];

                // Create a new map if this column index appears for the first time
                if (columnValueToLastLineIdxList.size() == colIdx) {
                    columnValueToLastLineIdxList.add(new HashMap<>());
                }

                // Skip empty values
                if (columnValue.isEmpty()) continue;

                Map<String, Integer> columnValueToLastLineIdx = columnValueToLastLineIdxList.get(colIdx);
                // Merge current line’s group with group of this column value in the same column
                Integer lastLineIdx;
                if ((lastLineIdx = columnValueToLastLineIdx.get(columnValue)) != null) {
                    disjointSetUnion.union(lastLineIdx, lineIdx);
                }
                columnValueToLastLineIdx.put(columnValue, lineIdx);
            }
        }
        Map<Integer, Set<Integer>> groups = getGroupsAsMap(lines.size());
        return new ArrayList<>(groups.values());
    }

    private Map<Integer, Set<Integer>> getGroupsAsMap(int dsuSize) {
        Map<Integer, Set<Integer>> groups = new HashMap<>();
        for (int lineIdx = 0; lineIdx < dsuSize; lineIdx++) {
            int root = disjointSetUnion.find(lineIdx);
            groups.computeIfAbsent(root, k -> new HashSet<>()).add(lineIdx);
        }
        return groups;
    }
}
