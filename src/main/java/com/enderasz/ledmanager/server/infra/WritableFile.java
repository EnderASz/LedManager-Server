package com.enderasz.ledmanager.server.infra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class WritableFile extends File {
    static private final Logger logger = LogManager.getLogger(FileStorage.class);

    public WritableFile(FileLock lock, FileChannel channel) throws IOException {
        super(lock, channel);
        channel.truncate(0);
    }

    private void write(ByteBuffer buffer) throws IOException {
        getChannel().write(buffer);
    }

    public void write(byte[] bytes) throws IOException {
        logger.debug("Writing " + bytes.length + " bytes to file: " + getPath().toString());
        write(ByteBuffer.wrap(bytes));
    }

    public void write(String string) throws IOException {
        write(string.getBytes());
    }
}
