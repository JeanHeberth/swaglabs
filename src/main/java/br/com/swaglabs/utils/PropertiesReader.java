package br.com.swaglabs.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class PropertiesReader {
    private PropertiesReader() {
        throw new IllegalStateException("Essa classe não deve ser instanciada");
    }

    private static Map<String, String> properties;

    private static void loadProperties() {
        var yaml = new Yaml();
        try (InputStream inputStream = yaml.getClass().getClassLoader().getResourceAsStream("arquivo-properties.yaml")) {
            properties = yaml.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String get(String property) {
        if (properties == null) loadProperties();
        return properties.get(property);
    }
}

