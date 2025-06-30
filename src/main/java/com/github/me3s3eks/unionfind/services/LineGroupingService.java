package com.github.me3s3eks.unionfind.services;

import java.util.List;
import java.util.Set;

public interface LineGroupingService {

    List<Set<Integer>> groupLines(List<String> lines);
}
