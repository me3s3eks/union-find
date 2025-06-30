package com.github.me3s3eks.unionfind.datastructures;

public interface DisjointSetUnion {

    int find(int idx);

    void union(int idx1, int idx2);

    default void addIndex() {
        throw new UnsupportedOperationException("addIndex() not supported");
    }

    default void reset(int size) {
        throw new UnsupportedOperationException("reset() not supported");
    }
}
