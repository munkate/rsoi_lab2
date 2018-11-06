package ru.rsoi.ships.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum ShipType {
    TANKER(1),
    BULK_CARRIER(2);
    private int value;
    private static Map map = new HashMap<>();

    private ShipType(int value) {
        this.value = value;
    }

    static {
        for (ShipType pageType : ShipType.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static ShipType valueOf(Integer unit) {
        return (ShipType) map.get(unit);
    }

    public int getValue() {
        return value;
    }
}
