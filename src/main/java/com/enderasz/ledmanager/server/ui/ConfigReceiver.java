package com.enderasz.ledmanager.server.ui;

import com.enderasz.ledmanager.server.domain.ConstLightConfig;
import com.enderasz.ledmanager.server.domain.FlashingLightConfig;
import com.enderasz.ledmanager.server.domain.LightConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigReceiver {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class);

    private final ClientManager clientManager;

    public ConfigReceiver(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
    
    public Map<Integer, LightConfig> receive() throws IOException {
        String input;
        input = clientManager.readMessage();
        int lightsCount = Integer.parseInt(input);
        logger.debug("Received lights count: " + lightsCount);

        Map<Integer, LightConfig> lights = new HashMap<>();
        for(int i = 0; i < lightsCount; i++) {
            input = clientManager.readMessage();
            int lightId = Integer.parseInt(input);
            logger.debug("Received light id: " + lightId);
            LightConfig lightConfig = receiveLightConfig();
            logger.debug("Received light config: " + lightConfig);
            lights.put(lightId, lightConfig);
        }

        return lights;
    }

    private LightConfig receiveLightConfig() throws IOException {
        String input = clientManager.readMessage();
        logger.debug("Received light config type: " + input);
        switch(input) {
            case "CONST":
                return receiveConstLightConfig();
            case "FLASH":
                return receiveFlashingLightConfig();
            case "NONE":
                return null;
            default:
                return null;
        }
    }

    private ConstLightConfig receiveConstLightConfig() throws IOException {
        ConstLightConfig config = new ConstLightConfig();

        String input = clientManager.readMessage();
        Integer colorValue = Integer.parseInt(input);
        config.setRed(colorValue);
        logger.debug("Received red value: " + config.getRed());

        input = clientManager.readMessage();
        colorValue = Integer.parseInt(input);
        config.setGreen(colorValue);
        logger.debug("Received green value: " + config.getGreen());

        input = clientManager.readMessage();
        colorValue = Integer.parseInt(input);
        config.setBlue(colorValue);
        logger.debug("Received blue value: " + config.getBlue());

        return config;
    }

    private FlashingLightConfig receiveFlashingLightConfig() throws IOException {
        FlashingLightConfig config = new FlashingLightConfig();

        String input = clientManager.readMessage();
        Integer colorValue = Integer.parseInt(input);
        config.setRed(colorValue);
        logger.debug("Received red value: " + config.getRed());

        input = clientManager.readMessage();
        colorValue = Integer.parseInt(input);
        config.setGreen(colorValue);
        logger.debug("Received green value: " + config.getGreen());

        input = clientManager.readMessage();
        colorValue = Integer.parseInt(input);
        config.setBlue(colorValue);
        logger.debug("Received blue value: " + config.getBlue());

        input = clientManager.readMessage();
        Integer timeValue = Integer.parseInt(input);
        config.setTimeLength(timeValue);
        logger.debug("Received timeLength value: " + config.getTimeLength());

        input = clientManager.readMessage();
        timeValue = Integer.parseInt(input);
        config.setTimeInterval(timeValue);
        logger.debug("Received timeInterval value: " + config.getTimeInterval());

        return config;
    }
}