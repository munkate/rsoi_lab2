package ru.rsoi.shipments.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum Unit {
    TONNA(1), KG(2), CENTNER(3);
    private int value;
    private static Map map = new HashMap<>();

    private Unit(int value) {
        this.value = value;
    }

    static {
        for (Unit pageType : Unit.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static Unit valueOf(int unit) {
        return (Unit) map.get(unit);
    }

    public int getValue() {
        return value;
    }
}

