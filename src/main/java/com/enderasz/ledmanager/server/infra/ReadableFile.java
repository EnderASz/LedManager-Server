package com.enderasz.ledmanager.server.infra;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ReadableFile extends File {
    public ReadableFile(FileLock lock, FileChannel channel) {
        super(lock, channel);
    }
}
