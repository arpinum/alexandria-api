package arpinum.configuration;

import java.nio.file.*;
import java.util.Optional;

public class Environment {

    public static String get() {
        return Optional.ofNullable(System.getenv("env")).orElse("dev");
    }

    public static Path configPath() {
        return Paths.get(Optional.ofNullable(System.getenv("CONFIG_PATH")).orElse("config"));
    }

    private Environment() {
    }


}
