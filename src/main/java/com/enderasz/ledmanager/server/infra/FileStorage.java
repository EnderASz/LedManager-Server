package com.enderasz.ledmanager.server.infra;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileStorage {
    static private final Logger logger = LogManager.getLogger(FileStorage.class);
    final static private AppDirs appDirs = AppDirsFactory.getInstance();

    static protected String getApplicationName() {
        return "LedManager-Server";
    }

    static protected Path getApplicationDataStorageLocation() {
        String path = appDirs.getUserDataDir(getApplicationName(), null, "EnderASz");
        return Paths.get(path);
    }

    static protected WritableFile openWritableFile(Path path) throws IOException {
        Files.createDirectories(path.getParent());
        FileChannel fc = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        FileLock l = fc.lock(0L, Long.MAX_VALUE, false);

        WritableFile file = new WritableFile(l, fc);

        logger.info("Opened writable file: " + path);

        return file;
    }

    static protected ReadableFile openReadableFile(Path path) throws IOException {
        FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);
        FileLock l = fc.lock(0L, Long.MAX_VALUE, true);
        return new ReadableFile(l, fc);
    }
}
