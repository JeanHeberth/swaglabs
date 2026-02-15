package br.com.swaglabs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class PropertiesReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    private PropertiesReader() {
        throw new IllegalStateException("Essa classe não deve ser instanciada");
    }

    private static Map<String, String> properties;

    private static void loadProperties() {
        var yaml = new Yaml();
        try (InputStream inputStream = yaml.getClass().getClassLoader().getResourceAsStream("arquivo-properties.yaml")) {
            if (inputStream == null) {
                LOGGER.warn("arquivo-properties.yaml não encontrado no classpath");
                properties = Collections.emptyMap();
                return;
            }
            Object loaded = yaml.load(inputStream);
            if (loaded instanceof Map<?, ?> raw) {
                Map<String, String> safe = new HashMap<>();
                for (Map.Entry<?, ?> entry : raw.entrySet()) {
                    Object k = entry.getKey();
                    Object v = entry.getValue();
                    if (k != null && v != null) {
                        safe.put(k.toString(), v.toString());
                    }
                }
                properties = safe;
            } else {
                properties = Collections.emptyMap();
            }
        } catch (IOException e) {
            LOGGER.error("Erro ao carregar arquivo-properties.yaml", e);
            properties = Collections.emptyMap();
        }
    }

    /**
     * Legacy API kept for compatibility. Delegates to getAny(key).
     */
    public static String get(String property) {
        String val = getAny(property);
        // keep old special-case behavior fallback: if asked for base_uri, fall back to some env/system historical keys
        if ((val == null || val.isBlank()) && "base_uri".equals(property)) {
            String selenideBase = System.getProperty("selenide.baseUrl");
            if (selenideBase != null && !selenideBase.isBlank()) return selenideBase;

            String baseUrlProp = System.getProperty("baseUrl");
            if (baseUrlProp != null && !baseUrlProp.isBlank()) return baseUrlProp;

            String envBase = System.getenv("BASE_URI");
            if (envBase != null && !envBase.isBlank()) return envBase;

            String envSelenide = System.getenv("SELENIDE_BASEURL");
            if (envSelenide != null && !envSelenide.isBlank()) return envSelenide;
        }
        return val;
    }

    /**
     * Try to resolve the first non-empty value among the provided keys.
     * For each candidate key we check, in order:
     *  - exact System property
     *  - environment variable variants (key -> UPPERCASE with '.' and '-' replaced by '_')
     *  - loaded YAML properties map (as-is and some simple variants)
     */
    public static String getAny(String... keys) {
        if (keys == null || keys.length == 0) return null;
        if (properties == null) loadProperties();

        for (String key : keys) {
            if (key == null || key.isBlank()) continue;
            // 1) exact system property
            String sys = System.getProperty(key);
            if (sys != null && !sys.isBlank()) return sys;

            // 2) environment variable variants
            String envKey = key.replace('.', '_').replace('-', '_').toUpperCase();
            String env = System.getenv(envKey);
            if (env != null && !env.isBlank()) return env;
            // also try uppercase raw key
            String env2 = System.getenv(key.toUpperCase());
            if (env2 != null && !env2.isBlank()) return env2;

            // 3) YAML properties map checks
            if (properties != null && !properties.isEmpty()) {
                // try exact
                String yamlVal = properties.get(key);
                if (yamlVal != null && !yamlVal.isBlank()) return yamlVal;
                // try some common alternatives
                yamlVal = properties.get(key.replace('.', '_'));
                if (yamlVal != null && !yamlVal.isBlank()) return yamlVal;
                yamlVal = properties.get(key.replace('.', '-'));
                if (yamlVal != null && !yamlVal.isBlank()) return yamlVal;
                yamlVal = properties.get(key.toLowerCase());
                if (yamlVal != null && !yamlVal.isBlank()) return yamlVal;
            }
        }
        return null;
    }
}
