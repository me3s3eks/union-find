package com.github.me3s3eks.unionfind.datastructures.impl;

import com.github.me3s3eks.unionfind.datastructures.DisjointSetUnion;

import java.util.ArrayList;
import java.util.List;

public class DefaultDisjointSetUnion implements DisjointSetUnion {

    private static final int ZERO = 0;

    private final List<Integer> parents;

    private final List<Integer> ranks;

    public DefaultDisjointSetUnion() {
        this.parents = new ArrayList<>();
        this.ranks = new ArrayList<>();
    }

    public DefaultDisjointSetUnion(int size) {
        this.parents = new ArrayList<>(size);
        this.ranks = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            parents.add(i);
            ranks.add(ZERO);
        }
    }

    // Find operation with path compression
    @Override
    public int find(int idx) {
        if (idx < 0 || idx >= parents.size()) {
            throw new IllegalArgumentException("Invalid index: " + idx);
        }

        if (parents.get(idx) != idx) {
            parents.set(idx, find(parents.get(idx)));
        }
        return parents.get(idx);
    }

    // Union operation with union by rank
    @Override
    public void union(int idx1, int idx2) {
        int rootIdx1 = find(idx1);
        int rootIdx2 = find(idx2);

        if (rootIdx1 == rootIdx2) return;

        int rankRootIdx1 = ranks.get(rootIdx1);
        int rankRootIdx2 = ranks.get(rootIdx2);

        if (rankRootIdx1 < rankRootIdx2) {
            parents.set(rootIdx1, rootIdx2);
        } else if (rankRootIdx1 > rankRootIdx2) {
            parents.set(rootIdx2, rootIdx1);
        } else {
            parents.set(rootIdx2, rootIdx1);
            ranks.set(rootIdx1, (rankRootIdx1 + 1));
        }
    }

    @Override
    public void reset(int size) {
        parents.clear();
        ranks.clear();
        for (int i = 0; i < size; i++) {
            parents.add(i);
            ranks.add(ZERO);
        }
    }
}
