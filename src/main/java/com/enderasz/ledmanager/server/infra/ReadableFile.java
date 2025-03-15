package com.enderasz.ledmanager.server.infra;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;

public class ReadableFile extends File {
    public ReadableFile(FileLock lock, FileChannel channel, Path path) {
        super(lock, channel, path);
    }
}
