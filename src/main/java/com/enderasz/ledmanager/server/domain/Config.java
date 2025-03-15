package com.enderasz.ledmanager.server.domain;

import java.util.Map;

public class Config {
    Map<Integer, LightConfig> lightConfigMap;
    String name;
    String id;

    public Config(Map<Integer, LightConfig> lightConfigMap, String name, String id) {
        this.lightConfigMap = lightConfigMap;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Map<Integer, LightConfig> getLightConfigMap() {
        return lightConfigMap;
    }
}
