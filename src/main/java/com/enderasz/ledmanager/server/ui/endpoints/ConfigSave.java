package com.enderasz.ledmanager.server.ui.endpoints;

import com.enderasz.ledmanager.server.domain.Config;
import com.enderasz.ledmanager.server.domain.ConstLightConfig;
import com.enderasz.ledmanager.server.domain.FlashingLightConfig;
import com.enderasz.ledmanager.server.domain.LightConfig;
import com.enderasz.ledmanager.server.infra.ConfigStorage;
import com.enderasz.ledmanager.server.infra.WritableFile;
import com.enderasz.ledmanager.server.ui.ClientManager;
import com.enderasz.ledmanager.server.ui.ConfigReceiver;
import com.enderasz.ledmanager.server.ui.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigSave implements Consumer<ClientManager> {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);

    @Override
    public void accept(ClientManager clientManager) {
        Config config = receive(clientManager);
        try {
            save(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Config receive(ClientManager clientManager) {
        logger.debug("Receiving configuration");

        String configId;
        try {
            configId = clientManager.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("Received configuration id: " + configId);

        String configName;
        try {
            configName = clientManager.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("Received configuration name: " + configName);

        ConfigReceiver receiver = new ConfigReceiver(clientManager);
        Map<Integer, LightConfig> config;
        try {
            config = receiver.receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.debug("Configuration receive");
        return new Config(config, configName, configId);
    }

    private static void save(Config config) throws IOException {
        logger.debug("Saving configuration");
        ConfigStorage storage = new ConfigStorage();
        try(WritableFile file = storage.openWritableConfigFile(config.getId())) {
            writeLightsToFile(config, file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.debug("Configuration saved");
    }

    private static void writeLightsToFile(Config config, WritableFile file) throws IOException {
        Map<Integer, LightConfig> lights = config.getLightConfigMap();
        FileChannel fc = file.getChannel();
        fc.write(ByteBuffer.wrap((config.getName() + "\n").getBytes()));
        for (Map.Entry<Integer, LightConfig> entry : lights.entrySet()) {
            StringBuilder sb = new StringBuilder();

            sb.append(entry.getKey());
            sb.append("\t");

            LightConfig lightConfig = entry.getValue();
            sb.append(getLightConfigSignature(lightConfig));

            if(lightConfig != null) {
                sb.append("\t");
                sb.append(lightConfig.toPlainText());
            }

            sb.append("\n");

            fc.write(ByteBuffer.wrap(sb.toString().getBytes()));
        }
    }

    private static String getLightConfigSignature(LightConfig lightConfig) {
        if (lightConfig == null) {
            return "NULL";
        } else if (lightConfig instanceof ConstLightConfig) {
            return "CONST";
        } else if (lightConfig instanceof FlashingLightConfig) {
            return "FLASH";
        } else {
            return "NULL";
        }
    }
}
