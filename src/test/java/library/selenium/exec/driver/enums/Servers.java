package library.selenium.exec.driver.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Servers {
    LOCAL("local"),
    REMOTE("remote"),
    SAUCELABS("saucelabs"),
    BROWSERSTACK("browserstack"),
    APPIUM("appium"),
    PERFECTO("perfecto"),
    HTMLUNIT("htmlunit");

    private String serverName;

    Servers(String serverName) {
        this.serverName = serverName;
    }

    public String getserverName() {
        return serverName;
    }

    public static Servers get(String serverName) {
        Optional<Servers> first = Arrays.stream(Servers.values())
                .filter(server -> server.getserverName()
                        .equalsIgnoreCase(serverName))
                .findFirst();

        return first.orElse(LOCAL);
    }

}
