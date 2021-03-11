package library.selenium.exec.driver.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PlateformType {
    IOS("ios"),
    ANDROID("android");

    private String plateformType;

    PlateformType(String plateformType) {
        this.plateformType = plateformType;
    }

    public String getPlateformType() {
        return plateformType;
    }

    public static PlateformType get(String plateformType) {
        Optional<PlateformType> first = Arrays.stream(PlateformType.values())
                .filter(browser -> browser.getPlateformType()
                        .equalsIgnoreCase(plateformType))
                .findFirst();

        return first.orElse(null);
    }

}
