package com.enderasz.ledmanager.server.infra;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigStorage extends FileStorage {
    public ReadableFile openReadableConfigFile(String configId) throws IOException {
        Path configPath = getConfigFilePath(configId);
        return openReadableFile(configPath);
    }

    public WritableFile openWritableConfigFile(String configId) throws IOException {
        Path configPath = getConfigFilePath(configId);
        return openWritableFile(configPath);
    }

    private Path getConfigFilePath(String configId) {
        String basePath = getApplicationDataStorageLocation().toString();
        return Paths.get(basePath, "configs", configId + ".lcfg");
    }
}
