package com.github.me3s3eks.unionfind.services;

import java.nio.file.Path;
import java.util.List;

public interface FileProcessorService {

    List<String> process(Path filePath);
}
