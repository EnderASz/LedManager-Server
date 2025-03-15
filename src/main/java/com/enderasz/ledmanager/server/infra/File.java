package com.enderasz.ledmanager.server.infra;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;

public class File implements AutoCloseable {
    private final FileChannel channel;
    private final FileLock lock;
    private final Path path;

    public File(FileLock lock, FileChannel channel, Path path) {
        this.lock = lock;
        this.channel = channel;
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public FileChannel getChannel() {
        return channel;
    }

    @Override
    public void close() throws Exception {
        lock.close();
        channel.close();
    }
}
