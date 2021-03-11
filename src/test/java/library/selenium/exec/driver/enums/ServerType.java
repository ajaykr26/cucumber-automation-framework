package library.selenium.exec.driver.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ServerType {
    LOCAL("local"),
    GRID("grid"),
    SAUCELABS("saucelabs"),
    BROWSERSTACK("browserstack"),
    APPIUM("appium"),
    PERFECTO("perfecto"),
    REMOTE("remote");

    private String serverName;

    ServerType(String serverName) {
        this.serverName = serverName;
    }

    public String getserverName() {
        return serverName;
    }

    public static ServerType get(String serverName) {
        Optional<ServerType> first = Arrays.stream(ServerType.values())
                .filter(server -> server.getserverName()
                        .equalsIgnoreCase(serverName))
                .findFirst();

        return first.orElse(LOCAL);
    }

}
